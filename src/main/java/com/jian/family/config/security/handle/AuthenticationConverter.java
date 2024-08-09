package com.jian.family.config.security.handle;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationConverter implements org.springframework.security.web.authentication.AuthenticationConverter {

    @Override
    public Authentication convert(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        return UsernamePasswordAuthenticationToken.unauthenticated(username, password);
    }
}
