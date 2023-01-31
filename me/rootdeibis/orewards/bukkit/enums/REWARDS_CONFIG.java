package me.rootdeibis.orewards.bukkit.enums;

public enum REWARDS_CONFIG {

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

        REWARDS_CONFIG(String path) {
            this.path = path;
        }

        public String parse() {
            return this.path;
        }

}
