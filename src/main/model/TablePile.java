package model;

import java.util.ArrayList;
import java.util.List;

// Represents the pile of cards on the table
public class TablePile {

    private List<Card> topHand;

    /**
     * Creates a new empty table pile
     */
    public TablePile() {
        topHand = new ArrayList<>();
    }

    /**
     * MODIFIES: this
     * EFFECTS: updates the most recent played hand
     */
    public void playHandInPile(List<Card> hand) {
        topHand = hand;
    }

    //EFFECTS: returns the number of cards in the topHand
    public int getSize() {
        return topHand.size();
    }

    //EFFECTS: returns a string representation of the current table pile
    public String toString() {
        String hand = "[";
        for (int i = 0; i < topHand.size(); i++) {
            hand += topHand.get(i).toString();
            if (i != topHand.size() - 1) {
                hand += ", ";
            }
        }
        return hand + "]";
    }
}
