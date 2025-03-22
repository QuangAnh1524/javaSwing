package org.example.swingUI.student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterStudent extends JPanel {
    public RegisterStudent() {
        setSize(600, 450);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridLayout(0, 3, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (int i = 1; i <= 6; i++) {
            mainPanel.add(createChildPanel("Panel"));
        }
        add(mainPanel);
        setVisible(true);
    }

    private JPanel createChildPanel(String panelIndex) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));


        JButton btnRegister = new JButton("Đăng ký");
        btnRegister.setBackground(new Color(179, 218, 255));
        btnRegister.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Lắng nghe sự kiện khi bấm vào button
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(panel, "Đăng ký thành công!");
            }
        });

        JPanel labelPanel = new JPanel(new GridLayout(8, 1));
        String[] labels = {"Họ và tên: ", "Số điện thoại: ", "Chuyên môn: ", "Giờ bắt đầu: ", "Giờ kết thúc",
        "Ngày bắt đầu: ","Thời gian: ", "Giá/1buổi: "};
        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i], SwingConstants.LEFT);
            label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            label.setForeground(new Color(135, 135, 135));
            labelPanel.add(label);
        }

        panel.add(btnRegister, BorderLayout.NORTH);
        panel.add(labelPanel, BorderLayout.CENTER);

        return panel;
    }
}
