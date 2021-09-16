package com.example.sheduler;

import com.example.sheduler.entity.*;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        UserSettings userSettings = new UserSettings(Transparency.TRANSPARENT.toString(), Trigger.FIVE_MINUTES.getValue(), "1");

        Document document = null;
        String url = "https://ssau.ru/rasp?groupId=755933211&selectedWeek=";

        Parser p = new Parser();
        List<ScheduleItem> itemList = p.getAllScheduleItemsForGroup(document, url, userSettings);

        System.out.println(itemList.size());

        PrintWriter writer = new PrintWriter("calendar.ics", "UTF-8");
        writer.println(new TextCreator().buildFileText(itemList, userSettings));
        writer.close();
    }


}