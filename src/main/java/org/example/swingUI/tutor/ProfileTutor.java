package org.example.swingUI.tutor;

import javax.swing.*;

public class ProfileTutor extends JPanel {
    private JButton jButtonSave;
    private JLabel jLabelName;
    private JLabel jLabelPhone;
    private JLabel jLabelSalary;
    private JTextField jTextFieldName;
    private JTextField jTextFieldPhone;
    private JTextField jTextFieldSalary;

    public ProfileTutor() {
        setSize(600, 450);
        initComponents();
    }

    private void initComponents() {
        jLabelName = new JLabel("Họ và tên:");
        jTextFieldName = new JTextField();
        jLabelPhone = new JLabel("Số điện thoại:");
        jTextFieldPhone = new JTextField();
        jLabelSalary = new JLabel("Lương:");
        jTextFieldSalary = new JTextField();
        jButtonSave = new JButton("Lưu");

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(50)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabelName)
                                        .addComponent(jLabelPhone)
                                        .addComponent(jLabelSalary))
                                .addGap(20)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jTextFieldName, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextFieldPhone, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextFieldSalary, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(150)
                                .addComponent(jButtonSave, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGap(30)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabelName)
                                .addComponent(jTextFieldName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGap(15)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabelPhone)
                                .addComponent(jTextFieldPhone, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGap(15)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabelSalary)
                                .addComponent(jTextFieldSalary, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGap(30)
                        .addComponent(jButtonSave)
        );
    }
}
