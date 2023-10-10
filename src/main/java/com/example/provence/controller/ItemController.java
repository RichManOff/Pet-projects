package com.example.provence.controller;

import com.example.provence.model.Item;
import com.example.provence.service.CategoryService;
import com.example.provence.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<String> deleteItem(@RequestParam("id") Long id) {
        log.info("controller");
        itemService.deleteMenuItem(id);
        return ResponseEntity.ok("Item deleted successfully.");
    }

    @PostMapping("/to_stop_menu")
    public ResponseEntity<String> toStopMenu(@RequestParam("id") Long id) {
        log.info("toStopMenu");
        Item item = itemService.getItemById(id);
        item.setStopMenu(true);
        itemService.createMenuItem(item);
        return ResponseEntity.ok("Item Stop successfully.");
    }

    @PostMapping("/delete_stop_menu")
    public ResponseEntity<String> deleteStopMenu(@RequestParam("id") Long id) {
        log.info("deleteStopMenu");
        Item item = itemService.getItemById(id);
        item.setStopMenu(false);
        itemService.createMenuItem(item);
        return ResponseEntity.ok("Item deleted successfully.");
    }
}
