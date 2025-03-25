package org.example.model;

public class Tutor {
    private int tutorId;
    private int userId;
    private String name;
    private String phoneNumber;
    private int salary;

    public int getTutorId() { return tutorId; }
    public void setTutorId(int tutorId) { this.tutorId = tutorId; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public int getSalary() { return salary; }
    public void setSalary(int salary) { this.salary = salary; }
}