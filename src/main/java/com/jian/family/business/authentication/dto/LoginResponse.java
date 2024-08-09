package com.jian.family.business.authentication.dto;

public record LoginResponse(
        Boolean success,
        String message
) {
}
