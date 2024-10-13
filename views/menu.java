/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package views;

/**
 *
 * @author Admin
 */
import models.Ram;

import java.util.Scanner;

import controllers.RamManagement;

public class menu {
    private RamManagement ramManagement; // Thêm biến này

    public menu() {
        ramManagement = new RamManagement(); // Khởi tạo trong constructor
    }

    public Ram addRam() {

        // RamManagement ramManagement = new RamManagement();
        System.out.println("Adding a new RAM:");
        Scanner sc = new Scanner(System.in);

        String type;
        while (true) {
            System.out.print("Enter RAM type (LPDDR5/DDR5/LPDDR4/DDR4): ");
            type = sc.nextLine();
            if (type.equals("LPDDR5") || type.equals("DDR5") || type.equals("LPDDR4") || type.equals("DDR4")) {
                break;
            } else {
                System.out.println("Invalid RAM type. Please enter LPDDR5, DDR5, LPDDR4, or DDR4.");
            }
        }

        String bus;
        while (true) {
            System.out.print("Enter RAM bus speed: ");
            bus = sc.nextLine();
            if (!bus.isEmpty()) {
                break;
            } else {
                System.out.println("Bus speed cannot be empty.");
            }
        }

        String brand;
        while (true) {
            System.out.print("Enter RAM brand: ");
            brand = sc.nextLine();
            if (!brand.isEmpty()) {
                break;
            } else {
                System.out.println("Brand cannot be empty.");
            }
        }

        int quantity;
        while (true) {
            try {
                System.out.print("Enter quantity: ");
                quantity = Integer.parseInt(sc.nextLine());
                if (quantity <= 0) {
                    System.out.println("Quantity must be a positive number.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid quantity. Please enter a number.");
            }
        }

        String productionMonthYear;
        while (true) {
            System.out.print("Enter production month and year (MM/YYYY): ");
            productionMonthYear = sc.nextLine();
            if (productionMonthYear.matches("\\d{2}/\\d{4}")) {
                break;
            } else {
                System.out.println("Invalid format. Please enter MM/YYYY.");
            }
        }

        // Bỏ phần hỏi người dùng về active vì khi người dùng add thì tự động hiểu là active true
        boolean active = true; // Đặt mặc định là true

        Ram newRam = new Ram(ramManagement.genCodeType(type), type, bus, brand, quantity, productionMonthYear, active);

        return newRam;

    }
}
