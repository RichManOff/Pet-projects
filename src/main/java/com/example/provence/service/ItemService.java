package com.example.provence.service;

import com.example.provence.model.Item;
import com.example.provence.model.Order;
import com.example.provence.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ItemService {

    public final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Item getItemById(Long id) {
        Optional<Item> item = itemRepository.findById(id);
        if (item.isPresent()) {
            return item.get();
        } else {
            throw new IllegalStateException("Menu item with ID " + id + " not found");
        }
    }

    public Item createMenuItem(Item item) {
        return itemRepository.save(item);
    }

    public void deleteMenuItem(Long id) {
        Optional<Item> itemOptional = itemRepository.findById(id);
        if (itemOptional.isPresent()) {
            itemRepository.delete(itemOptional.get());
        } else {
            throw new IllegalStateException("Order not found with ID: " + id);
        }
    }
}
