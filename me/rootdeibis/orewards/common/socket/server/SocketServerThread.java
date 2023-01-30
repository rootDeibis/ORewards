package me.rootdeibis.orewards.common.socket.server;

import me.rootdeibis.orewards.common.cache.Cache;
import me.rootdeibis.orewards.common.events.EventManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class SocketServerThread extends Thread {


    public Consumer<ServerSocketClientMessage> onMessage;
    private final Cache<ServerSocketClientThread> connections = new Cache<>();
    private final ServerSocket serverSocket;


    public SocketServerThread(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void setOnMessage(Consumer<ServerSocketClientMessage> onMessage) {
        this.onMessage = onMessage;
    }

    @Override
    public void run() {
        while (true) {
           try {
               Socket client = serverSocket.accept();

               ServerSocketClientThread serverSocketClientThread = new ServerSocketClientThread(this, client);

               serverSocketClientThread.start();

               this.connections.add(serverSocketClientThread);

           }catch (Exception e) {
               e.printStackTrace();
           }
        }
    }

    public Cache<ServerSocketClientThread> getConnections() {
        return connections;
    }


    public static class ServerSocketClientMessage {

        private final String message;
        private final ServerSocketClientThread thread;

        public ServerSocketClientMessage(String message, ServerSocketClientThread thread) {
            this.message = message;
            this.thread = thread;
        }

        public ServerSocketClientThread getThread() {
            return thread;
        }

        public String getMessage() {
            return message;
        }
    }

    public static class ServerSocketClientThread extends Thread  {


        private BufferedReader inputStream;
        private PrintWriter outputStream;

        private final SocketServerThread serverThread;
        private final Socket client;
        public ServerSocketClientThread(SocketServerThread serverThread,Socket client) {
            this.serverThread = serverThread;
            this.client = client;
        }

        @Override
        public void run() {

            try {
                this.inputStream = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
                this.outputStream = new PrintWriter(this.client.getOutputStream(), true);

                while (!this.client.isClosed()) {
                    String inputLine = inputStream.readLine();

                    if (inputLine != null) {
                        EventManager.emit(new MessageEvent(inputLine, this));
                    }


                }

                this.remove();

            }catch (Exception e) {
                e.printStackTrace();
                this.remove();
            }
        }

        private void remove() {
            this.serverThread.getConnections().remove(s -> s.getConnection().isClosed());
            this.interrupt();
        }


        public Socket getConnection() {
            return client;
        }
    }


}
