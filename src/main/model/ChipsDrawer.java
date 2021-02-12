package model;

import java.util.ArrayList;
import java.util.List;

// Represents the chips that player is in possession of
public class ChipsDrawer {

    private int numWhiteChips;
    private int numBlueChips;
    private int numRedChips;
    private int numGoldChips;
    private List<Chips> drawer;

    /**
     * Creates a new drawer for chips for player with an initial state of:
     * - numWhiteChips white chips
     * - numBlueChips blue chips
     * - numRedChips red chips
     * - numGoldChips gold chips
     */
    public ChipsDrawer(int numWhiteChips, int numBlueChips, int numRedChips, int numGoldChips) {
        drawer = new ArrayList<>();
        this.numWhiteChips = numWhiteChips;
        this.numBlueChips = numBlueChips;
        this.numRedChips = numRedChips;
        this.numGoldChips = numGoldChips;
    }

    /**
     * REQUIRES: drawer is not empty and drawer contains chip
     * MODIFIES: this
     * EFFECTS: removes chip from drawer
     */
    public void removeChipFromDrawer(Chips chip) {
        drawer.remove(chip);
    }

    /**
     * MODIFIES: this
     * EFFECTS: adds chip to drawer
     */
    public void addChipToDrawer(Chips chip) {
        drawer.add(chip);
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
}
