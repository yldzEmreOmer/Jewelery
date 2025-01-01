package com.star.altineller_kuyumcu.service.impl;

import com.star.altineller_kuyumcu.dto.OrderItemRequest;
import com.star.altineller_kuyumcu.model.Order;
import com.star.altineller_kuyumcu.model.Product;
import com.star.altineller_kuyumcu.model.Status;
import com.star.altineller_kuyumcu.model.User;
import com.star.altineller_kuyumcu.exception.BaseException;
import com.star.altineller_kuyumcu.exception.ResourceNotFoundException;
import com.star.altineller_kuyumcu.exception.MessageType;
import com.star.altineller_kuyumcu.repository.OrderRepository;
import com.star.altineller_kuyumcu.repository.ProductRepository;
import com.star.altineller_kuyumcu.repository.UserRepository;
import com.star.altineller_kuyumcu.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public Order createOrder(Long userId, List<OrderItemRequest> items) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageType.USER_NOT_FOUND));

        Order order = Order.builder()
                .user(user)
                .status(Status.PENDING)
                .orderDate(new Date())
                .build();

        Order savedOrder = orderRepository.save(order);

        for (OrderItemRequest item : items) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException(MessageType.PRODUCT_NOT_FOUND));

            if (product.getStockQuantity() < item.getQuantity()) {
                throw new BaseException(MessageType.INSUFFICIENT_STOCK);
            }

            product.setStockQuantity(product.getStockQuantity() - item.getQuantity());
            productRepository.save(product);
        }

        return savedOrder;
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MessageType.ORDER_NOT_FOUND));
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUser_Id(userId);
    }

    @Override
    public List<Order> getOrdersByDateRange(Date startDate, Date endDate) {
        return orderRepository.findByOrderDateBetween(startDate, endDate);
    }

    @Override
    public List<Order> getOrdersByStatus(Status status) {
        return orderRepository.findByStatus(status);
    }

    @Override
    @Transactional
    public Order updateOrderStatus(Long orderId, Status status) {
        Order order = getOrderById(orderId);
        order.setStatus(status);
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new RuntimeException("Order not found");
        }
        orderRepository.deleteById(id);
    }
}