package me.rootdeibis.orewards.bukkit.enums;

public enum CONFIG {

    GUI_CATEGORIES_TITLE("categories-gui.title"),
    GUI_CATEGORIES_ROWS("categories-gui.rows"),

    GUI_CATEGORY_TITLE("categories.%s.gui-title"),

    GUI_CATEGORY_ROWS("categories.%s.gui-rows"),
    GUI_BUTTON_MATERIAL("categories.%s.item.material"),

    GUI_BUTTON_TITLE("categories.%s.item.displayname"),

    GUI_BUTTON_SLOT("categories.%s.item.slot"),

    GUI_BUTTON_LORE("categories.%s.item.lore");

    private final String path;

    CONFIG(String path) {
        this.path = path;
    }


    public String parse() {
        return this.path;
    }
}
