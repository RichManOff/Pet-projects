package com.example.provence.service;

import com.example.provence.model.Item;
import com.example.provence.model.Order;
import com.example.provence.model.OrderStatus;
import com.example.provence.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class OrderService {

    public final OrderRepository orderRepository;

    public final ItemService itemService;

    public OrderService(OrderRepository orderRepository, ItemService itemService, JavaMailSender javaMailSender) {
        this.orderRepository = orderRepository;
        this.itemService = itemService;
        this.javaMailSender = javaMailSender;
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

    public String beautifyMessage(Order order){
        int sum = 0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String content = "<h2>Номер заказа: " + order.getId() + "</h2>";
        content += "<table><thead><tr><th>  №  </th><th>|  Название продукта  </th><th>|  Цена  </th><th>|  Количество продукта  </th><th>|  Сумма  </th></tr></thead><tbody>";
        int num = 1;
        for (int i = 0; i < order.getItems().size(); i++) {
            int quantity = 0;
            String itemName = order.getItems().get(i).getName();

            // Count occurrences of this item in the remaining items
            for (int j = i; j < order.getItems().size(); j++) {
                if (itemName.equals(order.getItems().get(j).getName())) {
                    quantity++;
                }
            }
            double item_sum = order.getItems().get(i).getPrice() * quantity;

//            content += "<br/>" + num + ") " + itemName + " (" + order.getItems().get(i).getCategory().getName() + ") * " + quantity;
            content += """
                    <tr>
                      <td>|  """ + num + """
                      </td>
                      <td>|  """ + itemName + """
                      </td>
                      <td>|  """ + order.getItems().get(i).getPrice() + """
                      </td>
                      <td>|  """ + quantity + """
                      </td>
                      <td>|  """ + item_sum + """
                      </td>
                    </tr>
                    """;
            // Skip the next items with the same name
            for (int j = i + 1; j < order.getItems().size(); j++) {
                if (itemName.equals(order.getItems().get(j).getName())) {
                    i++;
                } else {
                    break;
                }
            }
            num++;
            sum += order.getItems().get(i).getPrice() * quantity;
        }
        String formattedOrderDate = order.getOrderDate().format(formatter);
        content += "</tbody></table><br/> Имя клиента: " + order.getCustomerName() +
                "<br/> Номер клиента: " + order.getCustomerPhone() +
                "<br/> Время заказа: " + formattedOrderDate +
                "<br/><br/><b> Общая сумма: " + sum + "тг</b>";
        log.info(content);
        return content;
    }
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmail ;
//= "ice_akpekova@mail.ru"
    public void sendEmail(String subject, String text) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(fromEmail);
        helper.setSubject(subject);
        helper.setText(text, true);

        javaMailSender.send(message);
    }
}
