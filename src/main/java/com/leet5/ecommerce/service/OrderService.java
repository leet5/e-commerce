package com.leet5.ecommerce.service;

import com.leet5.ecommerce.model.dto.OrderItemRequest;
import com.leet5.ecommerce.model.dto.OrderRequest;
import com.leet5.ecommerce.model.entity.Customer;
import com.leet5.ecommerce.model.entity.Order;
import com.leet5.ecommerce.model.entity.OrderItem;
import com.leet5.ecommerce.model.entity.Product;
import com.leet5.ecommerce.repository.CustomerRepository;
import com.leet5.ecommerce.repository.OrderRepository;
import com.leet5.ecommerce.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    public Order placeOrder(OrderRequest orderRequest) {
        logger.info("Placing order for customer with ID: {}", orderRequest.customerId());

        try {
            final Customer customer = customerRepository.findById(orderRequest.customerId())
                    .orElseThrow(() -> new IllegalArgumentException("Customer not found with ID: " + orderRequest.customerId()));

            final Order order = new Order();
            order.setCustomer(customer);
            order.setOrderDateTime(LocalDateTime.now());

            BigDecimal totalAmount = BigDecimal.ZERO;
            for (OrderItemRequest itemRequest : orderRequest.orderItems()) {
                final Product product = productRepository.findById(itemRequest.productId())
                        .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + itemRequest.productId()));

                final OrderItem orderItem = new OrderItem();
                orderItem.setOrder(order);
                orderItem.setProduct(product);
                orderItem.setQuantity(itemRequest.quantity());
                orderItem.setUnitPrice(product.getPrice());

                totalAmount = totalAmount.add(product.getPrice().multiply(BigDecimal.valueOf(itemRequest.quantity())));

                order.getOrderItems().add(orderItem);
            }

            order.setTotalAmount(totalAmount);

            logger.info("Saving order with total amount: {}", totalAmount);
            final Order savedOrder = orderRepository.save(order);
            logger.info("Order placed successfully with ID: {}", savedOrder.getId());

            return savedOrder;
        } catch (Exception e) {
            logger.error("Error placing order: {}", e.getMessage());
            throw e;
        }
    }

    public Optional<Order> getOrderById(Long orderId) {
        logger.info("Fetching order with ID: {}", orderId);
        try {
            return orderRepository.findById(orderId);
        } catch (Exception e) {
            logger.error("Error fetching order: {}", e.getMessage());
            throw e;
        }
    }

    public List<Order> getAllOrders() {
        logger.info("Fetching all orders");
        try {
            return orderRepository.findAll();
        } catch (Exception e) {
            logger.error("Error fetching all orders: {}", e.getMessage());
            throw e;
        }
    }
}
