package com.example.provence.repository;

import com.example.provence.model.StopItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StopItemRepository  extends JpaRepository<StopItem, Long> {
}
