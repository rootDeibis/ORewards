package me.rootdeibis.orewards.common.socket.client;

import java.io.PrintWriter;
import java.net.Socket;

public class SocketClient {


    private PrintWriter writer;
    private Socket client;

    public SocketClient(String host, int port){
        try {
            this.client = new Socket(host, port);
            this.writer = new PrintWriter(this.client.getOutputStream(), true);

        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    public PrintWriter getSender() {
        return writer;
    }

    public Socket getClient() {
        return client;
    }
}
