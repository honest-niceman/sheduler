package com.example.sheduler;

import com.example.sheduler.entity.*;
import net.fortuna.ical4j.model.ValidationException;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, ValidationException {
        UserSettings userSettings = new UserSettings(Transparency.TRANSPARENT.toString(), Trigger.FIVE_MINUTES, "1");

        Document document = null;
        String url = "https://ssau.ru/rasp?groupId=755933211&selectedWeek=";

        Parser p = new Parser();
        List<ScheduleItem> itemList = p.getAllScheduleItemsForGroup(document, url, userSettings);

        System.out.println(itemList.size());

        new CalendarCreator().createCalendar(itemList,userSettings);
    }
}