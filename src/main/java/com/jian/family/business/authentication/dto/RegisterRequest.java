package com.jian.family.business.authentication.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record RegisterRequest(
        @NotBlank
        String username,
        @NotBlank
        String password,
        @NotBlank
        String name,
        @NotBlank
        @Pattern(regexp = "0?(13|14|15|17|18|19)[0-9]{9}", message = "手机号不正确")
        String phoneNumber
) {
}
