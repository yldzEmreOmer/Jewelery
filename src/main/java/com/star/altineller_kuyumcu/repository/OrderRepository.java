package com.star.altineller_kuyumcu.repository;

import com.star.altineller_kuyumcu.model.Order;
import com.star.altineller_kuyumcu.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser_Id(Long userId);

    List<Order> findByOrderDateBetween(Date startDate, Date endDate);

    List<Order> findByUser_IdAndOrderDateBetween(Long userId, Date startDate, Date endDate);

    List<Order> findByStatus(Status status);
}