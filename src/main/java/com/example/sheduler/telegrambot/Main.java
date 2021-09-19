package com.example.sheduler.telegrambot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Main {
    public static void main(String[] args) {
        ApiContextInitializer.init();

        System.out.println("Context initialized");

        SchedulerBot schedulerBot = new SchedulerBot();

        System.out.println("SsauBot created");

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

        try {
            telegramBotsApi.registerBot(schedulerBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }
}
