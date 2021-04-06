package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.*;

/**
 * Represents the chips that player is in possession of
 */
public class ChipsDrawer implements Writable {
    private static final String WHITE = Chips.WHITE;
    private static final String BLUE = Chips.BLUE;
    private static final String RED = Chips.RED;
    private static final String GOLD = Chips.GOLD;

    private Map<String, List<Chips>> drawer2;

    public ChipsDrawer() {
        this.drawer2 = new LinkedHashMap<>();
        this.drawer2.put(WHITE, new ArrayList<>());
        this.drawer2.put(BLUE, new ArrayList<>());
        this.drawer2.put(RED, new ArrayList<>());
        this.drawer2.put(GOLD, new ArrayList<>());
    }

    //EFFECTS: Makes a new drawer for chips for player with an initial state of:
    //          - numWhiteChips white chips, numBlueChips blue chips,
    //            numRedChips red chips, numGoldChips gold chips
    public ChipsDrawer(int numWhiteChips, int numBlueChips, int numRedChips, int numGoldChips) {
        this();
        insertInitialChipsInDrawer2(numWhiteChips, numBlueChips, numRedChips, numGoldChips);
    }

    //MODIFIES: this
    //EFFECTS: inserts multiple amounts of each chip colour to begin drawer
    private void insertInitialChipsInDrawer2(int numWhiteChips, int numBlueChips, int numRedChips, int numGoldChips) {
        for (int i = 0; i < numWhiteChips; i++) {
            List<Chips> prev = drawer2.get(WHITE);
            prev.add(new Chips(WHITE));
        }
        for (int i = 0; i < numBlueChips; i++) {
            List<Chips> prev = drawer2.get(BLUE);
            prev.add(new Chips(BLUE));
        }
        for (int i = 0; i < numRedChips; i++) {
            List<Chips> prev = drawer2.get(RED);
            prev.add(new Chips(RED));
        }
        for (int i = 0; i < numGoldChips; i++) {
            List<Chips> prev = drawer2.get(GOLD);
            prev.add(new Chips(GOLD));
        }
    }

    //MODIFIES: this
    //EFFECTS: removes chip from drawer if drawer contains chip; else do nothing
    public void removeChipFromDrawer(Chips chip) {
        String colour = chip.getColour();
        List<Chips> chips = drawer2.get(colour);
        if (chips != null && chips.size() != 0) {
            chips.remove(0);
        }
    }

    //MODIFIES: this
    //EFFECTS: adds chip to drawer
    public void addChipToDrawer(Chips chip) {
        List<Chips> chips = drawer2.get(chip.getColour());
        chips.add(chip);
    }

    //EFFECTS: returns the number of chips in the drawer
    public int getSize() {
        return drawer2.get(WHITE).size() + drawer2.get(BLUE).size() + drawer2.get(RED).size()
                + drawer2.get(GOLD).size();
    }

    //EFFECTS: returns total balance of this drawer
    public int getBalance() {
        int whiteBalance = drawer2.get(WHITE).size() * Chips.WHITE_CHIP_VALUE;
        int blueBalance = drawer2.get(BLUE).size() * Chips.BLUE_CHIP_VALUE;
        int redBalance = drawer2.get(RED).size() * Chips.RED_CHIP_VALUE;
        int goldBalance = drawer2.get(GOLD).size() * Chips.GOLD_CHIP_VALUE;

        return whiteBalance + blueBalance + redBalance + goldBalance;
    }

    //EFFECTS: returns number of white chips
    public int getNumWhiteChips() {
        return drawer2.get(WHITE).size();
    }

    //EFFECTS: returns number of blue chips
    public int getNumBlueChips() {
        return drawer2.get(BLUE).size();
    }

    //EFFECTS: returns number of red chips
    public int getNumRedChips() {
        return drawer2.get(RED).size();
    }

    //EFFECTS: returns number of gold chips
    public int getNumGoldChips() {
        return drawer2.get(GOLD).size();
    }

    //EFFECTS: returns a string representation of the chip drawer
    public String toString() {
        String drawerString = "[";
        for (String key : drawer2.keySet()) {
            List<Chips> chips = drawer2.get(key);
            for (int i = 0; i < chips.size(); i++) {
                drawerString += chips.get(i);
                if (key.equals(GOLD) && i == chips.size() - 1) {
                    break;
                }
                drawerString += ", ";
            }
        }
        return drawerString + "]";
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("white chips", getNumWhiteChips());
        json.put("blue chips", getNumBlueChips());
        json.put("red chips", getNumRedChips());
        json.put("gold chips", getNumGoldChips());
        return json;
    }
}
