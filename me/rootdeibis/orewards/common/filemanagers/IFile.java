package me.rootdeibis.orewards.common.filemanagers;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class IFile extends YamlConfiguration {

    private File file;


    public IFile(File file) {
        super();

        this.file = file;

        this.loadConfiguration();
    }


    public File getFile() {
        return file;
    }



    private void loadConfiguration() {
        try {

            this.load(this.file);


        }catch (Exception e) {
            e.printStackTrace();
        }

    }




}
