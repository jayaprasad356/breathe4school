package com.app.b4s.model;

public class Subject {
    private String id,name;

    public Subject(String id, String subName) {
        this.id=id;
        this.name=subName;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
