package com.example.sheduler.entity;

public enum Trigger {
    FIVE_MINUTES("-PT5M"),
    TEN_MINUTES("-PT10M"),
    FIFTEEN_MINUTES("-PT15M"),
    THIRTY_MINUTES("-PT30M"),
    ONE_HOUR("-PT1H"),
    TWO_HOURS("-PT2H"),
    ONE_DAY("-P1D"),
    TWO_DAYS("-P2D");

    String value;

    Trigger(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
