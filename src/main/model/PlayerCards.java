package model;

import java.util.List;

// Represents the cards that the player has currently
public class PlayerCards {

    public PlayerCards() {
        //stub
    }

    /**
     * REQUIRES: playerCards is not empty
     * MODIFIES: this
     * EFFECTS: player plays a hand
     *          - if hand is valid (greater than the most recent hand played and is a valid poker hand):
     *              - remove cards from playerCards
     *              - return hand
     *          else: try again
     */
    public List<Card> playHand() {
        return null;
    }
}
