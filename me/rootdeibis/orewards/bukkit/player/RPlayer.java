package me.rootdeibis.orewards.bukkit.player;

import me.rootdeibis.orewards.bukkit.rewards.IRewards;
import me.rootdeibis.orewards.common.cache.Cache;
import org.bukkit.entity.Player;

import java.util.UUID;

public class RPlayer {


    private final Cache<IRewards> rewardsCache = new Cache<>();
    private final UUID uuid;

    public RPlayer(Player player) {
        this.uuid = player.getUniqueId();
    }


    private void loadRewards() {

    }
}
