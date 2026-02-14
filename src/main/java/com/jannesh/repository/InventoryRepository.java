package com.jannesh.repository;

import com.jannesh.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, UUID> {
    boolean existsByItem_ItemId(UUID itemId);
    Optional<Inventory> findByItem_ItemId(UUID itemId);
}
