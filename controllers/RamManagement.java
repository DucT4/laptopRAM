/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.util.List;
import models.Ram;
import java.util.ArrayList;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

/**
 *
 * @author Admin
 */
public class RamManagement implements IItem<Ram> {
    private static final String FILE_NAME = "RAMModules.dat";
    private List<Ram> ramList;

    public RamManagement() {
        this.ramList = new ArrayList<>();
    }

    @Override
    public boolean add(Ram Item) {
        if (Item == null) {
            return false;
        }
        return ramList.add(Item);
    }

    @Override
    public Ram getByCode(String bus) {
        for (Ram ram : ramList) {
            if (ram.getBus().equals(bus)) {
                return ram;
            }
        }
        return null;
    }

    @Override
    public List<Ram> getByBus(String bus) {
        List<Ram> rams = new ArrayList<>();
        for (Ram ram : ramList) {
            if (ram.getBus().equals(bus)) {
                rams.add(ram);
            }
        }
        return rams;
    }

    @Override
    public List<Ram> getByBrand(String brand) {
        List<Ram> rams = new ArrayList<>();
        for (Ram ram : ramList) {
            if (ram.getBrand().equals(brand)) {
                rams.add(ram);
            }
        }
        return rams;
    }

    @Override
    public List<Ram> getBytype(String type) {
        List<Ram> rams = new ArrayList<>();
        for (Ram ram : ramList) {
            if (ram.getType().equals(type)) {
                rams.add(ram);
            }
        }
        return rams;
    }

    public List<String> searchBus(String bus) {
        List<String> result = new ArrayList<>();
        for (Ram ram : ramList) {
            if (ram.getBus().toLowerCase().contains(bus.toLowerCase())) {
                result.add(String.format("  Code : %s ,Bus: %s,  Production_month_year: %s, Quantity: %d",ram.getCode(), ram.getBus(), ram.getProductionMonthYear(), ram.getQuantity()));
            }
        }
        return result;
    }

    public List<String> searchType(String type) {
        List<String> result = new ArrayList<>();
        for (Ram ram : ramList) {
            if (ram.getType().toLowerCase().contains(type.toLowerCase())) {
                result.add(String.format(" Code : %s ,Type: %s, Production_month_year: %s, Quantity: %d", ram.getCode(),ram.getBus(),ram.getProductionMonthYear(),ram.getQuantity()));
                      
            }
        }
        return result;
    }

    public List<String> searchBrand(String brand) {
        List<String> result = new ArrayList<>();
        for (Ram ram : ramList) {
            if (ram.getBrand().toLowerCase().contains(brand.toLowerCase())) {
                result.add(String.format(" Code : %s ,Brand: %s, Production_month_year: %s, Quantity: %d", ram.getCode(),ram.getBrand(),ram.getProductionMonthYear(),ram.getQuantity()));

            }
        }
        return result;
    }

    // hàm này dùng để lấy item thông qua id
    public Ram getById(String id) {
        for (Ram ram : ramList) {
            if (ram.getCode().equals(id)) {
                return ram;
            }
        }
        return null;
    }

    @Override
    public Ram update(String oldId, Ram newItem) {
        Ram oldRam = getById(oldId);
        if (oldRam == null) {
            return null;
        }

        Ram updatedRam = new Ram(oldRam.getCode(), oldRam.getType(), oldRam.getBus(), oldRam.getBrand(),
                oldRam.getQuantity(), oldRam.getProductionMonthYear(), oldRam.isActive());

        System.out.println("Enter new values (press Enter to keep current value):");

        System.out.print("Type (" + oldRam.getType() + "): ");
        String newType = System.console().readLine();
        if (!newType.isEmpty() && (newType.equals("LPDDR5") || newType.equals("DDR5") || newType.equals("LPDDR4") || newType.equals("DDR4"))) {
            updatedRam.setType(newType);
            updatedRam.setCode(genCodeType(newType));
        } else {
            updatedRam.setType(oldRam.getType());
        }

        System.out.print("Bus (" + oldRam.getBus() + "): ");
        String newBus = System.console().readLine();
        if (!newBus.isEmpty()) {
            updatedRam.setBus(newBus);
        } else {
            updatedRam.setBus(oldRam.getBus());
        }

        System.out.print("Brand (" + oldRam.getBrand() + "): ");
        String newBrand = System.console().readLine();
        if (!newBrand.isEmpty()) {
            updatedRam.setBrand(newBrand);
        } else {
            updatedRam.setBrand(oldRam.getBrand());
        }

        System.out.print("Quantity (" + oldRam.getQuantity() + "): ");
        String newQuantityStr = System.console().readLine();
        if (!newQuantityStr.isEmpty()) {
            try {
                int newQuantity = Integer.parseInt(newQuantityStr);
                if (newQuantity > 0) {
                    updatedRam.setQuantity(newQuantity);
                } else {
                    System.out.println("Quantity must be a positive number. Keeping old value.");
                    updatedRam.setQuantity(oldRam.getQuantity());
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid quantity. Keeping old value.");
                updatedRam.setQuantity(oldRam.getQuantity());
            }
        } else {
            updatedRam.setQuantity(oldRam.getQuantity());
        }

        System.out.print("Production Month/Year (" + oldRam.getProductionMonthYear() + "): ");
        String newProductionMonthYear = System.console().readLine();
        if (!newProductionMonthYear.isEmpty() && newProductionMonthYear.matches("\\d{2}/\\d{4}")) {
            updatedRam.setProductionMonthYear(newProductionMonthYear);
        } else {
            updatedRam.setProductionMonthYear(oldRam.getProductionMonthYear());
        }

        System.out.print("Active (" + oldRam.isActive() + "): ");
        String newActiveStr = System.console().readLine();
        if (!newActiveStr.isEmpty() && (newActiveStr.equals("true") || newActiveStr.equals("false"))) {
            boolean newActive = Boolean.parseBoolean(newActiveStr);
            updatedRam.setActive(newActive);
        } else {
            updatedRam.setActive(oldRam.isActive());
        }

        for (int i = 0; i < ramList.size(); i++) {
            if (ramList.get(i).getCode().equals(oldId)) {
                ramList.set(i, updatedRam);
                return oldRam;
            }
        }
        return null;
    }

    @Override
    public Ram delete(String id) {
        // for (int i = 0; i < ramList.size(); i++) {
        //     if (ramList.get(i).getCode().equals(id)) {
        //         return ramList.remove(i);
        for (Ram ram : ramList) {
            if (ram.getCode().equals(id)) {
                ram.setActive(false);
                return ram;
            }
        }
        return null;
    }

    @Override
    public void displayAll() {
        boolean foundActiveRam = this.ramList.isEmpty();

        if (foundActiveRam) {
            System.out.println("No active RAMs found.");
            return;
        }
        for (Ram ram : ramList) {
            if (ram.isActive()) {
                System.out.println(ram);
            }
        }
    }

    @Override
    public boolean save() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(ramList);
            objectOutputStream.close();
            fileOutputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean load() {
        try {
            FileInputStream fileInputStream = new FileInputStream(FILE_NAME);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            List<Ram> loadedRams = (List<Ram>) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();

            // Clear existing ramList and add loaded RAMs
            ramList.clear();
            // Giải thích cách lấy dữ liệu từ file binary:

            // 1. Đọc file binary:
            // FileInputStream fileInputStream = new FileInputStream(FILE_NAME);
            // Dòng này mở file binary để đọc.

            // 2. Chuyển đổi dữ liệu binary thành đối tượng Java:
            // ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            // ObjectInputStream giúp chuyển đổi dữ liệu binary thành đối tượng Java.

            // 3. Đọc và ép kiểu dữ liệu:
            // List<Ram> loadedRams = (List<Ram>) objectInputStream.readObject();
            // Dòng này đọc dữ liệu từ file, chuyển đổi thành List<Ram>.

            // 4. Thêm dữ liệu vào ramList:
            for (Ram ram : loadedRams) {
                // Tạo đối tượng Ram mới từ dữ liệu đã đọc
                Ram newRam = new Ram(
                        ram.getCode(),
                        ram.getType(),
                        ram.getBus(),
                        ram.getBrand(),
                        ram.getQuantity(),
                        ram.getProductionMonthYear(),
                        ram.isActive());
                // Thêm đối tượng Ram mới vào ramList
                ramList.add(newRam);

            }

            // Quá trình này cho phép đọc dữ liệu từ file binary
            // và khôi phục lại các đối tượng Ram trong chương trình.

            return true;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public String genCodeType(String type) {
        // Tìm số lượng RAM cùng loại hiện có
        int count = 0;
        for (Ram ram : ramList) {
            if (ram.getType().equalsIgnoreCase(type)) {
                count++;
            }
        }
        
        // Tạo mã code mới
        // Tạo tiền tố từ 3 ký tự đầu của loại RAM (hoặc ít hơn nếu loại RAM ngắn hơn 3 ký tự)
        String prefix = type.toUpperCase();
        
        // Tạo mã code bằng cách kết hợp tiền tố và số thứ tự (count + 1) được định dạng với 3 chữ số
        String code = String.format( prefix+"_%02d", count + 1);
        
        return code;
    }

}
