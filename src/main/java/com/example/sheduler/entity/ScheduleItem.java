package com.example.sheduler.entity;

public class ScheduleItem extends MetaData {
    private String summary;
    private String url;
    private String description;
    private String location;

    public ScheduleItem(String dtstamp, String uid, String dtstart, String dtend, String transparency, String summary, String url, String description, String location) {
        super(dtstamp, uid, dtstart, dtend, transparency);
        this.summary = summary;
        this.url = url;
        this.description = description;
        this.location = location;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
