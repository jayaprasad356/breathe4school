package com.app.b4s.model;

public class OnHomeWorkData {


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    String  name;
    String deadline;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    String description;

    public Object getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(Object subject_id) {
        this.subject_id = subject_id;
    }

    Object subject_id;


}
