package com.leet5.ecommerce.service.factory;

import com.leet5.ecommerce.service.ShipmentService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class ShipmentServiceFactory {
    private final ConcurrentMap<Integer, ShipmentService> cache = new ConcurrentHashMap<>();

    public ShipmentServiceFactory(List<ShipmentService> shipmentServices) {
        shipmentServices.forEach(service -> cache.put(service.getVersion(), service));
    }

    public ShipmentService getService(int apiVersion) {
        if (!cache.containsKey(apiVersion)) {
            throw new IllegalArgumentException("Unknown api version: " + apiVersion);
        }
        return cache.get(apiVersion);
    }
}
