package org.delcom.app.controllers;

import org.delcom.app.entities.InventoryItem;
import org.delcom.app.entities.User;
import org.delcom.app.services.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import java.util.List;

@Controller
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    // Menampilkan list barang user
    public List<InventoryItem> getItems(User user) {
        // ✅ PERBAIKAN: Menambahkan .toString() agar UUID diubah jadi String
        return inventoryService.getAllItems(user.getId().toString());
    }

    // Menambah barang
    public void addItem(String userId, String name, String category, int stock, double price, String imagePath) {
        InventoryItem newItem = new InventoryItem();
        newItem.setUserId(userId);
        newItem.setItemName(name);
        newItem.setCategory(category);
        newItem.setStock(stock);
        newItem.setPrice(price);
        newItem.setImagePath(imagePath);
        
        inventoryService.addItem(newItem);
    }

    // Menghapus barang
    public void deleteItem(String itemId) {
        inventoryService.deleteItem(itemId);
    }
}