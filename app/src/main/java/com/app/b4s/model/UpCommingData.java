package com.app.b4s.model;

import android.location.Address;

import java.util.ArrayList;
import java.util.Date;

public class UpCommingData {
        public String id;
        public String name;
        public int start_time;
        public int end_time;
        public Object subject;
        public Teacher teacher;

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

        public Teacher getTeacher() {
                return teacher;
        }

        public void setTeacher(Teacher teacher) {
                this.teacher = teacher;
        }
}



