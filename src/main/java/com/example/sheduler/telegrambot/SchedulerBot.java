package com.example.sheduler.telegrambot;

import net.fortuna.ical4j.model.ValidationException;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;

public class SchedulerBot extends TelegramLongPollingBot {
    private static final String TOKEN = "2023437282:AAHg0TfTM22ZNWEULtS2ZRY7N7lEGAx9fP8";
    private static final String USERNAME = "ssau_scheduler_bot";


    public SchedulerBot() {
        super();
    }

    @Override
    public String getBotUsername() {
        return USERNAME;
    }

    @Override
    public String getBotToken() {
        return TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println("Update received");
        if (update.getMessage() != null) {
            long chatId = update.getMessage().getChatId();

            try {
                InputFile inputFile = new InputFile();
                System.out.println("Attempt to create file");
                inputFile.setMedia(new Generator().getFile(update.getMessage().getText(), chatId), "schedule.ics");

                System.out.println("Attempt to send file");
                execute(new SendDocument().setDocument(inputFile).setChatId(chatId));
            } catch (TelegramApiException | ValidationException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}
