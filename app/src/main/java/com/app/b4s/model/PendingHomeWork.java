package com.app.b4s.model;

public class PendingHomeWork {
    String sub_name,topic,date;

    public PendingHomeWork(String sub_name, String topic, String date) {
        this.sub_name = sub_name;
        this.topic = topic;
        this.date = date;
    }

    public String getSub_name() {
        return sub_name;
    }

    public void setSub_name(String sub_name) {
        this.sub_name = sub_name;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
