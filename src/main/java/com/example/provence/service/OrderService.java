package com.example.provence.service;

import com.example.provence.model.Item;
import com.example.provence.model.Order;
import com.example.provence.model.OrderStatus;
import com.example.provence.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class OrderService {

    public final OrderRepository orderRepository;

    public final ItemService itemService;

    public OrderService(OrderRepository orderRepository, ItemService itemService) {
        this.orderRepository = orderRepository;
        this.itemService = itemService;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            // Throw a custom exception when the order is not found.
            throw new IllegalStateException("Order not found with ID: " + orderId);
        }
        return orderOptional;
    }

    public Order createOrder(Order order) {
        List<Item> orderItems = new ArrayList<>();
        for(int i=0; i < order.getItems().size(); i++){
            Item item = itemService.getItemById(order.getItems().get(i).getId());
            orderItems.add(item);
        }
        order.setItems(orderItems);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.PENDING);
        log.info(order.getStatus().toString());
        return orderRepository.save(order);
    }

    public void deleteOrder(Long orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            orderRepository.delete(orderOptional.get());
        } else {
            throw new IllegalStateException("Order not found with ID: " + orderId);
        }
    }
}
