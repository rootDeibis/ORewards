package me.rootdeibis.orewards.common.filemanagers;

import me.rootdeibis.orewards.common.cache.Cache;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.function.Predicate;


@SuppressWarnings("unused")
public class FileManagerBukkit {


    private final Plugin plugin;
    private final Cache<IFile> cache = new Cache<>();
    private final Cache<IDirectory> directoryCache = new Cache<>();

    private String resourcesPath;
    public FileManagerBukkit(Plugin plugin) {
        this.plugin = plugin;

        if(!this.plugin.getDataFolder().exists()) this.plugin.getDataFolder().mkdirs();
    }

    public FileManagerBukkit(Plugin plugin, String resourcesPath) {
        this.plugin = plugin;
        this.resourcesPath = resourcesPath;
    }


    private IFile loadFile(File file) {
        return new IFile(file);
    }

    public IFile Import(String name) {
        File file = new File(this.plugin.getDataFolder(), name);

        Predicate<IFile> findPredication = (f) -> f.getFile().getName().equals(file.getName());

        if (!cache.has(findPredication)) {

            this.cache.add(this.loadFile(file));

        }

        return cache.find(findPredication);

    }



    public void setResourcesPath(String path) {
        this.resourcesPath = path;
    }

    public void Export(String resource, String dest) {


        try {
            if (this.resourcesPath == null) throw new Exception("Invalid resources path");



            File destination = new File(this.plugin.getDataFolder(), dest);

            if (destination.exists()) {
                this.Import(dest);

                return;
            } else {
               if(!destination.getParentFile().exists()) destination.getParentFile().mkdirs();

               destination.createNewFile();
            }

            InputStream inputStream = this.plugin.getClass().getClassLoader().getResourceAsStream(this.resourcesPath + resource);



            FileOutputStream outputStream = new FileOutputStream(destination);


            int buffSize;

            byte[] buffer = new byte[512];

            while ((buffSize = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, buffSize);
            }

            inputStream.close();
            outputStream.close();

            this.Import(dest);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void Export(String resource) {
        this.Export(resource, resource);
    }



    public IDirectory dir(String name) {
        if (!this.directoryCache.has(d -> d.getDirectory().getName().equals(name))) {
            File directory = new File(this.plugin.getDataFolder(), name);

            if (!directory.exists()) directory.mkdirs();

            this.directoryCache.add(new IDirectory(directory, this));
        }
        return this.directoryCache.find(d -> d.getDirectory().getName().equals(name));
    }



}
