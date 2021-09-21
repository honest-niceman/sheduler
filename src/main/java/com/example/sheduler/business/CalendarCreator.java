package com.example.sheduler.business;

import com.example.sheduler.entity.ScheduleItem;
import com.example.sheduler.entity.UserSettings;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.VAlarm;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.*;
import net.fortuna.ical4j.util.UidGenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.GregorianCalendar;
import java.util.List;

public class CalendarCreator {
    public File createCalendar(List<ScheduleItem> itemList, UserSettings userSettings, long chatId, String groupNumber) throws ValidationException, IOException {
        System.out.println("CalendarCreator createCalendar start");
        // Устанавливаем часовой пояс
        CalendarBuilder builder = new CalendarBuilder();
        TimeZoneRegistry registry = builder.getRegistry();
        TimeZone tz = registry.getTimeZone("Europe/Moscow");

        java.util.Calendar calendarStartTime = new GregorianCalendar();
        calendarStartTime.setTimeZone(tz);

        // Создаем сам календарь
        Calendar icsCalendar = new Calendar();
        icsCalendar.getProperties().add(new ProdId("-//vlasovgv"));
        icsCalendar.getProperties().add(CalScale.GREGORIAN);
        icsCalendar.getProperties().add(Version.VERSION_2_0);

        java.util.Calendar startDate = new GregorianCalendar();
        java.util.Calendar endDate = new GregorianCalendar();
        String eventName = "";
        DateTime start = null;
        DateTime end = null;
        VEvent meeting = null;
        VAlarm reminder = null;
        UidGenerator ug = new UidGenerator("1");

        for (int i = 0; i < itemList.size(); i++) {
            startDate.setTimeZone(tz);
            startDate.set(java.util.Calendar.YEAR, Integer.parseInt(itemList.get(i).getDtstart().substring(0, 4)));
            startDate.set(java.util.Calendar.MONTH, Integer.parseInt(itemList.get(i).getDtstart().substring(4, 6)) - 1);
            startDate.set(java.util.Calendar.DAY_OF_MONTH, Integer.parseInt(itemList.get(i).getDtstart().substring(6, 8)));
            startDate.set(java.util.Calendar.HOUR_OF_DAY, Integer.parseInt(itemList.get(i).getDtstart().substring(8, 10)));
            startDate.set(java.util.Calendar.MINUTE, Integer.parseInt(itemList.get(i).getDtstart().substring(10, 12)));
            startDate.set(java.util.Calendar.SECOND, Integer.parseInt(itemList.get(i).getDtstart().substring(12, 14)));


            endDate.setTimeZone(tz);
            endDate.set(java.util.Calendar.YEAR, Integer.parseInt(itemList.get(i).getDtend().substring(0, 4)));
            endDate.set(java.util.Calendar.MONTH, Integer.parseInt(itemList.get(i).getDtend().substring(4, 6)) - 1);
            endDate.set(java.util.Calendar.DAY_OF_MONTH, Integer.parseInt(itemList.get(i).getDtend().substring(6, 8)));
            endDate.set(java.util.Calendar.HOUR_OF_DAY, Integer.parseInt(itemList.get(i).getDtend().substring(8, 10)));
            endDate.set(java.util.Calendar.MINUTE, Integer.parseInt(itemList.get(i).getDtend().substring(10, 12)));
            endDate.set(java.util.Calendar.SECOND, Integer.parseInt(itemList.get(i).getDtend().substring(12, 14)));

            // Создаем событие
            eventName = itemList.get(i).getSummary();
            start = new DateTime(startDate.getTime());
            end = new DateTime(endDate.getTime());
            meeting = new VEvent(start, end, eventName);

            reminder = new VAlarm(new Dur(-1000 * 60 * userSettings.getTrigger().getValue()));
            reminder.getProperties().add(Action.DISPLAY);
            reminder.getProperties().add(new Description(itemList.get(i).getSummary() + " начнется через "
                    + userSettings.getTrigger().getValue() + " минут!"));

            meeting.getProperties().add(ug.generateUid());
            meeting.getAlarms().add(reminder);

            icsCalendar.getComponents().add(meeting);
        }

        System.out.println("Before file creation");

        String diectoryName = "C:/Users/vlasovgv/IdeaProjects/sheduler/" + chatId;

        Files.createDirectory(Paths.get(diectoryName));

        File f = new File(diectoryName + "/" + groupNumber + ".ics");

        FileOutputStream fos = new FileOutputStream(f); // create a file output stream around f
        CalendarOutputter out = new CalendarOutputter();
        out.output(icsCalendar, fos);

        System.out.println("File successfully created");
        return f;
    }
}
