package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyClassTest {

    @Test
    public void testAdd() {
        MyClass calculator = new MyClass();
        assertEquals(5, calculator.add(2, 3));
    }
}
