package me.rootdeibis.orewards.bukkit.rewards.types;

import me.rootdeibis.orewards.bukkit.rewards.IRewards;
import me.rootdeibis.orewards.common.filemanagers.IFile;
import org.bukkit.entity.Player;

public class TimeReward extends IRewards {

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
        return false;
    }

    @Override
    public void claim(Player player) {

    }
}
