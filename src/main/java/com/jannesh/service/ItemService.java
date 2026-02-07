package com.jannesh.service;

import com.jannesh.dto.item.ItemDTO;
import com.jannesh.dto.item.SaveItemDTO;
import com.jannesh.entity.Item;
import com.jannesh.entity.Vendor;
import com.jannesh.entity.Warehouse;
import com.jannesh.exception.ItemNotFoundException;
import com.jannesh.repository.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepo;
    private final VendorService vendorService;
    private final WarehouseService warehouseService;
    private final ModelMapper modelMapper;

    public ItemDTO fetchItemDetailsByItemId(UUID itemId) {
        return modelMapper.map(fetchItemByItemId(itemId), ItemDTO.class);
    }

    public ItemDTO createItemDTO(SaveItemDTO requestDTO) {
        return modelMapper.map(createItem(requestDTO), ItemDTO.class);
    }

    public boolean existsByItemId(UUID itemId) {
        return itemRepo.existsById(itemId);
    }

    public Item fetchItemByItemId(UUID itemId) {
        return itemRepo.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("Item Not Found"));
    }

    public Item createItem(SaveItemDTO requestDTO) {
        Item item = new Item();
        item.setBrand(requestDTO.getBrand());
        item.setModel(requestDTO.getModel());
        item.setColor(requestDTO.getColor());
        item.setSize(requestDTO.getSize());
        item.setFit(requestDTO.getFit());
        item.setMaterial(requestDTO.getMaterial());
        item.setActualPrice(requestDTO.getActualPrice());
        item.setDiscount(requestDTO.getDiscount());

        Vendor vendor = vendorService.fetchVendorByVendorId(requestDTO.getVendorId());
        Warehouse warehouse = warehouseService.fetchWarehouseByWarehouseId(requestDTO.getWarehouseId(), requestDTO.getVendorId());

        item.setVendor(vendor);
        item.setWarehouse(warehouse);

        return itemRepo.save(item);
    }
}
