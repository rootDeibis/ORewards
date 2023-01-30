package me.rootdeibis.orewards.common.socket.server;

import me.rootdeibis.orewards.bukkit.Logger;
import me.rootdeibis.orewards.common.events.Listener;

public class MessageListener implements Listener {

    public static void onMessage(MessageEvent event) {

        Logger.send("[" + event.getClient().getConnection().getInetAddress().getHostAddress() + "] > " + event.getMessage());

    }


}
