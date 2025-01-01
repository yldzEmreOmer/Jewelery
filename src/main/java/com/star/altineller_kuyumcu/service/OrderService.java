package com.star.altineller_kuyumcu.service;

import com.star.altineller_kuyumcu.dto.OrderItemRequest;
import com.star.altineller_kuyumcu.model.Order;
import com.star.altineller_kuyumcu.model.Status;

import java.util.Date;
import java.util.List;

public interface OrderService {
    Order createOrder(Long userId, List<OrderItemRequest> items);

    Order getOrderById(Long id);

    List<Order> getAllOrders();

    List<Order> getOrdersByUserId(Long userId);

    List<Order> getOrdersByDateRange(Date startDate, Date endDate);

    List<Order> getOrdersByStatus(Status status);

    Order updateOrderStatus(Long orderId, Status status);

    void deleteOrder(Long id);
}