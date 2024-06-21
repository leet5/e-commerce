package com.leet5.ecommerce.service.factory;

import com.leet5.ecommerce.service.ProductService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class ProductServiceFactory {
    private final ConcurrentMap<Integer, ProductService> cache = new ConcurrentHashMap<>();

    public ProductServiceFactory(List<ProductService> productServices) {
        productServices.forEach(service -> cache.put(service.getVersion(), service));
    }

    public ProductService getService(int apiVersion) {
        if (!cache.containsKey(apiVersion)) {
            throw new IllegalArgumentException("Unknown api version: " + apiVersion);
        }
        return cache.get(apiVersion);
    }
}
