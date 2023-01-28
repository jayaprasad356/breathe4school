package com.app.b4s.model;

public class Users {
    String id;
    String name;

    public Users(String id, String name, String mobile, String age) {
        this.id=id;
        this.name=name;
        this.mobile=mobile;
        this.age=age;
    }

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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    String mobile;
    String age;
}

