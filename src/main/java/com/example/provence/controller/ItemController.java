package com.example.provence.controller;

import com.example.provence.model.Item;
import com.example.provence.repository.ItemRepository;
import com.example.provence.service.CategoryService;
import com.example.provence.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/provence/items")
public class ItemController {

    @Autowired
    private ItemService itemService;
    @Autowired
    private CategoryService categoryService;

    // Endpoint to get all menu items
    @GetMapping
    public ResponseEntity<List<Item>> getAllMenuItems() {
        log.info("right");
        List<Item> items = itemService.getAllItems();
        return ResponseEntity.ok(items);
    }

    // Endpoint to get a menu item by ID
    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Long id) {
        Item item = itemService.getItemById(id);
        return ResponseEntity.ok(item);
    }

    @PostMapping("/add_to_category")
    public ResponseEntity<String> addToCategory(@RequestBody Item item) {
        if (categoryService.getCategoryById(item.getCategory().getId()) != null) {
            return ResponseEntity.ok("Item registered successfully: " + itemService.createMenuItem(item).toString());
        }

        return ResponseEntity.badRequest().body("Item registration failed. Category doesn't exists.");
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteItem(@RequestBody Long id) {
        itemService.deleteMenuItem(id);
        return ResponseEntity.badRequest().body("Item deleted successfully.");
    }

}
