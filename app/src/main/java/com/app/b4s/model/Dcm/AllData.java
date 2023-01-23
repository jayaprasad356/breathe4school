package com.app.b4s.model.Dcm;


import java.util.ArrayList;

public class AllData {
    public Timetable getTimetable() {
        return timetable;
    }

    public void setTimetable(Timetable timetable) {
        this.timetable = timetable;
    }

    public StudyPlanner getStudyPlanner() {
        return studyPlanner;
    }

    public void setStudyPlanner(StudyPlanner studyPlanner) {
        this.studyPlanner = studyPlanner;
    }

    public Timetable timetable;
    public StudyPlanner studyPlanner;
}

class GeneralCategory {
    public String id;
    public String name;

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

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int start_time;
    public int end_time;
    public String day;
}

class Lecture {
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

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Object getSupplements() {
        return supplements;
    }

    public void setSupplements(Object supplements) {
        this.supplements = supplements;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String id;
    public String name;
    public int start_time;
    public int end_time;
    public Subject subject;
    public Teacher teacher;
    public Object supplements;
    public String day;
    public String description;
}

class Root {
    public boolean status;
    public String message;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<AllData> getData() {
        return data;
    }

    public void setData(ArrayList<AllData> data) {
        this.data = data;
    }

    public ArrayList<AllData> data;
}

class Schedule {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<SpecialLecture> getSpecial_lectures() {
        return special_lectures;
    }

    public void setSpecial_lectures(ArrayList<SpecialLecture> special_lectures) {
        this.special_lectures = special_lectures;
    }

    public ArrayList<Lecture> getLectures() {
        return lectures;
    }

    public void setLectures(ArrayList<Lecture> lectures) {
        this.lectures = lectures;
    }

    public ArrayList<Object> getProxy_lectures() {
        return proxy_lectures;
    }

    public void setProxy_lectures(ArrayList<Object> proxy_lectures) {
        this.proxy_lectures = proxy_lectures;
    }

    public ArrayList<GeneralCategory> getGeneral_categories() {
        return general_categories;
    }

    public void setGeneral_categories(ArrayList<GeneralCategory> general_categories) {
        this.general_categories = general_categories;
    }

    public String id;
    public ArrayList<SpecialLecture> special_lectures;
    public ArrayList<Lecture> lectures;
    public ArrayList<Object> proxy_lectures;
    public ArrayList<GeneralCategory> general_categories;
}

class School {
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

    public String name;
}

class Section {
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

    public String name;
}

class SpecialLecture {
    public String id;
    public String name;
    public int start_time;
    public int end_time;
    public Object subject;

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

    public Object getSubject() {
        return subject;
    }

    public void setSubject(Object subject) {
        this.subject = subject;
    }

    public Object getTeacher() {
        return teacher;
    }

    public void setTeacher(Object teacher) {
        this.teacher = teacher;
    }

    public boolean isSupplements() {
        return supplements;
    }

    public void setSupplements(boolean supplements) {
        this.supplements = supplements;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Object teacher;
    public boolean supplements;
    public String day;
}

class Standard {
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

    public String name;
}

class Student {
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

    public String id;
    public String name;
}

class StudyPlanner {
    public String id;
    public School school;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public Standard getStandard() {
        return standard;
    }

    public void setStandard(Standard standard) {
        this.standard = standard;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public ArrayList<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(ArrayList<Schedule> schedules) {
        this.schedules = schedules;
    }

    public Standard standard;
    public Section section;
    public Student student;
    public ArrayList<Schedule> schedules;
}

class Subject {
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

    public String name;
}

class Teacher {
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

    public String name;
}

class Timetable {
    public String id;
    public School school;
    public Standard standard;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public Standard getStandard() {
        return standard;
    }

    public void setStandard(Standard standard) {
        this.standard = standard;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public TimetableSession getTimetableSession() {
        return timetableSession;
    }

    public void setTimetableSession(TimetableSession timetableSession) {
        this.timetableSession = timetableSession;
    }

    public ArrayList<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(ArrayList<Schedule> schedules) {
        this.schedules = schedules;
    }

    public Section section;
    public TimetableSession timetableSession;
    public ArrayList<Schedule> schedules;
}

class TimetableSession {
    public String id;
    public String name;
    public int start_time;

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

    public int end_time;
}