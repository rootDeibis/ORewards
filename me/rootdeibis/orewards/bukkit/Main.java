package me.rootdeibis.orewards.bukkit;


import com.mysql.jdbc.log.Log;
import me.rootdeibis.orewards.common.database.mysql.ISQLDatabase;
import me.rootdeibis.orewards.common.database.mysql.MySQLDatabase;
import me.rootdeibis.orewards.common.database.mysql.SQLFileDatabase;
import me.rootdeibis.orewards.common.filemanagers.FileManagerBukkit;
import me.rootdeibis.orewards.common.function.Functions;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.spigotmc.SpigotConfig;

import java.io.File;


public class Main extends JavaPlugin {


    private static Plugin plugin;
    private static FileManagerBukkit fileManager;
    private final String resourcePathFileManager = "me/rootdeibis/orewards/bukkit/resources/";

    protected static ISQLDatabase database;

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

        Functions.EFunction callback = () -> {
            Logger.send("&6ORewards &7> &aDatabase connected!");

        };

        FileConfiguration config = fileManager.Import("config.yml");

        String dbType = config.getString("database.type");

        if (dbType.equalsIgnoreCase("SQLFile")) {
            database = new SQLFileDatabase(new File(this.getDataFolder(), "storage.db"));

        } else if(dbType.equals("MYSQL")) {
            String host = config.getString("database.host");
            String username = config.getString("database.credentials.username");
            String password = config.getString("database.credentials.password");
            String dbName = config.getString("database.credentials.databaseName");

            database = new MySQLDatabase(host, dbName, username, password);
        } else {
            this.getPluginLoader().disablePlugin(this);

            try {
                throw new InvalidConfigurationException("Invalid Database Type: " + dbType);
            } catch (InvalidConfigurationException e) {
                throw new RuntimeException(e);
            }
        }

        database.connect(callback);

    }

    public static Plugin getPlugin() {
        return plugin;
    }

    public static FileManagerBukkit getFileManager() {
        return fileManager;
    }

    public static ISQLDatabase getDB() {
        return database;
    }
}
