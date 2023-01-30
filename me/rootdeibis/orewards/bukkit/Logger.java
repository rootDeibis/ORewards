package me.rootdeibis.orewards.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.List;

@SuppressWarnings("unchecked")
public class Logger {

    public static void send(Object value) {
        String message;


        if (value instanceof List) {
            message = String.join("\n", (List<String>) value);
        } else  {
            message = (String) value;
        }

        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }



}
