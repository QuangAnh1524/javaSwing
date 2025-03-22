package org.example.model;

public class Tutor {
    private int tutorId;
    private int userId;
    private String name;
    private String phoneNumber;
    private String subject;

    public Tutor() {
    }

    public Tutor(int tutorId, int userId, String name, String phoneNumber, String subject) {
        this.tutorId = tutorId;
        this.userId = userId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.subject = subject;
    }

    public int getTutorId() {
        return tutorId;
    }

    public void setTutorId(int tutorId) {
        this.tutorId = tutorId;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
