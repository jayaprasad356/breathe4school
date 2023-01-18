package com.app.b4s.model;

public class WeekDay {
    String day,date;

    public WeekDay(String day, String date) {
        this.day = day;
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
