package com.leet5.ecommerce.service;

import com.leet5.ecommerce.model.entity.Product;

public interface ProductService {
    Product addProduct(Product product);

    Product getProductById(Long id);

    Product updateProduct(Long id, Product product);

    void deleteProduct(Long id);

    int getVersion();
}
