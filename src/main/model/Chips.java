package model;

import org.json.JSONObject;
import persistence.Writable;

/**
 * Represents a chip with a value and colour
 */
public class Chips {
    public static final int WHITE_CHIP_VALUE = 1;
    public static final int BLUE_CHIP_VALUE = 5;
    public static final int RED_CHIP_VALUE = 10;
    public static final int GOLD_CHIP_VALUE = 25;

    private String colour;

    //EFFECTS: Makes a chip with the colour colour
    public Chips(String colour) {
        this.colour = colour.toLowerCase();
    }

    //getters
    public String getColour() {
        return this.colour;
    }

    //getters
    public int getValue() {
        if (colour.equals("white")) {
            return WHITE_CHIP_VALUE;
        } else if (colour.equals("blue")) {
            return BLUE_CHIP_VALUE;
        } else if (colour.equals("red")) {
            return RED_CHIP_VALUE;
        } else {
            return GOLD_CHIP_VALUE;
        }
    }

    //EFFECTS: returns the string representation of a chip in form of first letter of colour
    public String toString() {
        if (colour.equals("white")) {
            return "W";
        } else if (colour.equals("blue")) {
            return "B";
        } else if (colour.equals("red")) {
            return "R";
        } else {
            return "G";
        }
    }
}
