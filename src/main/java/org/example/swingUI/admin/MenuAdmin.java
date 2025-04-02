package org.example.swingUI.admin;

//public class MenuAdmin {
//}
import org.example.swingUI.listener.MenuListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// Lớp MenuAdmin xử lý phần menu cho giao diện quản trị
public class MenuAdmin extends JPanel {
    // Listener để xử lý sự kiện khi người dùng chọn mục menu
    private MenuListener listener;
    // Label được chọn hiện tại
    private JLabel selectedLabel = null;

    // Constructor tạo menu quản trị
    public MenuAdmin(MenuListener listener) {
        this.listener = listener;
        // Thiết lập layout và màu nền
        setLayout(new GridBagLayout());
        setBackground(new Color(102, 153, 255));  // Màu nền xanh dương

        // Cấu hình GridBagConstraints để bố trí các thành phần
        GridBagConstraints grid = new GridBagConstraints();
        grid.gridx = 0;
        grid.fill = GridBagConstraints.HORIZONTAL;

        // Danh sách các mục menu
        String[] menuItems = {"Quản lý gia sư"};

        // Thêm các mục menu vào panel
        int row = 0;
        for (String item : menuItems) {
            grid.gridy = row++;
            add(createMenuLabel(item), grid);
        }

        // Thêm khoảng trống đẩy mục "Đăng xuất" xuống cuối
        grid.gridy = row++;
        grid.weighty = 1;
        add(Box.createVerticalGlue(), grid);

        // Thêm mục "Đăng xuất" ở cuối menu
        grid.gridy = row;
        grid.weighty = 0;
        add(createMenuLabel("Đăng xuất"), grid);
    }

    // Tạo một label menu với các thuộc tính và sự kiện tương ứng
    private JLabel createMenuLabel(String text) {
        // Khởi tạo và định dạng label
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);  // Màu chữ trắng
        label.setFont(new Font("Arial", Font.PLAIN, 13));  // Font chữ
        label.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 10));  // Padding cho label
        label.setOpaque(true);  // Cho phép hiển thị màu nền
        label.setPreferredSize(new Dimension(140, 50));  // Kích thước label
        label.setBackground(new Color(102, 153, 255));  // Màu nền mặc định

        // Nếu là mục "Quản lý gia sư" thì mặc định được chọn
        if (text.equals("Quản lý gia sư")) {
            selectedLabel = label;
            selectedLabel.setBackground(new Color(90, 120, 200));  // Màu nền khi được chọn
            selectedLabel.setFont(new Font("Arial", Font.BOLD, 13));  // Font in đậm khi được chọn
        }

        // Thêm các sự kiện chuột
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Đổi màu khi di chuột vào
                label.setBackground(new Color(90, 120, 200));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Trả về màu mặc định khi di chuột ra (nếu không phải label đang được chọn)
                if (label != selectedLabel) {
                    label.setBackground(new Color(102, 153, 255));
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // Xử lý khi người dùng click vào label
                if (!label.getText().equals("Đăng xuất")) {
                    // Nếu không phải là "Đăng xuất", đổi trạng thái của label hiện tại
                    if (selectedLabel != null) {
                        selectedLabel.setBackground(new Color(102, 153, 255));  // Trả về màu mặc định
                        selectedLabel.setFont(new Font("Arial", Font.PLAIN, 13));  // Trả về font mặc định
                    }
                    // Cập nhật label mới được chọn
                    selectedLabel = label;
                    selectedLabel.setBackground(new Color(90, 120, 200));  // Đổi màu nền
                    selectedLabel.setFont(new Font("Arial", Font.BOLD, 13));  // Đổi font chữ thành in đậm
                }
                // Gọi listener để xử lý sự kiện menu được chọn
                if (listener != null) {
                    listener.onMenuSelected(label.getText());
                }
            }
        });
        return label;
    }
}