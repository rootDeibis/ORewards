package me.rootdeibis.orewards.common;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;


public class BukkitInterval {

    public static int repeat(Plugin plugin, Runnable runnable, int every) {

        return Bukkit.getScheduler().runTaskTimer(plugin, runnable, every * 20L, every * 20L).getTaskId();

    }

    public static int before(Plugin plugin, Runnable runnable, int beforeTime) {
        return Bukkit.getScheduler().runTaskLater(plugin, runnable, beforeTime * 20L).getTaskId();
    }

    public static void cancel(int id) {
        Bukkit.getScheduler().cancelTask(id);
    }


}
