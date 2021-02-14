package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the chips that player is in possession of
 */
public class ChipsDrawer {
    private static final String WHITE = "white";
    private static final String BLUE = "blue";
    private static final String RED = "red";
    private static final String GOLD = "gold";
    private static final Chips WHITE_CHIP = new Chips(WHITE);
    private static final Chips BLUE_CHIP = new Chips(BLUE);
    private static final Chips RED_CHIP = new Chips(RED);
    private static final Chips GOLD_CHIP = new Chips(GOLD);

    private int numWhiteChips;
    private int numBlueChips;
    private int numRedChips;
    private int numGoldChips;
    private List<Chips> drawer;

    //EFFECTS: Makes a new drawer for chips for player with an initial state of:
    //          - numWhiteChips white chips, numBlueChips blue chips,
    //            numRedChips red chips, numGoldChips gold chips
    public ChipsDrawer(int numWhiteChips, int numBlueChips, int numRedChips, int numGoldChips) {
        drawer = new ArrayList<>();
        this.numWhiteChips = numWhiteChips;
        this.numBlueChips = numBlueChips;
        this.numRedChips = numRedChips;
        this.numGoldChips = numGoldChips;
        insertInitialChipsInDrawer(numWhiteChips, numBlueChips, numRedChips, numGoldChips);
    }

    //MODIFIES: this
    //EFFECTS: inserts multiple amounts of each chip colour to begin drawer
    private void insertInitialChipsInDrawer(int numWhiteChips, int numBlueChips, int numRedChips, int numGoldChips) {
        for (int i = 0; i < numWhiteChips; i++) {
            drawer.add(WHITE_CHIP);
        }
        for (int i = 0; i < numBlueChips; i++) {
            drawer.add(BLUE_CHIP);
        }
        for (int i = 0; i < numRedChips; i++) {
            drawer.add(RED_CHIP);
        }
        for (int i = 0; i < numGoldChips; i++) {
            drawer.add(GOLD_CHIP);
        }
    }

    //REQUIRES: drawer is not empty and drawer contains chip
    //MODIFIES: this
    //EFFECTS: removes chip from drawer
    public void removeChipFromDrawer(Chips chip) {
        String colour = chip.getColour();
        for (int i = 0; i < drawer.size(); i++) {
            if (drawer.get(i).getColour().equals(colour)) {
                drawer.remove(i);
                break;
            }
        }
        if (colour.equals(WHITE)) {
            numWhiteChips--;
        } else if (colour.equals(BLUE)) {
            numBlueChips--;
        } else if (colour.equals(RED)) {
            numRedChips--;
        } else {
            numGoldChips--;
        }
    }

    //MODIFIES: this
    //EFFECTS: adds chip to drawer
    public void addChipToDrawer(Chips chip) {
        drawer.add(chip);
        String colour = chip.getColour();
        if (colour.equals(WHITE)) {
            numWhiteChips++;
        } else if (colour.equals(BLUE)) {
            numBlueChips++;
        } else if (colour.equals(RED)) {
            numRedChips++;
        } else {
            numGoldChips++;
        }
    }

    //EFFECTS: returns the total points in drawer
    public int getBalance() {
        return (numWhiteChips * WHITE_CHIP.getValue()) + (numBlueChips * BLUE_CHIP.getValue())
                + (numRedChips * RED_CHIP.getValue()) + (numGoldChips * GOLD_CHIP.getValue());
    }

    //EFFECTS: returns the number of chips in the drawer
    public int getSize() {
        return drawer.size();
    }

    //getters
    public int getNumWhiteChips() {
        return numWhiteChips;
    }

    //getters
    public int getNumBlueChips() {
        return numBlueChips;
    }

    //getters
    public int getNumRedChips() {
        return numRedChips;
    }

    //getters
    public int getNumGoldChips() {
        return numGoldChips;
    }

    //EFFECTS: returns a string representation of the chip drawer
    public String toString() {
        String drawerString = "[";
        for (int i = 0; i < drawer.size(); i++) {
            drawerString += drawer.get(i).toString();
            if (i != drawer.size() - 1) {
                drawerString += ", ";
            }
        }
        return drawerString + "]";
    }
}
