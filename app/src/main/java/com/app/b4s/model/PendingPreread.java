package com.app.b4s.model;

public class PendingPreread {
    String sub,content,time;

    public PendingPreread(String sub, String content, String time) {
        this.sub = sub;
        this.content = content;
        this.time = time;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
