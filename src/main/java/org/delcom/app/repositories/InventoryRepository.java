package org.delcom.app.repositories;

import org.delcom.app.entities.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryItem, String> {
    // Mencari semua barang milik user tertentu (Poin Penilaian No. 6)
    List<InventoryItem> findByUserId(String userId);
}