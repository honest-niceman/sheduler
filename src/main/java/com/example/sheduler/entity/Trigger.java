package com.example.sheduler.entity;

public enum Trigger {
    FIVE_MINUTES(5),
    TEN_MINUTES(10),
    FIFTEEN_MINUTES(15),
    THIRTY_MINUTES(30),
    ONE_HOUR(60),
    TWO_HOURS(120),
    ONE_DAY(2400),
    TWO_DAYS(4800);

    int value;

    Trigger(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
