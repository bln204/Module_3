package com.example.model;

public class Student {
    private int id;
    private String name;
    private String email;
    private int gender;
    private int point;
    private Clazz clazz;


    public Clazz getClazz() {
        return clazz;
    }

    public void setClazz(Clazz clazz) {
        this.clazz = clazz;
    }

    public Student(int id, String name, String email, int gender, Clazz clazz, int point) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.clazz = clazz;
        this.point = point;
    }

    public Student(String name, String email, int gender, Clazz clazz, int point) {
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.clazz = clazz;
        this.point = point;
    }

    public Student(String name, Clazz clazz, String email, int gender, int id, int point) {
        this.name = name;
        this.clazz = clazz;
        this.email = email;
        this.gender = gender;
        this.id = id;
        this.point = point;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
