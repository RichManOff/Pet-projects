package com.example.provence.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "order_menu_items",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<Item> items = new ArrayList<>();

    // Customer name
    @Column(name = "customer_name")
    private String customerName;

    // Customer phone number
    @Column(name = "customer_phone")
    private String customerPhone;

    // Order status
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    // Order date
    @Column(name = "order_date")
    private LocalDateTime orderDate;

    // Constructors, getters, and setters

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    // Define a custom method to set the order status
    public void setOrderStatus(OrderStatus status) {
        this.status = status;
    }
}
