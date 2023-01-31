package me.rootdeibis.orewards.bukkit.rewards;

import me.rootdeibis.orewards.bukkit.Main;
import me.rootdeibis.orewards.bukkit.db.DatabaseQuerys;
import me.rootdeibis.orewards.bukkit.enums.REWARDS_CONFIG;
import me.rootdeibis.orewards.bukkit.rewards.types.EnumRewardTypes;
import me.rootdeibis.orewards.bukkit.rewards.types.TimeReward;
import me.rootdeibis.orewards.common.TimeTool;
import me.rootdeibis.orewards.common.filemanagers.IFile;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public abstract class IReward {
    private static final HashMap<UUID, Long> untils = new HashMap<>();

    public String getName() {
        return this.getConfig().getString(REWARDS_CONFIG.NAME.parse());
    }

    public EnumRewardTypes getType() {
        return EnumRewardTypes.valueOf(this.getConfig().getString(REWARDS_CONFIG.TYPE.parse()).toLowerCase());
    }

    abstract public IFile getConfig();

    abstract public boolean canClaim(Player player);

     public void claim(Player player) {

     }


    public String getPermission() {
        return this.getConfig().getString(REWARDS_CONFIG.PERMISSION.parse());
    }
    public boolean needPermission() {
        if (this.getPermission() != null) {
            return true;
        } else {
            return false;
        }

    }


    public boolean validate() {
        return Main.getDB().exec(String.format(DatabaseQuerys.CREATE_TABLE.parse(), this.getName()));
    }

    public boolean update(String date_format, UUID uuid) {
        String until = String.valueOf(TimeTool.addToDate(date_format));

        return Main.getDB().exec(String.format(DatabaseQuerys.UPDATE_USER.parse(), this.getName(), until, uuid.toString()));
    }


    public static IReward resolve(IFile file) {
        EnumRewardTypes type = EnumRewardTypes.valueOf(file.getString(REWARDS_CONFIG.TYPE.parse()));

        if (type == EnumRewardTypes.TIMED_REWARD) {
            return new TimeReward(file);
        }

        return null;
    }


    public long getUntil(UUID uuid) {
        if (!untils.containsKey(uuid)) {
            long until = TimeTool.addToDate("1s").getTime();

            String query = String.format(DatabaseQuerys.SELECT_USER.parse(), this.getName(), uuid.toString());
            String execQuery = String.format(DatabaseQuerys.CREATE_USER.parse(), this.getName(), uuid.toString(), until);

            if (!Main.getDB().has(query)) {
                Main.getDB().exec(execQuery);
            } else {
                until = Long.parseLong(Main.getDB().get(query, "until").get("until").toString());
            }

            untils.put(uuid, until);

        }

        return untils.get(uuid);
    }




}
