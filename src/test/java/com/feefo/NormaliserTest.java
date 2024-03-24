package com.feefo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NormaliserTest {

    private Normaliser normaliser;

    @BeforeEach
    void setUp() {
        normaliser = new Normaliser();
    }

    @Test
    void testNormaliseWithNullShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> normaliser.normalise(null));
    }

    @Test
    void testNormaliseWithEmptyStringShouldReturnUnknown() {
        assertEquals("Unknown", normaliser.normalise(""));
    }

    @Test
    void testNormaliseWithExactMatch() {
        assertEquals("Architect", normaliser.normalise("Architect"));
    }

    @Test
    void testNormaliseWithNoMatchShouldReturnUnknown() {
        assertEquals("Unknown", normaliser.normalise("Delivery Manager"));
    }

    @Test
    void testNormaliseWithPartialMatch() {
        assertEquals("Software engineer", normaliser.normalise("Software Developer"));
    }

    @Test
    void testNormaliseWithMisspellMatch() {
        assertEquals("Accountant", normaliser.normalise("Accountent"));
    }

    @Test
    void testNormaliseWithHyphenatedMatch() {
        assertEquals("Quantity surveyor", normaliser.normalise("Quant-surveyor"));
    }
}

