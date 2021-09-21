package com.example.sheduler.business;

public class StringParser {
    public String getLink(String url) {
        if (url.length() < 38) return null;
        if (url.length() == 38) {
            return url + "&selectedWeek=";
        } else if (url.length() == 53) {
            return url.substring(0, url.length() - 1);
        } else if (url.length() == 71) {
            return url.substring(0, url.length() - 19);
        } else return null;
    }
}
