package com.leet5.ecommerce.service.implementation;

import com.leet5.ecommerce.exception.product.ProductNotFoundException;
import com.leet5.ecommerce.model.entity.Product;
import com.leet5.ecommerce.repository.ProductRepository;
import com.leet5.ecommerce.service.ProductService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProductServiceV1Impl implements ProductService {
    private static final Logger log = LoggerFactory.getLogger(ProductServiceV1Impl.class);
    private static final int VERSION = 1;

    private final ProductRepository productRepository;

    public ProductServiceV1Impl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product addProduct(Product product) {
        log.info("Adding product {}", product);
        final Product savedProduct = productRepository.save(product);

        log.info("Saved product ID: {}", savedProduct.getId());
        return savedProduct;
    }

    @Override
    public Product getProductById(Long id) {
        log.info("Getting product by ID {}", id);

        final Product product = productRepository
                .findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));

        log.info("Found product: {}", product);
        return product;
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        log.info("Updating product with ID: {}", id);

        final Product productToUpdate = productRepository
                .findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));

        productToUpdate.setName(product.getName());
        productToUpdate.setDescription(product.getDescription());
        productToUpdate.setPrice(product.getPrice());
        productToUpdate.setStockQuantity(product.getStockQuantity());

        final Product updatedProduct = productRepository.save(productToUpdate);
        log.info("Updated product: {}", updatedProduct);

        return updatedProduct;
    }

    @Override
    public void deleteProduct(Long id) {
        log.info("Deleting product with ID: {}", id);

        final Product productToUpdate = productRepository
                .findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));

        productRepository.delete(productToUpdate);

        log.info("Deleted product: {}", productToUpdate);
    }

    @Override
    public int getVersion() {
        return VERSION;
    }
}
