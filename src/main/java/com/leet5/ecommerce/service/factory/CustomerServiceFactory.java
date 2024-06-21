package com.leet5.ecommerce.service.factory;

import com.leet5.ecommerce.service.CustomerService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class CustomerServiceFactory {
    private final ConcurrentMap<Integer, CustomerService> cache = new ConcurrentHashMap<>();

    public CustomerServiceFactory(List<CustomerService> customerServices) {
        customerServices.forEach(service -> cache.put(service.getVersion(), service));
    }

    public CustomerService getService(Integer apiVersion) {
        if (!cache.containsKey(apiVersion)) {
            throw new IllegalArgumentException("Unknown api version: " + apiVersion);
        }
        return cache.get(apiVersion);
    }
}
