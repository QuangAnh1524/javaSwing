package org.example.service;

import org.example.DAO.ScheduleDAO;
import org.example.model.ScheduleRegister;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ScheduleService {
    private final ScheduleDAO scheduleDAO;

    public ScheduleService(ScheduleDAO scheduleDAO) {
        this.scheduleDAO = scheduleDAO;
    }

    /**
     * Tạo một lịch học mới cho gia sư
     */
    public boolean createSchedule(int tutorId, String subject, LocalTime timeStart,
                                  LocalTime timeEnd, LocalDate dayStart, LocalDate dayEnd) {
        // Kiểm tra tính hợp lệ của thời gian và ngày
        if (timeStart.isAfter(timeEnd) || dayStart.isAfter(dayEnd)) {
            return false; // Thời gian hoặc ngày không hợp lệ
        }

        // Kiểm tra xung đột lịch
        if (scheduleDAO.isScheduleConflict(tutorId, timeStart, timeEnd, dayStart, dayEnd)) {
            return false; // Có xung đột với lịch hiện tại
        }

        // Tạo lịch mới
        return scheduleDAO.createSchedule(tutorId, subject, timeStart, timeEnd, dayStart, dayEnd);
    }

    /**
     * Lấy danh sách các lịch học còn trống
     */
    public List<ScheduleRegister> getAvailableSchedules() {
        return scheduleDAO.getAvailableSchedules();
    }

    /**
     * Lấy danh sách lịch học của một gia sư
     */
    public List<ScheduleRegister> getTutorSchedules(int tutorId) {
        return scheduleDAO.getTutorSchedules(tutorId);
    }

    /**
     * Hủy một lịch học nếu nó chưa được đặt
     */
    public boolean cancelSchedule(int tutorId, int scheduleId) {
        return scheduleDAO.cancelSchedule(tutorId, scheduleId);
    }

    /**
     * Kiểm tra xem lịch mới có xung đột với lịch hiện tại của gia sư không
     */
    public boolean isScheduleConflict(int tutorId, LocalTime timeStart, LocalTime timeEnd,
                                      LocalDate dayStart, LocalDate dayEnd) {
        return scheduleDAO.isScheduleConflict(tutorId, timeStart, timeEnd, dayStart, dayEnd);
    }
}