package com.example.sheduler.entity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    public ArrayList<ScheduleItem> getAllScheduleItemsForGroup(Document doc, String url, UserSettings userSettings) throws IOException {
        ArrayList<ScheduleItem> itemList = new ArrayList<>();

        for (int i = 1; i < 19; i++) {
            itemList.addAll(getAllScheduleItemsForWeek(doc, url + i, userSettings));
        }

        return itemList;
    }

    public ArrayList<ScheduleItem> getAllScheduleItemsForWeek(Document doc, String url, UserSettings userSettings) throws IOException {
        ArrayList<ScheduleItem> itemList = new ArrayList<>();

        doc = Jsoup.connect(url).userAgent("Mozilla").get();

        Elements scheduleItems = null;

        scheduleItems = doc.select(".schedule__items");

        ArrayList<Node> dates = new ArrayList<>();

        //get dates
        List<Node> elements = scheduleItems.get(0).childNodes();
        for (int i = 0; i < 6; i++) {
            dates.add(elements.get(i + 1));
        }

        Parser parser = new Parser();
        //get first classes (start at 8:00 end at 9:35)
        ArrayList<Node> currentNodes = new ArrayList<>();
        for (int i = 8; i < 14; i++) {
            currentNodes.add(elements.get(i));
        }
        itemList.addAll(parser.getScheduledItemsForClassNumber(currentNodes, dates, 0, userSettings));
        currentNodes.clear();

        //get second classes (start at 9:45 end at 11:20)
        for (int i = 15; i < 21; i++) {
            currentNodes.add(elements.get(i));
        }
        itemList.addAll(parser.getScheduledItemsForClassNumber(currentNodes, dates, 1, userSettings));
        currentNodes.clear();

        //get third classes (start at 11:30 end at 13:05)
        for (int i = 22; i < 28; i++) {
            currentNodes.add(elements.get(i));
        }
        itemList.addAll(parser.getScheduledItemsForClassNumber(currentNodes, dates, 2, userSettings));
        currentNodes.clear();

        //get fourth classes (start at 13:30 end at 15:05)
        for (int i = 29; i < 35; i++) {
            currentNodes.add(elements.get(i));
        }
        itemList.addAll(parser.getScheduledItemsForClassNumber(currentNodes, dates, 3, userSettings));
        currentNodes.clear();

        //get fifth classes (start at 15:15 end at 16:50)
        for (int i = 36; i < 42; i++) {
            currentNodes.add(elements.get(i));
        }
        itemList.addAll(parser.getScheduledItemsForClassNumber(currentNodes, dates, 4, userSettings));
        currentNodes.clear();

        //get six classes (start at 17:00 end at 18:35)
        for (int i = 43; i < 49; i++) {
            currentNodes.add(elements.get(i));
        }
        itemList.addAll(parser.getScheduledItemsForClassNumber(currentNodes, dates, 5, userSettings));
        currentNodes.clear();

        //get seven classes (start at 18:45 end at 20:15)
        for (int i = 50; i < 56; i++) {
            currentNodes.add(elements.get(i));
        }
        itemList.addAll(parser.getScheduledItemsForClassNumber(currentNodes, dates, 6, userSettings));
        currentNodes.clear();

        //get seven classes (start at 20:25 end at 21:55)
        for (int i = 57; i < 62; i++) {
            currentNodes.add(elements.get(i));
        }
        itemList.addAll(parser.getScheduledItemsForClassNumber(currentNodes, dates, 7, userSettings));
        currentNodes.clear();

        return itemList;
    }

    public ArrayList<ScheduleItem> getScheduledItemsForClassNumber(ArrayList<Node> nodes, ArrayList<Node> dates, int classNumber, UserSettings userSettings) {
        ArrayList<ScheduleItem> scheduleItems = new ArrayList<>();
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).childNodes().size() == 1) {
                if (nodes.get(i).childNodes().get(0).childNodes().size() > 1) {
                    if(nodes.get(i).childNodes().get(0).childNodes().toString().contains("Подгруппы:")){
                        if (userSettings.getSubGroup().equals(getSubGroup(nodes.get(i).childNodes().get(0).childNodes().toString()))) {
                            String dateStart = getDate(dates.get(i).childNodes().get(1).toString()) + "T" + getClassStartTime(classNumber);
                            String dateEnd = getDate(dates.get(i).childNodes().get(1).toString()) + "T" + getClassEndTime(classNumber);
                            String title = getTitle(nodes.get(i).childNodes().get(0).childNodes().get(0).toString());
                            String teacherName = "Преподаватель: " + getTeacherName(nodes.get(i).childNodes().get(0).childNodes().get(2).toString());
                            String classTypeLoc = getClassType(nodes.get(i).childNodes().get(0).childNodes().get(0).toString()) +
                                    " " + getLocation(nodes.get(i).childNodes().get(0).childNodes().get(1).toString());

                            fillArray(scheduleItems, userSettings, dateStart, dateEnd, title, teacherName, classTypeLoc);
                        }
                    } else {
                        String dateStart = getDate(dates.get(i).childNodes().get(1).toString()) + "T" + getClassStartTime(classNumber);
                        String dateEnd = getDate(dates.get(i).childNodes().get(1).toString()) + "T" + getClassEndTime(classNumber);
                        String title = getTitle(nodes.get(i).childNodes().get(0).childNodes().get(0).toString());
                        String teacherName = "Преподаватель: " + getTeacherName(nodes.get(i).childNodes().get(0).childNodes().get(2).toString());
                        String classTypeLoc = getClassType(nodes.get(i).childNodes().get(0).childNodes().get(0).toString()) +
                                " " + getLocation(nodes.get(i).childNodes().get(0).childNodes().get(1).toString());

                        fillArray(scheduleItems, userSettings, dateStart, dateEnd, title, teacherName, classTypeLoc);
                    }
                }
            }
            if (nodes.get(i).childNodes().size() == 2) {
                //первая половина ячейки
                if (nodes.get(i).childNodes().get(0).childNodes().size() > 1) {
                    if (userSettings.getSubGroup().equals(getSubGroup(nodes.get(i).childNodes().get(0).childNodes().toString()))) {
                        String dateStart = getDate(dates.get(i).childNodes().get(1).toString()) + "T" + getClassStartTime(classNumber);
                        String dateEnd = getDate(dates.get(i).childNodes().get(1).toString()) + "T" + getClassEndTime(classNumber);
                        String title = getTitle(nodes.get(i).childNodes().get(0).childNodes().get(0).toString());
                        String teacherName = "Преподаватель: " + getTeacherName(nodes.get(i).childNodes().get(0).childNodes().get(2).toString());
                        String classTypeLoc = getClassType(nodes.get(i).childNodes().get(0).childNodes().get(0).toString()) +
                                " " + getLocation(nodes.get(i).childNodes().get(0).childNodes().get(1).toString());

                        fillArray(scheduleItems, userSettings, dateStart, dateEnd, title, teacherName, classTypeLoc);
                    }
                }

                //вторая половина ячейки
                if (nodes.get(i).childNodes().get(1).childNodes().size() > 1) {
                    if (userSettings.getSubGroup().equals(getSubGroup(nodes.get(i).childNodes().get(1).childNodes().toString()))) {
                        String dateStart = getDate(dates.get(i).childNodes().get(1).toString()) + "T" + getClassStartTime(classNumber);
                        String dateEnd = getDate(dates.get(i).childNodes().get(1).toString()) + "T" + getClassEndTime(classNumber);
                        String title = getTitle(nodes.get(i).childNodes().get(0).childNodes().get(0).toString());
                        String teacherName = "Преподаватель: " + getTeacherName(nodes.get(i).childNodes().get(0).childNodes().get(2).toString());
                        String classTypeLoc = getClassType(nodes.get(i).childNodes().get(0).childNodes().get(0).toString()) +
                                " " + getLocation(nodes.get(i).childNodes().get(0).childNodes().get(1).toString());

                        fillArray(scheduleItems, userSettings, dateStart, dateEnd, title, teacherName, classTypeLoc);
                    }
                }
            }
        }
        return scheduleItems;
    }

    private void fillArray(ArrayList<ScheduleItem> scheduleItems, UserSettings userSettings, String dateStart, String dateEnd, String title, String teacherName, String classTypeLoc) {
        String dtstamp = "DTSTAMP:20210914T131208Z";
        String uid = "UID:20210914T131208Z-487081606@marudot.com";
        String dtstart = "DTSTART;TZID=Europe/Samara:" + dateStart;
        String dtend = "DTEND;TZID=Europe/Samara:" + dateEnd;
        String summary = "SUMMARY:" + title;
        String url = "URL;VALUE=URI:" + "https://vk.com/honest_niceman";
        String description = "DESCRIPTION:" + teacherName;
        String location = "LOCATION:" + classTypeLoc;
        String transparency = "TRANSP:" + userSettings.getTransparency();

        scheduleItems.add(new ScheduleItem(dtstamp, uid, dtstart, dtend, transparency, summary, url, description, location));
    }

    public String getTitle(String s) {
        String after = "<div class=\"body-text schedule__discipline lesson-color lesson-color-type";
        return s.substring(s.indexOf(after) + after.length() + 5, s.indexOf("</div>")).trim();
    }

    public String getSubGroup(String s) {
        String after = "Подгруппы:";
        return s.substring(s.indexOf(after) + after.length(), s.indexOf("</span>")).trim();
    }

    public String getClassType(String s) {
        if (s.contains("lesson-color-type-1")) return "Лекция";
        if (s.contains("lesson-color-type-2")) return "Лабораторная";
        if (s.contains("lesson-color-type-3")) return "Практика";
        if (s.contains("lesson-color-type-4")) return "Другое";
        else return null;
    }

    public String getLocation(String s) {
        String after = "<div class=\"caption-text schedule__place\">";
        return s.substring(s.indexOf(after) + after.length(), s.indexOf("</div>")).trim();
    }

    public String getTeacherName(String s) {
        String after = "<a class=\"caption-text\" href=\"/rasp?staffId=484544778\">";
        String link = s.substring(s.indexOf("<div class=\"schedule__teacher\">") + after.length(), s.indexOf("</div>"));
        return link.substring(link.indexOf("\">") + 2, link.indexOf("</a>")).trim();
    }

    public String getDate(String s) {
        String after = "<div class=\"caption-text schedule__head-date\">";
        String d = s.substring(s.indexOf(after) + after.length(), s.indexOf("</div>")).trim();
        d = d.replace(".", "");
        String day = d.substring(0, 2);
        String month = d.substring(2, 4);
        String year = d.substring(4, 8);
        return year + month + day;
    }

    public String getClassStartTime(int i) {
        if (i == 0) return "080000";
        if (i == 1) return "094500";
        if (i == 2) return "113000";
        if (i == 3) return "133000";
        if (i == 4) return "151500";
        if (i == 5) return "170000";
        if (i == 6) return "184500";
        if (i == 7) return "202500";
        else return null;
    }

    public String getClassEndTime(int i) {
        if (i == 0) return "093500";
        if (i == 1) return "112000";
        if (i == 2) return "130500";
        if (i == 3) return "150500";
        if (i == 4) return "165000";
        if (i == 5) return "183500";
        if (i == 6) return "201500";
        if (i == 7) return "215500";
        else return null;
    }
}