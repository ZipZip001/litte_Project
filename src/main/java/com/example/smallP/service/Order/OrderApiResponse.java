package com.example.smallP.service.Order;

import com.example.smallP.entity.Order;

import java.util.List;

public class OrderApiResponse {
    private List<Order> orders;

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
