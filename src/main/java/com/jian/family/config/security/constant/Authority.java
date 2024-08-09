package com.jian.family.config.security.constant;

import org.springframework.security.core.GrantedAuthority;

public enum Authority implements GrantedAuthority {
    NORMAL, ADMIN, API;


    @Override
    public String getAuthority() {
        return name();
    }
}
