package model;

/**
 * Represents the pile of cards on the table
 */
public class TablePile extends Hand {

    //Creates a new empty table pile
    public TablePile() {
        super();
    }

    /**
     * MODIFIES: this
     * EFFECTS: updates the most recent played hand
     */
    public void playHandInPile(Hand hand) {
        super.setHand(hand);
    }
}
