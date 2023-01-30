package me.rootdeibis.orewards.common.events;

public abstract class Event  {

    private String name;

    public String getName() {
        if (this.name == null) this.name = this.getClass().getSimpleName();

        return this.name;
    }

    public void execute() {

    }
}
