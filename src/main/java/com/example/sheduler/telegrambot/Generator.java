package com.example.sheduler.telegrambot;

import com.example.sheduler.entity.*;
import net.fortuna.ical4j.model.ValidationException;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Generator {
    public File getFile(String url, long chatId) throws ValidationException, IOException {
        System.out.println("Generator getFile start");

        UserSettings userSettings = new UserSettings(Transparency.TRANSPARENT.toString(), Trigger.FIVE_MINUTES, "1");

        Document document = null;

        //String url = "https://ssau.ru/rasp?groupId=755933211&selectedWeek=";

        Parser p = new Parser();
        List<ScheduleItem> itemList = p.getAllScheduleItemsForGroup(document, url, userSettings);

        return new CalendarCreator().createCalendar(itemList, userSettings, chatId);
    }
}