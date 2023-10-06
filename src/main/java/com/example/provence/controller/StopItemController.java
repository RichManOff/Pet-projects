package com.example.provence.controller;

import com.example.provence.model.Item;
import com.example.provence.model.Review;
import com.example.provence.model.StopItem;
import com.example.provence.repository.ReviewRepository;
import com.example.provence.repository.StopItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/provence/stop_menu")
public class StopItemController {
    @Autowired
    private StopItemRepository stopItemRepository;

    @PostMapping("/bool")
    public ResponseEntity<Boolean> checkItem(@RequestBody StopItem stopItem) {
        Long itemCheck = stopItem.getItemId();
        if(stopItemRepository.findById(itemCheck).isPresent()){
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<StopItem> addReviews(@RequestBody StopItem stopItem) {
        stopItemRepository.save(stopItem);
        return ResponseEntity.ok(stopItem);
    }

    @GetMapping("/all")
    public ResponseEntity<List<StopItem>> getAllMenuItems() {
        List<StopItem> items = stopItemRepository.findAll();
        return ResponseEntity.ok(items);
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteItem(@RequestParam("id") Long id) {
        stopItemRepository.deleteById(id);
        return ResponseEntity.ok("Item deleted successfully.");
    }
}
