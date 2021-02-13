package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChipsDrawerTest {
    private static final int WHITE_CHIP_VALUE = 1;
    private static final int BLUE_CHIP_VALUE = 5;
    private static final int RED_CHIP_VALUE = 10;
    private static final int GOLD_CHIP_VALUE = 25;

    private ChipsDrawer drawer;
    private Chips whiteChip;
    private Chips blueChip;
    private Chips redChip;
    private Chips goldChip;

    @BeforeEach
    public void runBefore() {
        drawer = new ChipsDrawer(20, 10, 5, 1);
        whiteChip = new Chips("white");
        blueChip = new Chips("blue");
        redChip = new Chips("red");
        goldChip = new Chips("gold");
    }

    @Test
    public void testConstructor() {
        assertEquals(20, drawer.getNumWhiteChips());
        assertEquals(10, drawer.getNumBlueChips());
        assertEquals(5, drawer.getNumRedChips());
        assertEquals(1, drawer.getNumGoldChips());
        assertEquals(36, drawer.getSize());
    }

    @Test
    public void testAddChipToDrawerOnce() {
        drawer.addChipToDrawer(whiteChip);
        assertEquals(37, drawer.getSize());
        assertEquals(21, drawer.getNumWhiteChips());
    }

    @Test
    public void testAddChipToDrawerMultiple() {
        for (int i = 0; i < 10; i++) {
            drawer.addChipToDrawer(whiteChip);
            drawer.addChipToDrawer(blueChip);
            drawer.addChipToDrawer(redChip);
            drawer.addChipToDrawer(goldChip);
        }
        assertEquals(76, drawer.getSize());
        assertEquals(30, drawer.getNumWhiteChips());
        assertEquals(20, drawer.getNumBlueChips());
        assertEquals(15, drawer.getNumRedChips());
        assertEquals(11, drawer.getNumGoldChips());
    }

    @Test
    public void testRemoveChipFromDrawerOnce() {
        drawer.removeChipFromDrawer(whiteChip);
        assertEquals(35, drawer.getSize());
        assertEquals(19, drawer.getNumWhiteChips());
    }

    @Test
    public void testRemoveChipFromDrawerMultiple() {
        for (int i = 0; i < 5; i++) {
            drawer.removeChipFromDrawer(whiteChip);
            drawer.removeChipFromDrawer(blueChip);
            drawer.removeChipFromDrawer(redChip);
        }
        drawer.removeChipFromDrawer(goldChip);
        assertEquals(20, drawer.getSize());
        assertEquals(15, drawer.getNumWhiteChips());
        assertEquals(5, drawer.getNumBlueChips());
        assertEquals(0, drawer.getNumRedChips());
        assertEquals(0, drawer.getNumGoldChips());
    }

    @Test
    public void testGetBalance() {
        int balance = (20*WHITE_CHIP_VALUE)+(10*BLUE_CHIP_VALUE)
                +(5*RED_CHIP_VALUE)+(1*GOLD_CHIP_VALUE);
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
        chipsString += "G]";
        assertEquals(chipsString, drawer.toString());
    }
}
