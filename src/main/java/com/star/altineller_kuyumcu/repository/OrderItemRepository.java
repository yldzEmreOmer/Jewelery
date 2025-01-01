package com.star.altineller_kuyumcu.repository;

import com.star.altineller_kuyumcu.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrder_Id(Long orderId);

    List<OrderItem> findByProduct_Id(Long productId);

    List<OrderItem> findByOrder_User_Id(Long userId);
}