package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Represents the cards that the player has currently
public class PlayerCards {

    private List<Card> playerCards;

    /**
     * Creates an empty set for a player hand
     */
    public PlayerCards(List<Card> cards) {
        playerCards = cards;
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
    public List<Card> playHand(List<Card> hand) {
        return null;
    }

    /**
     * EFFECTS: returns true if given hand is a valid hand
     *              ie. ranking of hand is higher or equal to most recent played hand
     *                  and is a valid type hand
     */
    private boolean isValidHand(List<Card> hand) {
        return false;
    }


    //EFFECTS: returns number of cards in playerCards
    public int getSize() {
        return playerCards.size();
    }
}
