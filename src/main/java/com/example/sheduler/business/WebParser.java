package com.example.sheduler.business;

import com.example.sheduler.entity.ScheduleItem;
import com.example.sheduler.entity.UserSettings;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WebParser {

    public ArrayList<ScheduleItem> getAllScheduleItemsForGroup(Document doc, String url, UserSettings userSettings) throws IOException {
        ArrayList<ScheduleItem> itemList = new ArrayList<>();

        for (int i = 1; i < 19; i++) {
            System.out.println("Week №" + i + " parsing successfully started ");
            itemList.addAll(getAllScheduleItemsForWeek(doc, url + i, userSettings));
        }

        return itemList;
    }

    public ArrayList<ScheduleItem> getAllScheduleItemsForWeek(Document doc, String url, UserSettings userSettings) throws IOException {
        ArrayList<ScheduleItem> itemList = new ArrayList<>();

        doc = Jsoup.connect(url).header("Connection", "close").userAgent("Mozilla").get();

        Elements scheduleItems = null;

        scheduleItems = doc.select(".schedule__items");

        ArrayList<Node> dates = new ArrayList<>();

        //get dates
        List<Node> elements = scheduleItems.get(0).childNodes();
        for (int i = 0; i < 6; i++) {
            dates.add(elements.get(i + 1));
        }

        WebParser parser = new WebParser();
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
            //проверка что ячейка содержит только название одной пары
            List<Node> cells = nodes.get(i).childNodes();
            if (cells.size() == 1) {
                // Проверка что ячейка не пустая и в ней есть пара
                List<Node> childs = cells.get(0).childNodes();
                if (childs.size() > 1) {
                    //если эта пара только для одной подгруппы
                    if (childs.toString().contains("Подгруппы:")) {
                        //то проверяем нужна ли нам эта пара
                        if (getSubGroup(childs.toString()).equals(userSettings.getSubGroup())) {
                            getClassInfo(nodes, dates, classNumber, userSettings, scheduleItems, i, 0);
                        }
                    }
                    // иначе сразу записываем, тк пара общая
                    else {
                        getClassInfo(nodes, dates, classNumber, userSettings, scheduleItems, i, 0);
                    }
                }
            }
            //если же содержится в одной ячейке две пары
            else if (cells.size() == 2) {
                //первая пара в ячейке
                if (cells.get(0).childNodes().size() > 1 &&
                        getSubGroup(cells.get(0).childNodes().toString()).equals(userSettings.getSubGroup())) {

                    getClassInfo(nodes, dates, classNumber, userSettings, scheduleItems, i, 0);

                }
                //вторая половина ячейки
                else if (cells.get(1).childNodes().size() > 1 &&
                        getSubGroup(cells.get(1).childNodes().toString()).equals(userSettings.getSubGroup())) {

                    getClassInfo(nodes, dates, classNumber, userSettings, scheduleItems, i, 1);

                }
            }
        }
        return scheduleItems;
    }

    private void getClassInfo(ArrayList<Node> nodes, ArrayList<Node> dates, int classNumber, UserSettings userSettings, ArrayList<ScheduleItem> scheduleItems, int i, int cellNumber) {
        String dateStart;
        String dateEnd;
        String title;
        String teacherName;
        String classTypeLoc;
        List<Node> childs = nodes.get(i).childNodes().get(cellNumber).childNodes();

        dateStart = getDate(dates.get(i).childNodes().get(1).toString()) + getClassStartTime(classNumber);
        dateEnd = getDate(dates.get(i).childNodes().get(1).toString()) + getClassEndTime(classNumber);
        title = getTitle(childs.get(0).toString());
        teacherName = "Преподаватель: " + getTeacherName(childs.get(2).toString());
        classTypeLoc = getClassType(childs.get(0).toString()) +
                " " + getLocation(childs.get(1).toString());

        addScheduleItem(scheduleItems, userSettings, dateStart, dateEnd, title, teacherName, classTypeLoc);
    }

    private void addScheduleItem(ArrayList<ScheduleItem> scheduleItems, UserSettings userSettings, String dateStart, String dateEnd, String title, String teacherName, String classTypeLoc) {
        String dtstamp = "DTSTAMP:20210914T131208Z";
        String uid = "UID:20210914T131208Z-487081606@marudot.com";
        String url = "https://vk.com/honest_niceman";
        String transparency = userSettings.getTransparency();

        scheduleItems.add(new ScheduleItem(dtstamp, uid, dateStart, dateEnd, transparency, title, url, teacherName, classTypeLoc));
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