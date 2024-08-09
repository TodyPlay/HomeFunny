package com.jian.family.business.authentication.api;

import com.jian.family.business.authentication.dto.*;
import com.jian.family.business.user.entity.UserEntity;
import com.jian.family.business.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/api/authentication")
@RestController
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @PostMapping("register")
    public RegisterResponse register(@RequestBody @Validated RegisterRequest request) {
        return userService.registerUser(request);
    }

    @RequestMapping("login-failure")
    public LoginResponse LoginFailure(@RequestAttribute Exception exception) {
        return new LoginResponse(false, exception.getMessage());
    }

    @RequestMapping("login-success")
    public LoginResponse LoginSuccess() {
        return new LoginResponse(true, "登录成功");
    }

    @RequestMapping("logout")
    public LogoutResponse logout() {
        return new LogoutResponse(true, "登录成功");
    }

    @RequestMapping("login-state")
    public LoginState state() {
        SecurityContext context = SecurityContextHolder.getContext();

        Authentication authentication = context.getAuthentication();

        if (authentication.getPrincipal() instanceof UserEntity user) {
            return new LoginState(true, user.getId(), user.getUsername(), user.getName());
        }

        return new LoginState(false, -1L, "anonymousUser", "anonymousUser");

    }

}

