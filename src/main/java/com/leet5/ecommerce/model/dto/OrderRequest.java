package com.leet5.ecommerce.model.dto;

import java.util.List;

public record OrderRequest(Long customerId, List<OrderItemRequest> orderItems) {}
