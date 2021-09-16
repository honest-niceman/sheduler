package com.example.sheduler.entity;

public class MetaData {
    private String dtstamp;
    private String uid;
    private String dtstart;
    private String dtend;
    private String transparency;

    public MetaData(String dtstamp, String uid, String dtstart, String dtend, String transparency) {
        this.dtstamp = dtstamp;
        this.uid = uid;
        this.dtstart = dtstart;
        this.dtend = dtend;
        this.transparency = transparency;
    }

    public String getDtstamp() {
        return dtstamp;
    }

    public void setDtstamp(String dtstamp) {
        this.dtstamp = dtstamp;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDtstart() {
        return dtstart;
    }

    public void setDtstart(String dtstart) {
        this.dtstart = dtstart;
    }

    public String getDtend() {
        return dtend;
    }

    public void setDtend(String dtend) {
        this.dtend = dtend;
    }

    public String getTransparency() {
        return transparency;
    }

    public void setTransparency(String transparency) {
        this.transparency = transparency;
    }
}
