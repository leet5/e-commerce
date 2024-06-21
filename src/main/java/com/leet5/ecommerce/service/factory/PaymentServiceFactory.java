package com.leet5.ecommerce.service.factory;

import com.leet5.ecommerce.service.PaymentService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class PaymentServiceFactory {
    private final ConcurrentMap<Integer, PaymentService> cache = new ConcurrentHashMap<>();

    public PaymentServiceFactory(List<PaymentService> paymentServices) {
        paymentServices.forEach(service -> cache.put(service.getVersion(), service));
    }

    public PaymentService getService(int apiVersion) {
        if (!cache.containsKey(apiVersion)) {
            throw new IllegalArgumentException("Unknown api version: " + apiVersion);
        }
        return cache.get(apiVersion);
    }
}
