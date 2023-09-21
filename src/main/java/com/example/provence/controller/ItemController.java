package com.example.provence.controller;

import com.example.provence.model.Item;
import com.example.provence.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/provence/menu")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    // Endpoint to get all menu items
    @GetMapping
    public List<Item> getAllMenuItems() {
        return itemRepository.findAll();
    }

    // Endpoint to get a menu item by ID
    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Long id) {
        Optional<Item> item = itemRepository.findById(id);
        return item.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
