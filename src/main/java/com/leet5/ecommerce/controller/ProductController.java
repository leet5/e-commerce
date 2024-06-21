package com.leet5.ecommerce.controller;

import com.leet5.ecommerce.model.entity.Product;
import com.leet5.ecommerce.service.factory.ProductServiceFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductServiceFactory productServiceFactory;

    public ProductController(ProductServiceFactory productServiceFactory) {
        this.productServiceFactory = productServiceFactory;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product,
                                                 @RequestHeader("api-version") int apiVersion) {
        final var productService = productServiceFactory.getService(apiVersion);
        final var createdProduct = productService.addProduct(product);
        final var location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdProduct.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id,
                                                 @RequestBody Product product,
                                                 @RequestHeader("api-version") int apiVersion) {
        final var productService = productServiceFactory.getService(apiVersion);
        final var updatedProduct = productService.updateProduct(id, product);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id,
                                              @RequestHeader("api-version") int apiVersion) {
        final var productService = productServiceFactory.getService(apiVersion);
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id,
                                              @RequestHeader("api-version") int apiVersion) {
        final var productService = productServiceFactory.getService(apiVersion);
        final var product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }
}
