package com.jannesh.service;

import com.jannesh.dto.item.ItemDTO;
import com.jannesh.dto.item.SaveItemDTO;
import com.jannesh.dto.item.UpdateItemDTO;
import com.jannesh.entity.Item;
import com.jannesh.entity.Vendor;
import com.jannesh.entity.Warehouse;
import com.jannesh.repository.ItemRepository;
import com.jannesh.util.mapper.ItemMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepo;
    private final VendorService vendorService;
    private final WarehouseService warehouseService;
    private final ItemMapper mapper;

    public ItemDTO fetchItemDTOByItemId(UUID itemId) {
        return mapper.toDTO(fetchItemByItemId(itemId));
    }

    public ItemDTO createItemDTO(SaveItemDTO requestDTO) {
        Vendor vendor = vendorService.fetchVendorByVendorId(requestDTO.getVendorId());
        Warehouse warehouse = warehouseService.fetchWarehouseByWarehouseIdAndVendorId(requestDTO.getWarehouseId(), requestDTO.getVendorId());

        Item item = mapper.toEntity(requestDTO);
        item.setVendor(vendor);
        item.setWarehouse(warehouse);

        return mapper.toDTO(itemRepo.save(item));
    }

    public Item fetchItemByItemId(UUID itemId) {
        return itemRepo.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("Item Not Found"));
    }

    public boolean existsByItemId(UUID itemId) {
        return itemRepo.existsById(itemId);
    }

    public ItemDTO updateItemDetails(UpdateItemDTO requestDTO) {
        Item item = fetchItemByItemId(requestDTO.getItemId());
        mapper.toEntity(requestDTO, item);
        return mapper.toDTO(itemRepo.save(item));
    }
}
