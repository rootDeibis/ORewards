package me.rootdeibis.orewards.bukkit.commands;

import me.rootdeibis.orewards.bukkit.rewards.guis.GUICategories;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ORewardsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (sender instanceof Player)
            GUICategories.get().open((Player) sender);


        return false;
    }
}
