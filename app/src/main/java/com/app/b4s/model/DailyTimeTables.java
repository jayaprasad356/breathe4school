package com.app.b4s.model;

public class DailyTimeTables {
    public String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Object getSubject() {
        return subject;
    }

    public void setSubject(Object subject) {
        this.subject = subject;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public String  start_time;
    public  String end_time;
    public Object subject;
    public String activity_id;

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

    public String assessment_id;
    public String bbb_lecture_id;
    public String pre_read_id;
    public Teacher teacher;
}
