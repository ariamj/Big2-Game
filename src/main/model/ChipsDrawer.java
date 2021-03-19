package model;

import org.json.JSONObject;
import persistence.Writable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the chips that player is in possession of
 */
public class ChipsDrawer implements Writable {
    private static final String WHITE = "white";
    private static final String BLUE = "blue";
    private static final String RED = "red";
    private static final String GOLD = "gold";

    private int numWhiteChips;
    private int numBlueChips;
    private int numRedChips;
    private int numGoldChips;
    private int balance;
    private List<Chips> drawer;

    public ChipsDrawer() {
        drawer = new ArrayList<>();
        this.numWhiteChips = 0;
        this.numBlueChips = 0;
        this.numRedChips = 0;
        this.numGoldChips = 0;
    }

    //EFFECTS: Makes a new drawer for chips for player with an initial state of:
    //          - numWhiteChips white chips, numBlueChips blue chips,
    //            numRedChips red chips, numGoldChips gold chips
    public ChipsDrawer(int numWhiteChips, int numBlueChips, int numRedChips, int numGoldChips) {
        this();
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
            Chips whiteChip = new Chips(WHITE);
            drawer.add(whiteChip);
            balance += whiteChip.getValue();
        }
        for (int i = 0; i < numBlueChips; i++) {
            Chips blueChip = new Chips(BLUE);
            drawer.add(blueChip);
            balance += blueChip.getValue();
        }
        for (int i = 0; i < numRedChips; i++) {
            Chips redChip = new Chips(RED);
            drawer.add(redChip);
            balance += redChip.getValue();
        }
        for (int i = 0; i < numGoldChips; i++) {
            Chips goldChip = new Chips(GOLD);
            drawer.add(goldChip);
            balance += goldChip.getValue();
        }
    }

    //REQUIRES: drawer is not empty and drawer contains chip
    //MODIFIES: this
    //EFFECTS: removes chip from drawer
    public void removeChipFromDrawer(Chips chip) {
        if (drawer.size() == 0) {
            return;
        }
        String colour = chip.getColour();
        for (int i = 0; i < drawer.size(); i++) {
            if (drawer.get(i).getColour().equals(colour)) {
                drawer.remove(i);
                if (colour.equals(WHITE)) {
                    numWhiteChips--;
                } else if (colour.equals(BLUE)) {
                    numBlueChips--;
                } else if (colour.equals(RED)) {
                    numRedChips--;
                } else {
                    numGoldChips--;
                }
                break;
            }
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

    //EFFECTS: returns the number of chips in the drawer
    public int getSize() {
        return drawer.size();
    }

    //getter
    public int getBalance() {
        return balance;
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

    //getters
    public List<Chips> getDrawer() {
        return this.drawer;
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

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("white chips", numWhiteChips);
        json.put("blue chips", numBlueChips);
        json.put("red chips", numRedChips);
        json.put("gold chips", numGoldChips);
        return json;
    }
}
