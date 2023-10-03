package com.example.smallP.service.Order;

import com.example.smallP.entity.Order;

import org.springframework.stereotype.Service;

import java.util.List;

public interface OrderService {

    List<Order> findAll();

    List<Order> findByUserName(String userName);

}
