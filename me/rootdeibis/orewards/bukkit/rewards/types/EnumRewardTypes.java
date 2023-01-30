package me.rootdeibis.orewards.bukkit.rewards.types;

public enum EnumRewardTypes {
    
    TIMED_REWARD("time"),

    DAILY_REWARD("daily"),
    FIRST_JOIN_REWARD("first");

    private final String type;

    EnumRewardTypes(String type) {
        this.type = type;
    }
}
