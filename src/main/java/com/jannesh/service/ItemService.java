package com.jannesh.service;

import com.jannesh.dto.item.ItemDTO;
import com.jannesh.dto.item.SaveItemDTO;
import com.jannesh.entity.Item;
import com.jannesh.exception.ItemNotFoundException;
import com.jannesh.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepo;
    private final ModelMapper modelMapper;

    public ItemDTO fetchItemDetailsByItemId(UUID itemId) {
        Optional<Item> optionalItem = itemRepo.findById(itemId);
        if(optionalItem.isEmpty()) throw new ItemNotFoundException("Item Not Found");
        return modelMapper.map(optionalItem.get(), ItemDTO.class);
    }

    public ItemDTO createItem(SaveItemDTO requestDTO) {
        Item item = modelMapper.map(requestDTO, Item.class);
        item.setItemId(UUID.randomUUID());
        return modelMapper.map(itemRepo.save(item), ItemDTO.class);
    }
}
