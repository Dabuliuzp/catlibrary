package com.example.virtual_campus.service;

import com.example.virtual_campus.model.Order;
import com.example.virtual_campus.model.OrderItem;
import com.example.virtual_campus.model.Product;
import com.example.virtual_campus.model.User;
import com.example.virtual_campus.exception.ResourceNotFoundException;
import com.example.virtual_campus.repository.OrderRepository;
import com.example.virtual_campus.repository.ProductRepository;
import com.example.virtual_campus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
    }

    @Transactional
    public Order createOrder(Long userId, Map<Long, Integer> productQuantities) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        Order order = new Order(user);

        for (Map.Entry<Long, Integer> entry : productQuantities.entrySet()) {
            Long productId = entry.getKey();
            int quantity = entry.getValue();

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));

            if (product.getQuantity() < quantity) {
                throw new IllegalArgumentException("Not enough stock for product: " + product.getName());
            }

            product.setQuantity(product.getQuantity() - quantity); // 减少库存

            OrderItem orderItem = new OrderItem(order, product, quantity);
            order.getOrderItems().add(orderItem);
        }

        // 计算订单中商品的总价，并根据用户的余额判断能否购买，如果不能购买则抛出异常，如果可以购买则扣除用户的余额
        double total = order.getOrderItems().stream()
                .mapToDouble(orderItem -> orderItem.getProduct().getPrice() * orderItem.getQuantity())
                .sum();

        if (user.getBalance() < total) {
            throw new IllegalArgumentException("Not enough balance to purchase");
        }

        user.setBalance(user.getBalance() - total); // 扣除余额

        // 将商品总价存入订单
        order.setTotalPrice(total);

        return orderRepository.save(order);
    }
}