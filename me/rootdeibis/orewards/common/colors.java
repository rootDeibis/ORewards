package me.rootdeibis.orewards.common;

import org.bukkit.ChatColor;

import java.util.List;

@SuppressWarnings("unchecked")
public class colors {

    private static String colors(String std) {
        return ChatColor.translateAlternateColorCodes('&', std);
    }

    public static <T> T colors(T value) {

        if (value instanceof List) {
            return (T) ((List<T>) value).stream().map(str -> colors(String.valueOf(str)));
        } else if(value instanceof String) {
            return (T) colors(String.valueOf(value));
        }

        return value;
    }


}
