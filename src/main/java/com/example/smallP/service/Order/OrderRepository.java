package com.example.smallP.service.Order;

import com.example.smallP.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(value = "SELECT * FROM ordertable LIMIT ?1 OFFSET ?2", nativeQuery = true)
    List<Order> findOrdersWithPagination(int limit, int offset);

    @Query(value = "SELECT * FROM ordertable ORDER BY created_at DESC", nativeQuery = true)
    List<Order> findAllByOrderByCreatedAtDesc();
}

