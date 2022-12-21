package com.app.b4s.model;

public class WeeklyTimeTable {
    public String mon_name,tue_name,wed_name,thurs_name,fri_name,satur_name,sun_name;

    public WeeklyTimeTable(String mon_name, String tue_name, String wed_name, String thurs_name, String fri_name, String satur_name, String sun_name) {
        this.mon_name = mon_name;
        this.tue_name = tue_name;
        this.wed_name = wed_name;
        this.thurs_name = thurs_name;
        this.fri_name = fri_name;
        this.satur_name = satur_name;
        this.sun_name = sun_name;
    }

    public String getMon_name() {
        return mon_name;
    }

    public void setMon_name(String mon_name) {
        this.mon_name = mon_name;
    }

    public String getTue_name() {
        return tue_name;
    }

    public void setTue_name(String tue_name) {
        this.tue_name = tue_name;
    }

    public String getWed_name() {
        return wed_name;
    }

    public void setWed_name(String wed_name) {
        this.wed_name = wed_name;
    }

    public String getThurs_name() {
        return thurs_name;
    }

    public void setThurs_name(String thurs_name) {
        this.thurs_name = thurs_name;
    }

    public String getFri_name() {
        return fri_name;
    }

    public void setFri_name(String fri_name) {
        this.fri_name = fri_name;
    }

    public String getSatur_name() {
        return satur_name;
    }

    public void setSatur_name(String satur_name) {
        this.satur_name = satur_name;
    }

    public String getSun_name() {
        return sun_name;
    }

    public void setSun_name(String sun_name) {
        this.sun_name = sun_name;
    }
}
