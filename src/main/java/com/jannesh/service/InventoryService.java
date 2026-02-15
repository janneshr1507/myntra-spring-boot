package com.jannesh.service;

import com.jannesh.dto.inventory.InventoryDTO;
import com.jannesh.dto.inventory.SaveInventoryDTO;
import com.jannesh.entity.Inventory;
import com.jannesh.entity.Item;
import com.jannesh.repository.InventoryRepository;
import com.jannesh.util.mapper.InventoryMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepo;
    private final ItemService itemService;
    private final InventoryMapper mapper;

    public InventoryDTO createInventoryDTO(SaveInventoryDTO requestDTO) {
        Item item = itemService.fetchItemByItemId(requestDTO.getItemId());
        Inventory inventory = inventoryRepo.findByItem_ItemId(requestDTO.getItemId())
                .orElse(new Inventory());

        inventory.setItem(item);
        inventory.setAvailableQty((inventory.getAvailableQty() == null ? 0L : inventory.getAvailableQty()) + requestDTO.getAvailableQty());

        return mapper.toDTO(createInventory(inventory));
    }

    public Inventory createInventory(Inventory inventory) {
        return inventoryRepo.save(inventory);
    }

    public Inventory fetchInventoryByItemId(UUID itemId) {
        return inventoryRepo.findByItem_ItemId(itemId)
                .orElseThrow(() -> new EntityNotFoundException("Inventory Not Found"));
    }

    public InventoryDTO fetchInventoryDTOByItemId(UUID itemId) {
        return mapper.toDTO(fetchInventoryByItemId(itemId));
    }
}
