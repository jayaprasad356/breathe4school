package com.app.b4s.model;

public class DailyTimeTables {
    public String name,id,day,type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    Boolean joinNow,viewSummery,setReminder;
    public String description;
    public String  start_time;
    public  String end_time;
    public Subject subject;
    public String activity_id;
    public String assessment_id;
    public String bbb_lecture_id;
    public String pre_read_id;
    public Teacher teacher;

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

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Boolean getJoinNow() {
        return joinNow;
    }

    public void setJoinNow(Boolean joinNow) {
        this.joinNow = joinNow;
    }

    public Boolean getViewSummery() {
        return viewSummery;
    }

    public void setViewSummery(Boolean viewSummery) {
        this.viewSummery = viewSummery;
    }

    public Boolean getSetReminder() {
        return setReminder;
    }

    public void setSetReminder(Boolean setReminder) {
        this.setReminder = setReminder;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public String getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(String activity_id) {
        this.activity_id = activity_id;
    }

    public String getAssessment_id() {
        return assessment_id;
    }

    public void setAssessment_id(String assessment_id) {
        this.assessment_id = assessment_id;
    }

    public String getBbb_lecture_id() {
        return bbb_lecture_id;
    }

    public void setBbb_lecture_id(String bbb_lecture_id) {
        this.bbb_lecture_id = bbb_lecture_id;
    }

    public String getPre_read_id() {
        return pre_read_id;
    }

    public void setPre_read_id(String pre_read_id) {
        this.pre_read_id = pre_read_id;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}
