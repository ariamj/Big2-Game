package model;

// Represents a chip with a value and colour
public class Chips {
    private static final int WHITE_CHIP_VALUE = 1;
    private static final int BLUE_CHIP_VALUE = 5;
    private static final int RED_CHIP_VALUE = 10;
    private static final int GOLD_CHIP_VALUE = 25;

    private String colour;

    /**
     * Creates a chip with the colour colour
     */
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

    //EFFECTS: returns the string representation of a chip
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