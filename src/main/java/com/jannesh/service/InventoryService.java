package com.jannesh.service;

import com.jannesh.dto.inventory.InventoryDTO;
import com.jannesh.dto.inventory.SaveInventoryDTO;
import com.jannesh.entity.Inventory;
import com.jannesh.entity.Item;
import com.jannesh.repository.InventoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepo;
    private final ItemService itemService;
    private final ModelMapper modelMapper;

    public InventoryDTO createInventoryDTO(SaveInventoryDTO requestDTO) {

        if(inventoryRepo.existsByItem_ItemId(requestDTO.getItemId()))
            throw new RuntimeException(("Item already exists in the Inventory"));

        Item item = itemService.fetchItemByItemId(requestDTO.getItemId());
        Inventory inventory = modelMapper.map(requestDTO, Inventory.class);

        inventory.setItem(item);
        inventory.setAvailableQty(requestDTO.getAvailableQty());

        return modelMapper.map(createInventory(inventory), InventoryDTO.class);
    }

    public Inventory createInventory(Inventory inventory) {
        return inventoryRepo.save(inventory);
    }

    public Inventory fetchInventoryByItemId(UUID itemId) {
        return inventoryRepo.findByItem_ItemId(itemId)
                .orElseThrow(() -> new EntityNotFoundException("Inventory Not Found"));
    }

}
