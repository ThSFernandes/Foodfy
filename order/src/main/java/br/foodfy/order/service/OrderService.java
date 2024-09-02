package br.foodfy.order.service;

import br.foodfy.order.model.OrderItem;
import br.foodfy.order.model.MenuItemResponse;
import br.foodfy.order.model.Order;
import br.foodfy.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public OrderService() {
    }

    public Order createOrder(Order order) {
        boolean isValid = validateOrderItems(order);

        if (!isValid) {
            throw new IllegalArgumentException("One or more items in the order are invalid.");
        }

        double total = calculateTotal(order);
        order.setTotal(total);

        return orderRepository.save(order);
    }

    public Order getOrder(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("Order with ID " + id + " not found."));
    }

    public List<Order> listOrders() {
        List<Order> orders = orderRepository.findAll();
        if (orders.isEmpty()) {
            throw new IllegalStateException("No orders found.");
        }
        return orders;
    }

    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new IllegalArgumentException("Order with ID " + id + " does not exist.");
        }
        orderRepository.deleteById(id);
    }

    private boolean validateOrderItems(Order order) {
        RestTemplate restTemplate = new RestTemplate();

        for (OrderItem item : order.getItems()) {
            String urlMenuItem = "http://menu-service/api/menu-items/" + item.getMenuItemId();
            MenuItemResponse menuItemResponse = restTemplate.getForObject(urlMenuItem, MenuItemResponse.class);

            if (menuItemResponse == null) {
                throw new NullPointerException("Menu item with ID " + item.getMenuItemId() + " not found.");
            }
        }

        return true;
    }

    private double calculateTotal(Order order) {
        double total = 0;
        for (OrderItem item : order.getItems()) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }

    public void updateStatus(Long id, String status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("Order with ID " + id + " not found."));
        order.setStatus(status);
        orderRepository.save(order);
    }
}