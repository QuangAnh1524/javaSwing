package org.example.DAO;

import org.example.model.ScheduleTutorSalary;
import org.example.swingUI.tutor.ScheduleTutor;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class SalaryDAO {
    private Connection connection;
    public SalaryDAO(Connection connection) {
        this.connection = connection;
    }


//    public List<ScheduleTutorSalary> getSalaryTutor(int tutor_id){
//        List<ScheduleTutorSalary> table = new ArrayList<>();
//        String query = "SECLECT " +
//               "s.subject_name" +
//    }
}
