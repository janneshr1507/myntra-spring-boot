package com.jannesh.service;

import com.jannesh.dto.ItemDTO;
import com.jannesh.entity.Item;
import com.jannesh.exception.ItemNotFoundException;
import com.jannesh.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepo;
    private final ModelMapper modelMapper;

    public ItemDTO fetchItemDetailsByItemId(Long itemId) {
        Optional<Item> optionalItem = itemRepo.findById(itemId);
        if(optionalItem.isEmpty()) throw new ItemNotFoundException("Item Not Found");
        return modelMapper.map(optionalItem.get(), ItemDTO.class);
    }
}
