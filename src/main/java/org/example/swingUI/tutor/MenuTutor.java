package org.example.swingUI.tutor;

import org.example.swingUI.listener.MenuListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
public class MenuTutor extends  JPanel{
    private  MenuListener listener;
    private JLabel selectedLabel = null;

    public MenuTutor(MenuListener listener){
        this.listener = listener;
        setLayout(new GridBagLayout());
        setBackground(new Color(102, 153, 255));

        GridBagConstraints grid = new GridBagConstraints();
        grid.gridx = 0;
        grid.fill = GridBagConstraints.HORIZONTAL;

        String[] menuItems = {"Đăng ký dạy", "Lịch dạy", "Thông tin cá nhân", "Thống kê lương"};

        int row = 0;
        for (String item : menuItems) {
            grid.gridy = row++;
            add(createMenuLabel(item), grid);
        }

        grid.gridy = row++;
        grid.weighty = 1;
        add(Box.createVerticalGlue(), grid);


        grid.gridy = row;
        grid.weighty = 0;
        add(createMenuLabel("Đăng xuất"), grid);
    }

    private JLabel createMenuLabel(String text){
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.PLAIN, 13));
        label.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 10));
        label.setOpaque(true);
        label.setPreferredSize(new Dimension(140, 50));
        label.setBackground(new Color(102, 153, 255));

        if (text.equals("Đăng ký dạy")) {
            selectedLabel = label;
            selectedLabel.setBackground(new Color(90, 120, 200));
            selectedLabel.setFont(new Font("Arial", Font.BOLD, 13));
        }

        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                label.setBackground(new Color(90, 120, 200));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (label != selectedLabel) {
                    label.setBackground(new Color(102, 153, 255));
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if(!label.getText().equals("Đăng xuất")){
                    if (selectedLabel != null) {
                        selectedLabel.setBackground(new Color(102, 153, 255));
                        selectedLabel.setFont(new Font("Arial", Font.PLAIN, 13));
                    }
                    selectedLabel = label;
                    selectedLabel.setBackground(new Color(90, 120, 200));
                    selectedLabel.setFont(new Font("Arial", Font.BOLD, 13));
                }
                if (listener != null) {
                    listener.onMenuSelected(label.getText());
                }
            }
        });
        return label;
    }
}