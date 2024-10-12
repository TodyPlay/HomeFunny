package com.jian.family;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.nio.ByteBuffer;

public class FamilyCloudTests {
    @Test
    void byteBuffer() {
        System.out.println(new BCryptPasswordEncoder().encode("ctsi@123"));
    }
}
