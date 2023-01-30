package me.rootdeibis.orewards.common.guifactory.interfaces;

import me.rootdeibis.orewards.common.function.Functions;
import me.rootdeibis.orewards.common.guifactory.MenuFactory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface IButton {




    String getMaterial();
    String getTitle();
    List<String> getLore();

    MenuFactory getContainer();

    int getSlot();

    IButton setMaterial(Functions.RFunction<String> material);
    IButton setTitle(Functions.RFunction<String> title);

    IButton setLore(Functions.RFunction<List<String>> lore);

    ItemStack getItem();



    void build();





}
