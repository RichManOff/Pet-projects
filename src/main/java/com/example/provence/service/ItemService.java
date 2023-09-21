package com.example.provence.service;

import com.example.provence.model.Item;
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
            // Handle not found case (e.g., throw an exception or return null)
            throw new IllegalStateException("Menu item with ID " + id + " not found");
        }
    }

    public List<Item> searchItems(String keyword) {
        return itemRepository.findByNameContainingIgnoreCase(keyword);
    }

    public Item createMenuItem(Item item) {
        // Add validation logic here, e.g., check if the name is unique
        return itemRepository.save(item);
    }

    public Item updateMenuItem(Long id, Item updatedMenu) {
        // Check if the menu item with the given ID exists
        Item existingItem = getItemById(id);

        // Update the existing menu item with the new data
        existingItem.setName(updatedMenu.getName());
        existingItem.setDescription(updatedMenu.getDescription());
        existingItem.setPrice(updatedMenu.getPrice());

        // Save the updated menu item
        return itemRepository.save(existingItem);
    }

    public void deleteMenuItem(Long id) {
        // Check if the menu item with the given ID exists
        Item existingItem = getItemById(id);

        // Implement authorization logic here, e.g., check if the user is an admin
//        if (!userIsAdmin()) {
//            throw new AuthorizationException("You are not authorized to delete menu items.");
//        }

        // Delete the menu item
        itemRepository.delete(existingItem);
    }


}
