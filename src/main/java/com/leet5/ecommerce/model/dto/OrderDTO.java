package com.leet5.ecommerce.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderDTO(Long id,
                       Long customerId,
                       List<Long> items,
                       LocalDateTime orderDateTime,
                       BigDecimal totalAmount,
                       Long paymentId,
                       Long shipmentId) {
}
