package me.rootdeibis.orewards.common.guifactory;

import me.rootdeibis.orewards.common.colors;
import me.rootdeibis.orewards.common.function.Functions;
import me.rootdeibis.orewards.common.guifactory.interfaces.IButton;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.List;

public class ButtonFactory implements IButton {

    private final MenuFactory container;

    private final int slot;
    private Functions.RFunction<String> material;
    private Functions.RFunction<String> title;
    private Functions.RFunction<List<String>> lore;

    private ItemStack itemStack;

    private Functions.FunctionV2<InventoryClickEvent, MenuFactory> action = (e,b) -> {};


    public ButtonFactory(MenuFactory container, int slot) {
        this.container = container;
        this.slot = slot;
    }

    @Override
    public String getMaterial() {
        return material.apply();
    }

    @Override
    public String getTitle() {
        return title.apply();
    }

    @Override
    public List<String> getLore() {
        return lore.apply();
    }

    @Override
    public MenuFactory getContainer() {
        return this.container;
    }

    @Override
    public int getSlot() {
        return this.slot;
    }

    @Override
    public IButton setMaterial(Functions.RFunction<String> material) {
        this.material = material;

        return this;
    }

    @Override
    public IButton setTitle(Functions.RFunction<String> title) {
        this.title = title;
        return this;
    }

    @Override
    public IButton setLore(Functions.RFunction<List<String>> lore) {
        this.lore = lore;
        return this;
    }

    @Override
    public ItemStack getItem() {
        return this.itemStack;
    }

    @Override
    public IButton setClickAction(Functions.FunctionV2<InventoryClickEvent, MenuFactory> action) {
        this.action = action;
        return this;
    }

    @Override
    public void click(InventoryClickEvent event, MenuFactory factory) {
        this.action.apply(event, factory);
    }

    @Override
    public void build() {

        String[] $material_data = this.getMaterial().split(":");

        Material $material = Material.valueOf($material_data[0]);

        byte $material_type = 0;

        if ($material_data.length > 1) $material_type = Byte.valueOf($material_data[1]);

        this.itemStack = new ItemStack($material);

        MaterialData materialData = new MaterialData($material,$material_type);

        this.itemStack.setData(materialData);

        ItemMeta meta = this.itemStack.getItemMeta();

        meta.setDisplayName(colors.colors(this.getTitle()));

        meta.setLore(colors.colors(this.getLore()));

        this.itemStack.setItemMeta(meta);
    }
}
