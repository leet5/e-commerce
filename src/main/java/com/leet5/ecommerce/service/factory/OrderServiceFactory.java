package com.leet5.ecommerce.service.factory;

import com.leet5.ecommerce.service.OrderService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class OrderServiceFactory {
    private final ConcurrentMap<Integer, OrderService> cache = new ConcurrentHashMap<>();

    public OrderServiceFactory(List<OrderService> orderServices) {
        orderServices.forEach(service -> cache.put(service.getVersion(), service));
    }

    public OrderService getService(int apiVersion) {
        if (!cache.containsKey(apiVersion)) {
            throw new IllegalArgumentException("Unknown api version: " + apiVersion);
        }
        return cache.get(apiVersion);
    }
}
