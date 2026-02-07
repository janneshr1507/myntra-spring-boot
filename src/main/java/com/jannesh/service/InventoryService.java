package com.jannesh.service;

import com.jannesh.dto.inventory.SaveInventoryDTO;
import com.jannesh.entity.Inventory;
import com.jannesh.entity.Item;
import com.jannesh.entity.Vendor;
import com.jannesh.entity.Warehouse;
import com.jannesh.repository.InventoryRepository;
import com.jannesh.repository.ItemRepository;
import com.jannesh.repository.VendorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepo;
    private final VendorService vendorService;
    private final WarehouseService warehouseService;
    private final ItemService itemService;
    private final ModelMapper modelMapper;

    public Inventory createInventory(SaveInventoryDTO requestDTO) {
        Item item = itemService.createItem(requestDTO.getItem());

        Inventory inventory = new Inventory();
        inventory.setItem(item);

        //Adding Quantity in the Inventory
        inventory.setAvailableQty(requestDTO.getAvailableQty());
        return inventoryRepo.save(inventory);
    }

}
