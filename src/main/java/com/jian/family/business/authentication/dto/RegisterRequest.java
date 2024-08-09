package com.jian.family.business.authentication.dto;

import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
        @NotBlank
        String username,
        @NotBlank
        String password,
        @NotBlank
        String name,
        @NotBlank
        String phoneNumber
) {
}
