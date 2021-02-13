package model;

import java.util.ArrayList;

// Represents a card with a rank number and suit
public class Card {

    private int rank;
    private String suit;

    /**
     * Creates a new card with a rank and suit.
     * rank must be within the range of [1,13]
     * suit must be one of either:
     * - diamond
     * - clubs
     * - heart
     * - spade
     */
    public Card(int rank, String suit) {
        this.rank = rank;
        this.suit = suit.toLowerCase();
    }

    //getters
    public int getRank() {
        return this.rank;
    }

    //getters
    public String getSuit() {
        return this.suit;
    }

    //EFFECTS: Returns a string representation of a card
    public String toString() {
        String cardString = "";
        if (rank >= 2 && rank <= 10) {
            cardString += String.valueOf(rank);
        } else if (rank == 11) {
            cardString += "J";
        } else if (rank == 12) {
            cardString += "Q";
        } else if (rank == 13) {
            cardString += "K";
        } else {
            cardString += "A";
        }
        if (suit.equalsIgnoreCase("diamond")) {
            cardString += "D";
        } else if (suit.equalsIgnoreCase("clubs")) {
            cardString += "C";
        } else if (suit.equalsIgnoreCase("heart")) {
            cardString += "H";
        } else {
            cardString += "S";
        }
        return cardString;
    }
}
