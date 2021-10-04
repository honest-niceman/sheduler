package com.example.sheduler.telegrambot;

import com.example.sheduler.business.StringParser;
import net.fortuna.ical4j.model.ValidationException;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

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
                System.out.println("Attempt to create file");

                String url = new StringParser().getLink(update.getMessage().getText());

                System.out.println(url);

                if (url != null) {
                    InputFile inputFile = new InputFile();

                    String groupNumber = url.substring(url.length() - 23, url.length() - 14);

                    inputFile.setMedia(new Generator().getFile(url, chatId, groupNumber), groupNumber + ".ics");

                    System.out.println("Attempt to send file");
                    execute(new SendDocument().setDocument(inputFile).setChatId(chatId));

                }

            } catch (TelegramApiException | ValidationException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}
