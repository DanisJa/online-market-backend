package com.onlinemarket;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
class OnlineMarketApplicationTests {
    @Test
    void checkTests(){
        assertEquals("2 + 2 * 2 - 2 should return 4 (checking if tests work)", 4,2 + 2 * 2 - 2);
    }
}

