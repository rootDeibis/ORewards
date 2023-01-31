package me.rootdeibis.orewards.common.guifactory.listener;

import me.rootdeibis.orewards.common.guifactory.MenuFactory;
import me.rootdeibis.orewards.common.guifactory.interfaces.IButton;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getWhoClicked() instanceof Player) {
            Player player = (Player) e.getWhoClicked();

            if (MenuFactory.isMenuFactory(player)) {
                e.setCancelled(true);

                MenuFactory menuFactory = (MenuFactory) e.getInventory().getHolder();

                IButton button = menuFactory.getButton(btn -> btn.getSlot() == e.getSlot());

                if (button != null) button.click(e, menuFactory);


            }
        }


    }
}
