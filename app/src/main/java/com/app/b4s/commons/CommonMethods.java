package com.app.b4s.commons;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CommonMethods {
    public String militaryToOrdinaryTime(int milTime) {
        int hour = milTime / 100;
        int min = milTime % 100;
        String period;

        if (hour < 0 || hour > 24 || min < 0 || min > 59) {
            return "";
        } else if (hour > 12) {
            hour = hour - 12;
            period = "PM";
        } else {
            period = "AM";
        }
        if (hour == 0) {
            hour = 12;
        } else if (min == 0) {
            String ordTime = hour + " " + period;
            return ordTime;
        } else if (min < 10 && min > 0) {
            String min1 = String.valueOf(min);
            min1 = "0" + min1;
            String ordTime = hour + ":" + min1 + " " + period;
            return ordTime;
        }
        String ordTime = hour + ":" + min + " " + period;
        return ordTime;
    }
    public String ordinaryToMilitaryTime(String time){

        SimpleDateFormat inputFormat = new SimpleDateFormat("hh:mm a");
        SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm");
        java.util.Date date = null;
        try {
            date = inputFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String militaryTime = outputFormat.format(date);


        return militaryTime.replace(":", "");
    }

    public String getCurrentMilitaryTime() {
        Calendar c = Calendar.getInstance();
        int minute = c.get(Calendar.MINUTE);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        if (minute < 10 && minute > 0) {
            String minut = "0" + minute;
            String time = String.valueOf(hour) + String.valueOf(minut);
            return time;
        }

        String time = String.valueOf(hour) + String.valueOf(minute);
        return time;
    }
    public String dateFomater(String dateString){
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date date = null;
        try {
            date = inputFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedDate = outputFormat.format(date);
        return formattedDate;
    }

}
