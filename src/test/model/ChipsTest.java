package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChipsTest {
    private static final int WHITE_CHIP_VALUE = 1;
    private static final int BLUE_CHIP_VALUE = 5;
    private static final int RED_CHIP_VALUE = 10;
    private static final int GOLD_CHIP_VALUE = 25;

    private Chips chip;
    private Chips chip2;
    private Chips chip3;
    private Chips chip4;

    @BeforeEach
    public void runBefore() {
        chip = new Chips("white");
        chip2 = new Chips("blue");
        chip3 = new Chips("red");
        chip4 = new Chips("gold");
    }

    @Test
    public void testGetColour() {
        assertEquals("white", chip.getColour());
        assertEquals("blue", chip2.getColour());
        assertEquals("red", chip3.getColour());
        assertEquals("gold", chip4.getColour());
    }

    @Test
    public void testGetValue() {
        assertEquals(WHITE_CHIP_VALUE, chip.getValue());
        assertEquals(BLUE_CHIP_VALUE, chip2.getValue());
        assertEquals(RED_CHIP_VALUE, chip3.getValue());
        assertEquals(GOLD_CHIP_VALUE, chip4.getValue());
    }
}
