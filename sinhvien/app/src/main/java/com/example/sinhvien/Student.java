package com.example.sinhvien;

public class Student {

    private int id;
    private String name;
    private String mssv;
    private String avatarPath;

    // Constructor
    public Student(int id, String name, String mssv, String avatarPath) {
        this.id = id;
        this.name = name;
        this.mssv = mssv;
        this.avatarPath = avatarPath;
    }

    public Student(String name, String mssv, String avatarPath) {
        this.name = name;
        this.mssv = mssv;
        this.avatarPath = avatarPath;
    }

    // Getters và Setters
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

    public String getMssv() {
        return mssv;
    }

    public void setMssv(String mssv) {
        this.mssv = mssv;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }
}
