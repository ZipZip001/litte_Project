package com.example.smallP.service.Order;

import com.example.smallP.entity.Order;
import com.example.smallP.entity.User;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderRepository orderRepository; // OrderRepository là interface bạn đã định nghĩa trước đó
    private EntityManager entityManager;
    @Autowired
    public OrderServiceImpl(EntityManager theEntityManager){
        entityManager = theEntityManager;
    }

    @Override
    public List<Order> findAll() {
        return entityManager.createQuery("SELECT u FROM Order u", Order.class).getResultList();
    }
    @Override
    public List<Order> findByUserName(String userName) {
        // Sử dụng truy vấn tùy chỉnh để lấy các đơn hàng dựa trên tên người dùng
        return entityManager.createQuery("SELECT o FROM Order o WHERE o.name = :userName", Order.class)
                .setParameter("userName", userName)
                .getResultList();
    }
}
