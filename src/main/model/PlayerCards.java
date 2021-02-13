package model;

import java.util.List;

// Represents the cards that the player has currently
public class PlayerCards extends ListOfCards {

//    private ListOfCards playerCards;

    /**
     * Creates an empty set for a player hand
     */
    public PlayerCards(ListOfCards cards) {
        super(cards.getListOfCards());
    }

    /**
     * REQUIRES: playerCards is not empty
     * MODIFIES: this
     * EFFECTS: player plays a hand
     * - remove cards from playerCards
     * - return hand
     */
    public Hand playHand(Hand hand) {
        for (int i = 0; i < this.getSize(); i++) {
            if (hand.contains(this.getListOfCards().get(i))) {
                this.getListOfCards().remove(i);
                i--;
            }
        }
        return hand;
    }

    /**
     * EFFECTS: returns true if given hand is a valid hand
     * ie. ranking of hand is higher or equal to most recent played hand
     * and is a valid type hand
     */
    private boolean canPlayHand(Hand hand) {
        if (hand.isValidTypeHand()) {
            return true;
        }
        return false;
    }
}
