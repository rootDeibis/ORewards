package me.rootdeibis.orewards.bukkit.rewards.types;

import me.rootdeibis.orewards.bukkit.rewards.IReward;
import me.rootdeibis.orewards.common.filemanagers.IFile;
import org.bukkit.entity.Player;

import java.util.Date;

public class TimeReward extends IReward {


    private final IFile configuration;

    public TimeReward(IFile configuration) {
        this.configuration = configuration;
    }

    @Override
    public IFile getConfig() {
        return this.configuration;
    }

    @Override
    public boolean canClaim(Player player) {
        if (this.needPermission() && ! player.hasPermission(this.getPermission())) return false;

        return !new Date().after(new Date(this.getUntil(player.getUniqueId())));
    }




}
