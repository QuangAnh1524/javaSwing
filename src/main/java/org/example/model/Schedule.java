package org.example.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Schedule {
    private int scheduleId;
    private int tutorId;
    private LocalTime timeStart;
    private LocalTime timeEnd;
    private LocalDate dayStart;
    private LocalDate dayEnd;
    private String status;
    private int studentId;
    private String subject;

    public Schedule() {
        this.status = "empty";
    }

    // Getters và Setters
    public int getScheduleId() { return scheduleId; }
    public void setScheduleId(int scheduleId) { this.scheduleId = scheduleId; }
    public int getTutorId() { return tutorId; }
    public void setTutorId(int tutorId) { this.tutorId = tutorId; }
    public LocalTime getTimeStart() { return timeStart; }
    public void setTimeStart(LocalTime timeStart) { this.timeStart = timeStart; }
    public LocalTime getTimeEnd() { return timeEnd; }
    public void setTimeEnd(LocalTime timeEnd) { this.timeEnd = timeEnd; }
    public LocalDate getDayStart() { return dayStart; }
    public void setDayStart(LocalDate dayStart) { this.dayStart = dayStart; }
    public LocalDate getDayEnd() { return dayEnd; }
    public void setDayEnd(LocalDate dayEnd) { this.dayEnd = dayEnd; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
}