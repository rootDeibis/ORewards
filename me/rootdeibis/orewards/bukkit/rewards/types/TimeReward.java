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
        return this.getStatus(player) == REWARD_STATUS.AVAILABLE;
    }

    @Override
    public REWARD_STATUS getStatus(Player player) {
        if (this.needPermission() && player.hasPermission(this.getPermission())) {
            return REWARD_STATUS.NEED_PERMISSION;
        } else if(new Date().before(new Date(this.getUntil(player.getUniqueId())))) {
            return REWARD_STATUS.NOT_AVAILABLE;
        } else {
            return REWARD_STATUS.AVAILABLE;
        }


    }


}
