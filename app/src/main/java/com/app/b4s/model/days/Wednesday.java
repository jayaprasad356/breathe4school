package com.app.b4s.model.days;

import com.app.b4s.model.Teacher;

public class Wednesday {

    public String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStart_time() {
        return start_time;
    }

    public void setStart_time(int start_time) {
        this.start_time = start_time;
    }

    public int getEnd_time() {
        return end_time;
    }

    public void setEnd_time(int end_time) {
        this.end_time = end_time;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public String getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(String activity_id) {
        this.activity_id = activity_id;
    }

    public String getHomework_id() {
        return homework_id;
    }

    public void setHomework_id(String homework_id) {
        this.homework_id = homework_id;
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

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String name;
    public int start_time;
    public int end_time;
    public Teacher teacher;
    public String activity_id;
    public String homework_id;
    public String assessment_id;
    public String bbb_lecture_id;
    public String pre_read_id;
    public String day;
}

