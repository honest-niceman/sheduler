package com.example.sheduler.entity;

import java.util.List;

public class TextCreator {
    public StringBuilder buildFileText(List<ScheduleItem> itemList, UserSettings userSettings) {
        StringBuilder builder = new StringBuilder();

        builder.append("BEGIN:VCALENDAR\n" +
                "VERSION:2.0\n" +
                "X-WR-CALNAME:Test\n" +
                "X-APPLE-CALENDAR-COLOR:#CC73E1\n");

        for (int i = 0; i < itemList.size(); i++) {
            builder.append("BEGIN:VEVENT\n" +
                    "CREATED:20210916T125434Z\n"+
                    itemList.get(i).getDescription() + "\n" +
                    itemList.get(i).getDtend() + "\n" +
                    itemList.get(i).getDtstamp() + "\n" +
                    itemList.get(i).getDtstart() + "\n" +
                    "LAST-MODIFIED:20210916T125513Z\n"+
                    itemList.get(i).getLocation() + "\n" +
                    "SEQUENCE:0\n"+
                    itemList.get(i).getSummary() + "\n" +
                    itemList.get(i).getUid() + "\n" +
                    itemList.get(i).getUrl() + "\n" +
                    "END:VEVENT\r\n"
            );
        }

        builder.append("BEGIN:VTIMEZONE\n" +
                "TZID:Europe/Samara\n" +
                "X-LIC-LOCATION:Europe/Samara\n" +
                "BEGIN:STANDARD\n" +
                "DTSTART:19190701T032020\n" +
                "RDATE:19190701T032020\n" +
                "TZNAME:+03\n" +
                "TZOFFSETFROM:+032020\n" +
                "TZOFFSETTO:+0300\n" +
                "END:STANDARD\n" +
                "BEGIN:STANDARD\n" +
                "DTSTART:19300621T000000\n" +
                "RDATE:19300621T000000\n" +
                "RDATE:19911020T030000\n" +
                "RDATE:20110327T020000\n" +
                "TZNAME:+04\n" +
                "TZOFFSETFROM:+0300\n" +
                "TZOFFSETTO:+0400\n" +
                "END:STANDARD\n" +
                "BEGIN:STANDARD\n" +
                "DTSTART:19350127T000000\n" +
                "RDATE:19350127T000000\n" +
                "TZNAME:+04\n" +
                "TZOFFSETFROM:+0400\n" +
                "TZOFFSETTO:+0400\n" +
                "END:STANDARD\n" +
                "BEGIN:DAYLIGHT\n" +
                "DTSTART:19810401T000000\n" +
                "RRULE:FREQ=YEARLY;UNTIL=19840331T200000Z;BYMONTH=4\n" +
                "TZNAME:+05\n" +
                "TZOFFSETFROM:+0400\n" +
                "TZOFFSETTO:+0500\n" +
                "END:DAYLIGHT\n" +
                "BEGIN:STANDARD\n" +
                "DTSTART:19811001T000000\n" +
                "RRULE:FREQ=YEARLY;UNTIL=19830930T190000Z;BYMONTH=10\n" +
                "TZNAME:+04\n" +
                "TZOFFSETFROM:+0500\n" +
                "TZOFFSETTO:+0400\n" +
                "END:STANDARD\n" +
                "BEGIN:STANDARD\n" +
                "DTSTART:19840930T030000\n" +
                "RRULE:FREQ=YEARLY;UNTIL=19880924T220000Z;BYMONTH=9;BYDAY=-1SU\n" +
                "TZNAME:+04\n" +
                "TZOFFSETFROM:+0500\n" +
                "TZOFFSETTO:+0400\n" +
                "END:STANDARD\n" +
                "BEGIN:DAYLIGHT\n" +
                "DTSTART:19850331T020000\n" +
                "RRULE:FREQ=YEARLY;UNTIL=19880326T220000Z;BYMONTH=3;BYDAY=-1SU\n" +
                "TZNAME:+05\n" +
                "TZOFFSETFROM:+0400\n" +
                "TZOFFSETTO:+0500\n" +
                "END:DAYLIGHT\n" +
                "BEGIN:STANDARD\n" +
                "DTSTART:19890326T020000\n" +
                "RDATE:19890326T020000\n" +
                "RDATE:20100328T020000\n" +
                "TZNAME:+03/+04\n" +
                "TZOFFSETFROM:+0400\n" +
                "TZOFFSETTO:+0400\n" +
                "END:STANDARD\n" +
                "BEGIN:STANDARD\n" +
                "DTSTART:19890924T030000\n" +
                "RRULE:FREQ=YEARLY;UNTIL=19900929T230000Z;BYMONTH=9;BYDAY=-1SU\n" +
                "TZNAME:+03\n" +
                "TZOFFSETFROM:+0400\n" +
                "TZOFFSETTO:+0300\n" +
                "END:STANDARD\n" +
                "BEGIN:DAYLIGHT\n" +
                "DTSTART:19900325T020000\n" +
                "RDATE:19900325T020000\n" +
                "TZNAME:+04\n" +
                "TZOFFSETFROM:+0300\n" +
                "TZOFFSETTO:+0400\n" +
                "END:DAYLIGHT\n" +
                "BEGIN:STANDARD\n" +
                "DTSTART:19910331T020000\n" +
                "RDATE:19910331T020000\n" +
                "TZNAME:+02/+03\n" +
                "TZOFFSETFROM:+0300\n" +
                "TZOFFSETTO:+0300\n" +
                "END:STANDARD\n" +
                "BEGIN:STANDARD\n" +
                "DTSTART:19910929T030000\n" +
                "RDATE:19910929T030000\n" +
                "TZNAME:+03\n" +
                "TZOFFSETFROM:+0300\n" +
                "TZOFFSETTO:+0300\n" +
                "END:STANDARD\n" +
                "BEGIN:DAYLIGHT\n" +
                "DTSTART:19920329T020000\n" +
                "RRULE:FREQ=YEARLY;UNTIL=20090328T220000Z;BYMONTH=3;BYDAY=-1SU\n" +
                "TZNAME:+05\n" +
                "TZOFFSETFROM:+0400\n" +
                "TZOFFSETTO:+0500\n" +
                "END:DAYLIGHT\n" +
                "BEGIN:STANDARD\n" +
                "DTSTART:19920927T030000\n" +
                "RRULE:FREQ=YEARLY;UNTIL=19950923T220000Z;BYMONTH=9;BYDAY=-1SU\n" +
                "TZNAME:+04\n" +
                "TZOFFSETFROM:+0500\n" +
                "TZOFFSETTO:+0400\n" +
                "END:STANDARD\n" +
                "BEGIN:STANDARD\n" +
                "DTSTART:19961027T030000\n" +
                "RRULE:FREQ=YEARLY;UNTIL=20091024T220000Z;BYMONTH=10;BYDAY=-1SU\n" +
                "TZNAME:+04\n" +
                "TZOFFSETFROM:+0500\n" +
                "TZOFFSETTO:+0400\n" +
                "END:STANDARD\n" +
                "BEGIN:STANDARD\n" +
                "DTSTART:20101031T030000\n" +
                "RDATE:20101031T030000\n" +
                "TZNAME:+03\n" +
                "TZOFFSETFROM:+0400\n" +
                "TZOFFSETTO:+0300\n" +
                "END:STANDARD\n" +
                "END:VTIMEZONE\n" +
                "END:VCALENDAR");

        return builder;
    }
}
