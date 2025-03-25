package org.example;

import org.example.service.AuthService;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AuthService authService = new AuthService();
        System.out.print("Nhập tên đăng nhập: ");
        String username = scanner.nextLine();
        System.out.print("Nhập mật khẩu: ");
        String password = scanner.nextLine();
        boolean isValid = authService.login(username, password);
        if (isValid) {
            System.out.println("Đăng nhập thành công!");
        } else {
            System.out.println("Đăng nhập thất bại! Sai tên đăng nhập hoặc mật khẩu.");
        }
        scanner.close();
    }
}
