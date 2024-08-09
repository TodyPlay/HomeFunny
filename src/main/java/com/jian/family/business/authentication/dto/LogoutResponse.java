package com.jian.family.business.authentication.dto;

public record LogoutResponse(
        boolean success,
        String message
) {
}
