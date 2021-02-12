package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChipsDrawerTest {
    private ChipsDrawer drawer;
    private Chips chip;

    @BeforeEach
    public void runBefore() {
        drawer = new ChipsDrawer(20, 10, 5, 1);
        chip = new Chips("white");
    }

    @Test
    public void testAddChipToDrawerOnce() {
        drawer.addChipToDrawer(chip);
        assertEquals(1, drawer.getSize());
    }

    @Test
    public void testRemoveChipFromDrawerOnce() {
        drawer.addChipToDrawer(chip);
        drawer.removeChipFromDrawer(chip);
        assertEquals(0, drawer.getSize());
    }
}
