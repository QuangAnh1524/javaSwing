package org.example.model;

import java.util.Date;

public class Schedule {
    private int scheduleId;
    private int tutorId;
    private Date lessonDate;
    private Date endDate;
    private String status; //"empty" hoac "booked"
    private int studentId;

    public Schedule() {
    }

    public Schedule(int scheduleId, int tutorId, Date lessonDate, Date endDate, String status, int studentId) {
        this.scheduleId = scheduleId;
        this.tutorId = tutorId;
        this.lessonDate = lessonDate;
        this.endDate = endDate;
        this.status = status;
        this.studentId = studentId;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public int getTutorId() {
        return tutorId;
    }

    public void setTutorId(int tutorId) {
        this.tutorId = tutorId;
    }

    public Date getLessonDate() {
        return lessonDate;
    }

    public void setLessonDate(Date lessonDate) {
        this.lessonDate = lessonDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
}