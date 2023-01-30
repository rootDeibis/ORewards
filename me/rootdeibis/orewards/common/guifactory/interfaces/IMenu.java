package me.rootdeibis.orewards.common.guifactory.interfaces;

import me.rootdeibis.orewards.common.cache.Cache;
import org.bukkit.entity.Player;

import java.util.function.Predicate;

public interface IMenu {

    IMenu createButton(IButton button);

    IButton getButton(Predicate<IButton> predicate);

    Cache<IButton> getButtons();





    IMenu load();

    void open(Player player);

    void close(Player player);





}
