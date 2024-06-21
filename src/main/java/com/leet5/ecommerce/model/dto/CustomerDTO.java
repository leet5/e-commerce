package com.leet5.ecommerce.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.List;

public record CustomerDTO(
        Long id,
        String firstName,
        String lastName,
        String email,
        @JsonFormat(pattern = "dd.MM.yyyy") LocalDate birthdate,
        List<Long> orders) {
}
