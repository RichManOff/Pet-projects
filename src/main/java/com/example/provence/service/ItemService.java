package com.example.provence.service;

import com.example.provence.dto.ImageDataDTO;
import com.example.provence.model.Item;
import com.example.provence.model.Order;
import com.example.provence.repository.ItemRepository;
import com.example.provence.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Slf4j
@Service
public class ItemService {

    public final ItemRepository itemRepository;
    public final OrderRepository orderRepository;

    private static final Map<String, MediaType> MEDIA_TYPE_MAP = new HashMap<>();

    static {
        MEDIA_TYPE_MAP.put("png", MediaType.IMAGE_PNG);
        MEDIA_TYPE_MAP.put("jpg", MediaType.IMAGE_JPEG);
        MEDIA_TYPE_MAP.put("jpeg", MediaType.IMAGE_JPEG);
        MEDIA_TYPE_MAP.put("webp", MediaType.parseMediaType("image/webp"));
    }

    public ItemService(ItemRepository itemRepository, OrderRepository orderRepository, ResourceLoader resourceLoader) {
        this.itemRepository = itemRepository;
        this.orderRepository = orderRepository;
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
        List<Order> orders = orderRepository.findByItemsId(id);
        log.info("service");
        if (itemOptional.isPresent()) {
            orderRepository.deleteAll(orders);
            itemRepository.deleteById(id);
        } else {
            throw new IllegalStateException("Order not found with ID: " + id);
        }
    }

    public void findAllByCategory_IdAndDelete(Long id){
        List<Item> items = itemRepository.findAllByCategory_Id(id);
        for (Item item : items) {
            deleteMenuItem(item.getId());
        }
    }

    public ImageDataDTO getImageData(String imageName) {
        if (imageName.startsWith(".")) {
            imageName = imageName.substring(1);
        }
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("static" + imageName);
        ClassPathResource resource = new ClassPathResource("static" + imageName);
        String filename = resource.getFilename();
        MediaType mediaType = null;
        byte[] imageBytes = new byte[0];

        if (Objects.nonNull(filename)) {
            mediaType = getMediaTypeFromFileName(filename);
        }

        try {
            if (resourceAsStream != null) {
                imageBytes = resourceAsStream.readAllBytes();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return ImageDataDTO.builder()
                .imageBytes(imageBytes)
                .mediaType(mediaType)
                .build();
    }

    public MediaType getMediaTypeFromFileName(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex != -1 && dotIndex < fileName.length() - 1) {
            String fileExtension = fileName.substring(dotIndex + 1);
            MediaType mediaType = MEDIA_TYPE_MAP.get(fileExtension.toLowerCase());
            return Objects.nonNull(mediaType) ? mediaType : MediaType.APPLICATION_OCTET_STREAM;
        }
        return MediaType.APPLICATION_OCTET_STREAM;
    }

}
