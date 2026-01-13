package com.example.studentmanagementapptemplate;

public class Student {
    public int id;
    public String index;
    public String name;
    public String grade;
    public String contact;
    public String address;
    public String guardian;
    public String gender;

    public Student(int id, String index, String name, String grade, String contact, String guardian, String address, String gender) {
        this.id = id;
        this.index = index;
        this.name = name;
        this.grade = grade;
        this.contact = contact;
        this.address = address;
        this.guardian = guardian;
        this.gender = gender;
    }
}
