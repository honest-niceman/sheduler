package com.example.sheduler.entity;

public class UserSettings {
    String transparency;
    Trigger trigger;
    String subGroup;

    public UserSettings(String transparency, Trigger trigger, String subGroup) {
        this.transparency = transparency;
        this.trigger = trigger;
        this.subGroup = subGroup;
    }

    public String getTransparency() {
        return transparency;
    }

    public void setTransparency(String transparency) {
        this.transparency = transparency;
    }

    public Trigger getTrigger() {
        return trigger;
    }

    public void setTrigger(Trigger trigger) {
        this.trigger = trigger;
    }

    public String getSubGroup() {
        return subGroup;
    }

    public void setSubGroup(String subGroup) {
        this.subGroup = subGroup;
    }
}
