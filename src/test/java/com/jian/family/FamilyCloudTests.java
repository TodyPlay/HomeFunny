package com.jian.family;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;

public class FamilyCloudTests {
    @Test
    void byteBuffer() {
        ByteBuffer buffer = ByteBuffer.wrap(new byte[]{1, 2, 3, 4, 5});

        byte i = buffer.get();
        Assertions.assertEquals(i, 1);
    }
}
