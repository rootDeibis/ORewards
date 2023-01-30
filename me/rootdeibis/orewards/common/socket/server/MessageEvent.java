package me.rootdeibis.orewards.common.socket.server;

import me.rootdeibis.orewards.common.events.Event;

public class MessageEvent extends Event {

    private final String message;
    private final SocketServerThread.ServerSocketClientThread client;
    public MessageEvent(String message, SocketServerThread.ServerSocketClientThread client) {
        this.message = message;
        this.client = client;
    }

    public String getMessage() {
        return message;
    }

    public SocketServerThread.ServerSocketClientThread getClient() {
        return client;
    }
}
