package org.example.model;

public class Student {
    private int studentId;
    private int userId;
    private String name;
    private int age;
    private String grade;

    public Student() {
    }

    public Student(int studentId, int userId, String name, int age, String grade) {
        this.studentId = studentId;
        this.userId = userId;
        this.name = name;
        this.age = age;
        this.grade = grade;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}