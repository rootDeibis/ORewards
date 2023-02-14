package me.rootdeibis.orewards.bukkit.rewards.guis;

import me.rootdeibis.orewards.bukkit.Logger;
import me.rootdeibis.orewards.bukkit.Main;
import me.rootdeibis.orewards.bukkit.enums.CONFIG;
import me.rootdeibis.orewards.bukkit.rewards.IReward;
import me.rootdeibis.orewards.common.colors;
import me.rootdeibis.orewards.common.filemanagers.IFile;
import me.rootdeibis.orewards.common.guifactory.ButtonFactory;
import me.rootdeibis.orewards.common.guifactory.MenuFactory;
import me.rootdeibis.orewards.common.guifactory.interfaces.IButton;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BuilderMenu {

    private static MenuFactory menu;

    public static void open(Player player) {

        if(menu == null) {
            IFile config = Main.getFileManager().Import("config.yml");

            String title = config.getString(CONFIG.GUI_CATEGORIES_TITLE.parse());
            int rows = config.getInt(CONFIG.GUI_CATEGORIES_ROWS.parse());

            menu = new MenuFactory(colors.colors(title), rows);

            loadCategoriesButtons(menu, player);
        }

       menu.open(player);

    }


    private static void loadCategoriesButtons(MenuFactory menu, Player player) {
        IFile config = Main.getFileManager().Import("config.yml");


        config.getConfigurationSection("categories").getKeys(false).forEach(categoryKey -> {

            String material = config.getString(String.format(CONFIG.GUI_BUTTON_MATERIAL.parse(), categoryKey));
            String title = config.getString(String.format(CONFIG.GUI_BUTTON_TITLE.parse(), categoryKey));
            int slot = config.getInt(String.format(CONFIG.GUI_BUTTON_SLOT.parse(), categoryKey));
            List<String> lore = config.getStringList(String.format(CONFIG.GUI_BUTTON_LORE.parse(), categoryKey));

            IButton button = new ButtonFactory(menu, slot);

            button.setMaterial(() -> material);
            button.setTitle(() -> colors.colors(title));
            button.setLore(() -> colors.colors(lore));

            Set<String> Rewards = config.getConfigurationSection("categories." + categoryKey + ".rewards").getKeys(false)
                            .stream().map(key -> "categories." + categoryKey + ".rewards." +  key).collect(Collectors.toSet());

            button.setClickAction((e, factory) -> {
                openRewardGui(player,categoryKey,Rewards);
            });

            menu.createButton(button);
        });
    }

    private static void openRewardGui(Player player,String categoryName,Set<String> rewardsList) {
        IFile config = Main.getFileManager().Import("config.yml");

        String title = config.getString(String.format(CONFIG.GUI_CATEGORY_TITLE.parse(), categoryName));
        int rows = config.getInt(String.format(CONFIG.GUI_CATEGORY_ROWS.parse(), categoryName));

        Set<IReward> rewards =  Main.getRewards().all(r -> rewardsList.stream().map(path -> path.substring(path.lastIndexOf(".") + 1)
        ).collect(Collectors.toList()).contains(r.getConfig().getFile().getName()));

        MenuFactory menuFactory = new MenuFactory(title, rows);


        menuFactory.open(player);


    }

}
