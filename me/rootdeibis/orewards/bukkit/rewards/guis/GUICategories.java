package me.rootdeibis.orewards.bukkit.rewards.guis;

import me.rootdeibis.orewards.bukkit.Logger;
import me.rootdeibis.orewards.bukkit.Main;
import me.rootdeibis.orewards.bukkit.enums.CONFIG;
import me.rootdeibis.orewards.common.colors;
import me.rootdeibis.orewards.common.filemanagers.IFile;
import me.rootdeibis.orewards.common.guifactory.ButtonFactory;
import me.rootdeibis.orewards.common.guifactory.MenuFactory;
import me.rootdeibis.orewards.common.guifactory.interfaces.IButton;

import java.util.List;

public class GUICategories {

    private static MenuFactory menu;

    public static MenuFactory get() {

        if(menu == null) {
            IFile config = Main.getFileManager().Import("config.yml");

            String title = config.getString(CONFIG.GUI_CATEGORIES_TITLE.parse());
            int rows = config.getInt(CONFIG.GUI_CATEGORIES_ROWS.parse());

            menu = new MenuFactory(colors.colors(title), rows);

            loadCategoriesButtons(menu);
        }

        return menu;

    }


    private static void loadCategoriesButtons(MenuFactory menu) {
        IFile config = Main.getFileManager().Import("config.yml");


        config.getConfigurationSection("categories").getKeys(false).forEach(categoryKey -> {

            String material = config.getString(String.format(CONFIG.GUI_BUTTON_MATERIAL.parse(), categoryKey));
            String title = config.getString(String.format(CONFIG.GUI_BUTTON_TITLE.parse(), categoryKey));
            int slot = config.getInt(String.format(CONFIG.GUI_BUTTON_SLOT.parse(), categoryKey));
            List<String> lore = config.getStringList(String.format(CONFIG.GUI_BUTTON_LORE.parse(), categoryKey));

            Logger.send(material);

            IButton button = new ButtonFactory(menu, slot);

            button.setMaterial(() -> material);
            button.setTitle(() -> colors.colors(title));
            button.setLore(() -> colors.colors(lore));


            button.setClickAction((e, factory) -> {

            });

            menu.createButton(button);
        });


    }

}
