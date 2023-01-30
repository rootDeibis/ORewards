package me.rootdeibis.orewards.common.guifactory;

import me.rootdeibis.orewards.common.function.Functions;
import me.rootdeibis.orewards.common.guifactory.interfaces.IButton;

import java.util.List;

public class ButtonFactory implements IButton {

    private final MenuFactory container;
    private Functions.RFunction<String> material;
    private Functions.RFunction<String> title;
    private Functions.RFunction<List<String>> lore;


    public ButtonFactory(MenuFactory container) {
        this.container = container;
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
}
