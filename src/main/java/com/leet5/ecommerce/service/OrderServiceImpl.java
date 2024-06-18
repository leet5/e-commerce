package com.leet5.ecommerce.service;

import com.leet5.ecommerce.exception.customer.CustomerNotFoundException;
import com.leet5.ecommerce.exception.order.OrderNotFoundException;
import com.leet5.ecommerce.exception.product.ProductNotFoundException;
import com.leet5.ecommerce.model.dto.OrderDTO;
import com.leet5.ecommerce.model.dto.OrderItemRequest;
import com.leet5.ecommerce.model.dto.OrderRequest;
import com.leet5.ecommerce.model.entity.Customer;
import com.leet5.ecommerce.model.entity.Order;
import com.leet5.ecommerce.model.entity.OrderItem;
import com.leet5.ecommerce.model.entity.Product;
import com.leet5.ecommerce.repository.CustomerRepository;
import com.leet5.ecommerce.repository.OrderRepository;
import com.leet5.ecommerce.repository.ProductRepository;
import com.leet5.ecommerce.util.OrderMapper;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, CustomerRepository customerRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    @Override
    public OrderDTO placeOrder(OrderRequest orderRequest) {
        logger.info("Placing order for customer with ID: {}", orderRequest.customerId());

        final Customer customer = customerRepository
                .findById(orderRequest.customerId())
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with ID: " + orderRequest.customerId()));

        final Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDateTime(LocalDateTime.now());

        BigDecimal totalAmount = BigDecimal.ZERO;
        for (OrderItemRequest itemRequest : orderRequest.orderItems()) {
            final Product product = productRepository
                    .findById(itemRequest.productId())
                    .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + itemRequest.productId()));

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
        return OrderMapper.toOrderDTO(savedOrder);
    }

    @Override
    public OrderDTO getOrderById(Long orderId) {
        logger.info("Fetching order with ID: {}", orderId);

        final Order order = orderRepository
                .findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));

        return OrderMapper.toOrderDTO(order);
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        logger.info("Fetching all orders");
        final List<Order> orders = orderRepository.findAll();
        return orders.stream().map(OrderMapper::toOrderDTO).toList();
    }
}
