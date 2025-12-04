package org.delcom.app.services;

import org.delcom.app.entities.InventoryItem;
import org.delcom.app.repositories.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    // 1. Method yang dicari oleh HomeView (PENTING)
    public List<InventoryItem> getAllItems(String userId) {
        return inventoryRepository.findByUserId(userId);
    }

    // 2. Tambah Barang
    public InventoryItem addItem(InventoryItem item) {
        return inventoryRepository.save(item);
    }

    // 3. Ambil Detail Barang
    public Optional<InventoryItem> getItemById(String id) {
        return inventoryRepository.findById(id);
    }

    // 4. Update Barang
    public InventoryItem updateItem(InventoryItem item) {
        return inventoryRepository.save(item);
    }

    // 5. Hapus Barang
    public void deleteItem(String id) {
        inventoryRepository.deleteById(id);
    }
}