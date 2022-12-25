package com.app.b4s.model.Dcm;

public class Summarymodel {
    String sub_name,topic,time;


    public Summarymodel(String sub_name, String topic, String time) {
        this.sub_name = sub_name;
        this.topic = topic;
        this.time = time;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
