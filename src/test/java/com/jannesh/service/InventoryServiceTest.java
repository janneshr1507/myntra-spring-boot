package com.jannesh.service;

import com.jannesh.dto.inventory.InventoryDTO;
import com.jannesh.dto.inventory.SaveInventoryDTO;
import com.jannesh.entity.Inventory;
import com.jannesh.entity.Item;
import com.jannesh.repository.InventoryRepository;
import com.jannesh.repository.ItemRepository;
import com.jannesh.util.mapper.InventoryMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InventoryServiceTest {

    @Mock
    private ItemService itemService;

    @Mock
    private InventoryRepository inventoryRepo;

    @Mock
    private InventoryMapper mapper;

    @InjectMocks
    private InventoryService inventoryService;

    @Test
    public void shouldCreateInventorySuccessfully() {
        UUID itemId = UUID.randomUUID();

        Item item = new Item();
        item.setItemId(itemId);

        Inventory inventory = new Inventory();

        SaveInventoryDTO saveInventoryDTO = new SaveInventoryDTO();
        saveInventoryDTO.setItemId(itemId);
        saveInventoryDTO.setAvailableQty(1L);

        when(itemService.fetchItemByItemId(itemId)).thenReturn(item);
        when(inventoryRepo.findByItem_ItemId(itemId)).thenReturn(Optional.of(inventory));
        when(inventoryRepo.save(any(Inventory.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(mapper.toDTO(any(Inventory.class))).thenAnswer(invocation -> {
            Inventory savedInventory = invocation.getArgument(0);
            InventoryDTO savedInventoryDTO = new InventoryDTO();
            savedInventoryDTO.setAvailableQty(inventory.getAvailableQty());
            return savedInventoryDTO;
        });

        InventoryDTO savedInventoryDTO = inventoryService.createInventoryDTO(saveInventoryDTO);

        assertEquals(1L, savedInventoryDTO.getAvailableQty());
    }


}
