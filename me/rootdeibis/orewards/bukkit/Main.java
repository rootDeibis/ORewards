package me.rootdeibis.orewards.bukkit;


import me.rootdeibis.orewards.common.filemanagers.FileManagerBukkit;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.spigotmc.SpigotConfig;



public class Main extends JavaPlugin {


    private static Plugin plugin;
    private static FileManagerBukkit fileManager;
    private final String resourcePathFileManager = "me/rootdeibis/orewards/bukkit/resources/";

    @Override
    public void onLoad() {
        plugin = this;

        fileManager = new FileManagerBukkit(plugin, this.resourcePathFileManager);

        fileManager.Export("config.yml");
        fileManager.Export("messages.yml");
        fileManager.dir("rewards");


    }

    @Override
    public void onEnable() {
        if (!SpigotConfig.bungee) {
            Logger.send("&6PlayerWorlds &7> &cBungee Mode is Disabled, disabling plugin...");
            Bukkit.getPluginManager().disablePlugin(this);
        }



    }

    public static Plugin getPlugin() {
        return plugin;
    }

    public static FileManagerBukkit getFileManager() {
        return fileManager;
    }


}
