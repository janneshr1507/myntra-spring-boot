package com.jannesh.service;

import com.jannesh.dto.item.ItemDTO;
import com.jannesh.dto.item.SaveItemDTO;
import com.jannesh.entity.Item;
import com.jannesh.entity.Vendor;
import com.jannesh.entity.Warehouse;
import com.jannesh.repository.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepo;
    private final VendorService vendorService;
    private final WarehouseService warehouseService;
    private final ModelMapper modelMapper;

    public ItemDTO fetchItemDTOByItemId(UUID itemId) {
        return modelMapper.map(fetchItemByItemId(itemId), ItemDTO.class);
    }

    public ItemDTO createItemDTO(SaveItemDTO requestDTO) {
        return modelMapper.map(createItem(requestDTO), ItemDTO.class);
    }

    public Item fetchItemByItemId(UUID itemId) {
        return itemRepo.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("Item Not Found"));
    }

    public Item createItem(SaveItemDTO requestDTO) {
        Item item = mapToItem(requestDTO);

        Vendor vendor = vendorService.fetchVendorByVendorId(requestDTO.getVendorId());
        Warehouse warehouse = warehouseService.fetchWarehouseByWarehouseIdAndVendorId(requestDTO.getWarehouseId(), requestDTO.getVendorId());

        item.setVendor(vendor);
        item.setWarehouse(warehouse);

        return itemRepo.save(item);
    }

    public Item mapToItem(SaveItemDTO itemDTO) {
        Item item = new Item();
        item.setBrand(itemDTO.getBrand());
        item.setModel(itemDTO.getModel());
        item.setColor(itemDTO.getColor());
        item.setSize(itemDTO.getSize());
        item.setFit(itemDTO.getFit());
        item.setMaterial(itemDTO.getMaterial());
        item.setActualPrice(itemDTO.getActualPrice());
        item.setDiscount(itemDTO.getDiscount());
        return item;
    }
}
