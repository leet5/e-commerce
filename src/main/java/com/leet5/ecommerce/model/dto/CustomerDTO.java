package com.leet5.ecommerce.model.dto;

import java.time.LocalDate;
import java.util.List;

public record CustomerDTO(Long id, String firstName, String lastName, String email, LocalDate birthdate,
                          List<Long> orders) {
}
