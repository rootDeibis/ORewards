package me.rootdeibis.orewards.common.socket.server;

import java.util.function.Consumer;

public class ServerSocket {



    private java.net.ServerSocket socket;
    private SocketServerThread thread;
    private final int port;
    public ServerSocket(int port) {
        this.port = port;

        try {

            this.socket = new java.net.ServerSocket(port);

            this.thread = new SocketServerThread(this.socket);

            this.thread.start();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onMessage(Consumer<SocketServerThread.ServerSocketClientMessage> onMessage) {
        this.thread.setOnMessage(onMessage);
    }

    public java.net.ServerSocket getSocket() {
        return socket;
    }


    public SocketServerThread getThread() {
        return thread;
    }
}
