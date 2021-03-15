package model;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Represents the cards that the player has currently
 */
public class PlayerCards extends ListOfCards {

    //Creates an initial set for a player hand
    public PlayerCards(List<Card> cards) {
        super(cards);
    }

//    @Override
//    public void draw(Graphics g) {
    public void draw(JPanel parent) {
        // stub
        //TODO: IMPLEMENT DRAW
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
            if (hand.contains(this.getCard(i))) {
                this.getListOfCards().remove(i);
                i--;
            }
        }
        return hand;
    }

    //getters
    public List<Card> getCardsList() {
        return listOfCards;
    }
}
