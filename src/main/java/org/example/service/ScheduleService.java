package org.example.service;

import org.example.DAO.ScheduleDAO;
import org.example.model.Schedule;

import java.util.List;

public class ScheduleService {
    private ScheduleDAO scheduleDAO;
    public ScheduleService() {
        this.scheduleDAO = new ScheduleDAO();
    }

    //lay danh sacsh lich trong
    public List<Schedule> getEmptySchedules() {
        return scheduleDAO.getEmptySchedule();
    }

    //lay danh sach lich da dat cua gia su
    public List<Schedule> getTutorSchedules(int tutorId) {
        return scheduleDAO.getScheduleByTutor(tutorId);
    }

    //gia su chon lich
    public boolean bookSchedule(int scheduleId, int tutorId) {
        return scheduleDAO.bookSchedule(scheduleId, tutorId);
    }

    //gia su huy lich
    public boolean cancelSchedule(int scheduleId, int tutorId) {
        return scheduleDAO.cancelSchedule(scheduleId, tutorId);
    }
}
