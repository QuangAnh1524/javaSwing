package org.example.swingUI.student;

import org.example.manager.SessionManager;
import org.example.model.Student;
import org.example.service.StudentService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfileStudent extends JPanel {
    private JButton btnSave;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JTextField txtName;
    private JTextField txtAge;
    private JTextField txtGrade;
    private JTextField txtEmail;
    private JTextField txtPhone;

    private StudentService studentService;


    public ProfileStudent( StudentService studentService) {

        this.studentService = studentService;
        setSize(600, 450);
        initComponents();
        getProfile();
    }




    private void initComponents() {

        jLabel1 = new JLabel();
        txtName = new JTextField();
        jLabel2 = new JLabel();
        txtAge = new JTextField();
        jLabel3 = new JLabel();
        txtGrade = new JTextField();
        jLabel4 = new JLabel();
        txtEmail = new JTextField();
        jLabel5 = new JLabel();
        txtPhone = new JTextField();
        btnSave = new JButton();

        jLabel1.setText("Họ và tên");
        jLabel2.setText("Tuổi ");
        jLabel3.setText("Học vấn ");
        jLabel4.setText("Email");
        jLabel5.setText("Số điện thoại ");
        btnSave.setText("LƯU");


        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveProfile();
            }
        });

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(59, 59, 59)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel4, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel3, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel5, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(txtEmail, GroupLayout.PREFERRED_SIZE, 211, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtGrade, GroupLayout.PREFERRED_SIZE, 211, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtAge, GroupLayout.PREFERRED_SIZE, 211, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtName, GroupLayout.PREFERRED_SIZE, 211, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtPhone, GroupLayout.PREFERRED_SIZE, 211, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(46, Short.MAX_VALUE))
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 100, Short.MAX_VALUE) // Đẩy nút vào giữa
                                .addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 100, Short.MAX_VALUE)) // Đẩy nút vào giữa
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel1))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtAge, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel2))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtGrade, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel3))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtEmail, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel4))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtPhone, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel5))
                                .addGap(30, 30, 30)
                                .addComponent(btnSave)
                                .addContainerGap(56, Short.MAX_VALUE))
        );
    }

    private void saveProfile(){
        int id = SessionManager.getInstance().getUserId();
        String name = txtName.getText().toString();
        int age = Integer.parseInt(txtAge.getText().toString());
        String grade = txtGrade.getText().toString();
        String email = txtEmail.getText().toString();
        String phone = txtPhone.getText().toString();
        boolean valid = studentService.updateProfileStudent(id, name, age, grade, phone, email);
        if(valid){
            JOptionPane.showMessageDialog(this, "Update profile successfully","Success" ,JOptionPane.INFORMATION_MESSAGE);
        }else {
            JOptionPane.showMessageDialog(this, "An error occurred, please try again!","Error" ,JOptionPane.ERROR_MESSAGE);
        }

    }

    private void getProfile(){
        Student student = new Student();
        student = studentService.getProfileStudent(SessionManager.getInstance().getUserId());
        txtName.setText(student.getName());
        txtAge.setText(String.valueOf(student.getAge()));
        txtGrade.setText(student.getGrade());
        txtEmail.setText(student.getEmail());
        txtPhone.setText(student.getPhoneNumber());
    }
}