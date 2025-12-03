package org.delcom.app.views;

import org.delcom.app.controllers.InventoryController;
import org.delcom.app.entities.InventoryItem;
import org.delcom.app.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Scanner; // Kuning hilang karena Scanner kita pakai di bawah

@Component
public class InventoryView {

    @Autowired
    private InventoryController inventoryController; // Kuning hilang karena kita pakai di case 1

    public void show(User user) {
        try (Scanner scanner = new Scanner(System.in)) { // Memakai Scanner

            System.out.println("--- MENU INVENTORY (GUDANG) ---");
            System.out.println("1. Lihat Daftar Barang");
            System.out.println("2. Tambah Barang");
            System.out.println("3. Hapus Barang");
            System.out.println("4. Kembali ke Menu Utama");
            System.out.print("Pilih menu (1-4): ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Bersihkan buffer

            switch (choice) {
                case 1:
                    // Menggunakan inventoryController untuk ambil data
                    List<InventoryItem> items = inventoryController.getItems(user);
                    System.out.println("\n=== DAFTAR BARANG ===");
                    for (InventoryItem item : items) {
                        System.out.println("- " + item.getItemName() + " (Stok: " + item.getStock() + ")");
                    }
                    break;
                case 2:
                    System.out.println("Fitur Tambah sedang disiapkan...");
                    break;
                case 3:
                    System.out.println("Fitur Hapus sedang disiapkan...");
                    break;
                case 4:
                    System.out.println("Kembali ke utama...");
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }
}