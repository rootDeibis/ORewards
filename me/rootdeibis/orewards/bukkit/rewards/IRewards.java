package me.rootdeibis.orewards.bukkit.rewards;

import me.rootdeibis.orewards.bukkit.Main;
import me.rootdeibis.orewards.bukkit.db.DatabaseQuerys;
import me.rootdeibis.orewards.bukkit.rewards.types.EnumRewardTypes;
import me.rootdeibis.orewards.common.filemanagers.IFile;
import org.bukkit.entity.Player;

public abstract class IRewards {

    public String getName() {
        return this.getConfig().getString(CONFIG.NAME.parse());
    }

    public EnumRewardTypes getType() {
        return EnumRewardTypes.valueOf(this.getConfig().getString(CONFIG.TYPE.parse()).toLowerCase());
    }

    abstract public IFile getConfig();

    abstract public boolean canClaim(Player player);

    abstract public void claim(Player player);


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
