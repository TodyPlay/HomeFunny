package com.jian.family.business.user.ex;

public class UserExistException extends RuntimeException {
    public UserExistException(String message) {
        super(message);
    }

    public UserExistException() {
        this("用户已存在");
    }
}
