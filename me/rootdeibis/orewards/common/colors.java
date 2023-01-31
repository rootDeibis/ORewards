package me.rootdeibis.orewards.common;

import org.bukkit.ChatColor;

import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class colors {

    public static String colors(String std) {
        return ChatColor.translateAlternateColorCodes('&', std);
    }

    public static List<String> colors(List<String> value) {
        return value.stream().map(colors::colors).collect(Collectors.toList());
    }


}
