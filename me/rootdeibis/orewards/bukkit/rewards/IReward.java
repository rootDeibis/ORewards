package me.rootdeibis.orewards.bukkit.rewards;

import me.rootdeibis.orewards.bukkit.Main;
import me.rootdeibis.orewards.bukkit.db.DatabaseQuerys;
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
        return this.getConfig().getString(CONFIG.NAME.parse());
    }

    public EnumRewardTypes getType() {
        return EnumRewardTypes.valueOf(this.getConfig().getString(CONFIG.TYPE.parse()).toLowerCase());
    }

    abstract public IFile getConfig();

    abstract public boolean canClaim(Player player);

     public void claim(Player player) {

     }


    public String getPermission() {
        return this.getConfig().getString(CONFIG.PERMISSION.parse());
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
        EnumRewardTypes type = EnumRewardTypes.valueOf(file.getString(CONFIG.TYPE.parse()));

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


    public static enum CONFIG {

        NAME("name"),

        PERMISSION("permission"),

        TYPE("type"),

        STATUS_AVAILABLE_MATERIAL("status-available.material"),
        STATUS_AVAILABLE_TITLE("status-available.title"),

        STATUS_AVAILABLE_LORE("status-available.lore"),


        STATUS_PERM_MATERIAL("status-need-permission.material"),
        STATUS_PERM_TITLE("status-need-permission.title"),

        STATUS_PERM_LORE("status-need-permission.lore"),

        STATUS_NOT_AVAILABLE_MATERIAL("status-not-available.material"),
        STATUS_NOT_AVAILABLE_TITLE("status-not-available.title"),

        STATUS_NOT_AVAILABLE_LORE("status-not-available.lore");

        private final String path;

        CONFIG(String path) {
            this.path = path;
        }

        public String parse() {
            return this.path;
        }
    }

}
