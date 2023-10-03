package com.example.smallP.service.Order.Interface;

import com.example.smallP.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    // Các phương thức tùy chỉnh khác (nếu cần)
}

