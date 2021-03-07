package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChipsDrawerTest {
    private static final int NUM_WHITE = 20;
    private static final int NUM_RED = 10;
    private static final int NUM_BLUE = 5;
    private static final int NUM_GOLD = 4;

    private ChipsDrawer drawer;
    private int initialSize = (NUM_WHITE + NUM_BLUE + NUM_RED + NUM_GOLD);
    private Chips whiteChip;
    private Chips blueChip;
    private Chips redChip;
    private Chips goldChip;

    @BeforeEach
    public void runBefore() {
        drawer = new ChipsDrawer(NUM_WHITE, NUM_BLUE, NUM_RED, NUM_GOLD);
        whiteChip = new Chips("white");
        blueChip = new Chips("blue");
        redChip = new Chips("red");
        goldChip = new Chips("gold");
    }

    @Test
    public void testConstructor() {
        assertEquals(NUM_WHITE, drawer.getNumWhiteChips());
        assertEquals(NUM_BLUE, drawer.getNumBlueChips());
        assertEquals(NUM_RED, drawer.getNumRedChips());
        assertEquals(NUM_GOLD, drawer.getNumGoldChips());
        assertEquals(initialSize, drawer.getSize());
    }

    @Test
    public void testAddChipToDrawerOnce() {
        drawer.addChipToDrawer(whiteChip);
        assertEquals(initialSize + 1, drawer.getSize());
        assertEquals(NUM_WHITE + 1, drawer.getNumWhiteChips());
    }

    @Test
    public void testAddChipToDrawerMultiple() {
        for (int i = 0; i < 10; i++) {
            drawer.addChipToDrawer(whiteChip);
            drawer.addChipToDrawer(blueChip);
            drawer.addChipToDrawer(redChip);
            drawer.addChipToDrawer(goldChip);
        }
        assertEquals(initialSize + 40, drawer.getSize());
        assertEquals(NUM_WHITE + 10, drawer.getNumWhiteChips());
        assertEquals(NUM_BLUE + 10, drawer.getNumBlueChips());
        assertEquals(NUM_RED + 10, drawer.getNumRedChips());
        assertEquals(NUM_GOLD + 10, drawer.getNumGoldChips());
    }

    @Test
    public void testRemoveChipFromDrawerEmpty() {
        ChipsDrawer emptyDrawer = new ChipsDrawer();
        emptyDrawer.removeChipFromDrawer(whiteChip);
        assertEquals(0, emptyDrawer.getSize());
    }

    @Test
    public void testRemoveChipFromDrawerWrongColour() {
        drawer.removeChipFromDrawer(new Chips("purple"));
        assertEquals(initialSize, drawer.getSize());
    }

    @Test
    public void testRemoveChipFromDrawerOnce() {
        drawer.removeChipFromDrawer(whiteChip);
        assertEquals(initialSize - 1, drawer.getSize());
        assertEquals(NUM_WHITE - 1, drawer.getNumWhiteChips());
    }

    @Test
    public void testRemoveChipFromDrawerMultiple() {
        for (int i = 0; i < 5; i++) {
            drawer.removeChipFromDrawer(whiteChip);
            drawer.removeChipFromDrawer(blueChip);
            drawer.removeChipFromDrawer(redChip);
            drawer.removeChipFromDrawer(goldChip);
        }
        assertEquals(initialSize - 19, drawer.getSize());
        assertEquals(NUM_WHITE - 5, drawer.getNumWhiteChips());
        assertEquals(NUM_BLUE - 5, drawer.getNumBlueChips());
        assertEquals(NUM_RED - 5, drawer.getNumRedChips());
        assertEquals(NUM_GOLD - 4, drawer.getNumGoldChips());
    }

    @Test
    public void testGetBalance() {
        int balance = (NUM_WHITE * Chips.WHITE_CHIP_VALUE) + (NUM_BLUE * Chips.BLUE_CHIP_VALUE)
                + (NUM_RED * Chips.RED_CHIP_VALUE) + (NUM_GOLD * Chips.GOLD_CHIP_VALUE);
        assertEquals(balance, drawer.getBalance());
    }

    @Test
    public void testToString() {
        String chipsString = "[";
        for (int i = 0; i < drawer.getNumWhiteChips(); i++) {
            chipsString += "W, ";
        }
        for (int i = 0; i < drawer.getNumBlueChips(); i++) {
            chipsString += "B, ";
        }
        for (int i = 0; i < drawer.getNumRedChips(); i++) {
            chipsString += "R, ";
        }
        for (int i = 0; i < drawer.getNumGoldChips() - 1; i++) {
            chipsString += "G, ";
        }
        chipsString += "G]";
        assertEquals(chipsString, drawer.toString());
    }
}
