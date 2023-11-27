package com.ptmsbackend;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SanityTest {
    @Test
    void sanity() {
        var string = "Hello";

        assertEquals("Hello", string);
    }
}
