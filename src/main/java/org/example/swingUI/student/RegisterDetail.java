package org.example.swingUI.student;

import javax.swing.*;
import java.awt.*;

public class RegisterDetail extends JPanel{
    public RegisterDetail() {
        setSize(600, 450);
        setLayout(new BorderLayout());

        // Panel chính chứa 2 cột x 3 hàng
        JPanel mainPanel = new JPanel(new GridLayout(3, 2, 10, 10)); // 4 hàng, 3 cột, khoảng cách 10px
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Tạo 12 JPanel con, mỗi cái có 5 JLabel
        for (int i = 1; i <= 4; i++) {
            mainPanel.add(createChildPanel("Panel " + i));
        }
        add(mainPanel);
        setVisible(true);
    }

    private JPanel createChildPanel(String title) {
        JPanel panel = new JPanel(new GridLayout(5, 1)); // 1 cột, 5 hàng
        panel.setBorder(BorderFactory.createTitledBorder(title));

        // Thêm 5 label vào panel con
        for (int i = 1; i <= 5; i++) {
            JLabel label = new JLabel("Label " + i, SwingConstants.LEFT);
            label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            label.setForeground(new Color(135, 135, 135));
            panel.add(label);
        }
        return panel;
    }
}
