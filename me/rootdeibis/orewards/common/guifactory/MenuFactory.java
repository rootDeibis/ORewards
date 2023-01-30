package me.rootdeibis.orewards.common.guifactory;

import me.rootdeibis.orewards.common.cache.Cache;
import me.rootdeibis.orewards.common.guifactory.interfaces.IButton;
import me.rootdeibis.orewards.common.guifactory.interfaces.IMenu;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.function.Predicate;

public class MenuFactory implements IMenu, InventoryHolder {

    private final Cache<IButton> buttonCache = new Cache<>();
    private final Inventory inventory;


    public MenuFactory(String title, int rows) {
        this.inventory = Bukkit.createInventory(this, rows * 9, title);
    }

    @Override
    public IMenu createButton(IButton button) {
        buttonCache.add(button);
        return this;
    }

    @Override
    public IButton getButton(Predicate<IButton> predicate) {
        return this.buttonCache.find(predicate);
    }

    @Override
    public Cache<IButton> getButtons() {
        return this.buttonCache;
    }

    @Override
    public IMenu load() {
        return this;
    }

    @Override
    public void open(Player player) {
        player.openInventory(this.inventory);
    }

    @Override
    public void close(Player player) {
        if (isMenuFactory(player))
            player.closeInventory();
    }

    @Override
    public Inventory getInventory() {
        return this.getInventory();
    }


    public static boolean isMenuFactory(Player player) {
        if (player.getOpenInventory() != null
                &&
                player.getOpenInventory().getTopInventory() != null
                &&
                player.getOpenInventory().getTopInventory().getHolder() instanceof MenuFactory) {
            return true;

        }

        return false;
    }
}
