package com.leet5.ecommerce.service.implementation;

import com.leet5.ecommerce.exception.customer.CustomerNotFoundException;
import com.leet5.ecommerce.exception.order.OrderNotFoundException;
import com.leet5.ecommerce.exception.order.OrderUpdateException;
import com.leet5.ecommerce.exception.payment.PaymentNotFoundException;
import com.leet5.ecommerce.exception.product.ProductNotFoundException;
import com.leet5.ecommerce.exception.shipment.ShipmentNotFoundException;
import com.leet5.ecommerce.model.dto.OrderDTO;
import com.leet5.ecommerce.model.dto.OrderItemRequest;
import com.leet5.ecommerce.model.dto.OrderRequest;
import com.leet5.ecommerce.model.entity.Customer;
import com.leet5.ecommerce.model.entity.Order;
import com.leet5.ecommerce.model.entity.OrderItem;
import com.leet5.ecommerce.model.entity.Product;
import com.leet5.ecommerce.repository.*;
import com.leet5.ecommerce.service.OrderService;
import com.leet5.ecommerce.util.OrderMapper;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class OrderServiceV1Impl implements OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceV1Impl.class);
    private static final int VERSION = 1;

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final PaymentRepository paymentRepository;
    private final ShipmentRepository shipmentRepository;

    @Autowired
    public OrderServiceV1Impl(OrderRepository orderRepository, CustomerRepository customerRepository, ProductRepository productRepository, PaymentRepository paymentRepository, ShipmentRepository shipmentRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.paymentRepository = paymentRepository;
        this.shipmentRepository = shipmentRepository;
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
    public List<OrderDTO> getAllOrders(int page, int size) {
        logger.info("Fetching all orders");
        final PageRequest pageRequest = PageRequest.of(page, size);
        final List<Order> orders = orderRepository.findAll(pageRequest).getContent();
        return orders.stream().map(OrderMapper::toOrderDTO).toList();
    }

    @Override
    public void deleteOrderById(Long orderId) {
        logger.info("Deleting order with ID: {}", orderId);

        if (!orderRepository.existsById(orderId)) {
            throw new OrderNotFoundException("Order not found with ID: " + orderId);
        }

        orderRepository.deleteById(orderId);

        logger.info("Deleted customer with id {}", orderId);
    }

    @Override
    public OrderDTO updateOrder(Long id, OrderDTO newOrder) {
        logger.info("Updating order with ID: {}", id);

        final var order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + id));
        final var payment = paymentRepository.findById(newOrder.paymentId()).orElseThrow(() -> new PaymentNotFoundException("Payment not found with ID: " + id));
        final var shipment = shipmentRepository.findById(newOrder.shipmentId()).orElseThrow(() -> new ShipmentNotFoundException("Shipment not found with ID: " + id));

        order.setOrderDateTime(newOrder.orderDateTime());
        order.setPayment(payment);
        order.setShipment(shipment);

        try {
            final Order updatedOrder = orderRepository.save(order);
            logger.info("Updated customer with id {}", updatedOrder.getId());
            return OrderMapper.toOrderDTO(updatedOrder);
        } catch (Exception e) {
            throw new OrderUpdateException("Failed to update order due to data integrity violation");
        }
    }

    @Override
    public int getVersion() {
        return VERSION;
    }
}
