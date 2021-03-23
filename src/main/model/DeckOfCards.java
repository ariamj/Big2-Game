package model;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a deck of cards containing 52 different cards
 */
public class DeckOfCards extends ListOfCards {

    //EFFECTS: Makes a new deck of 52 cards; each rank has one of each suit and no cards are repeated
    public DeckOfCards() {
        super();
        for (int i = 1; i <= 13; i++) {
            for (String suit : SUITS) {
                this.addCard(new Card(i, suit));
            }
        }
    }

//    @Override
//    TODO: GUI CLEAN UP
//    public void draw(Graphics g) {
//    public void draw(JPanel parent) {
//        for (Card card : listOfCards) {
////            card.draw(g);
////            card.draw();
//        }
//    }

    //REQUIRES: this has at least 26 cards (ie. size is >= 26)
    //MODIFIES: this
    //EFFECTS: deals out startAmount amount of cards
    public List<Card> dealCards(String startAmount) {
        List<Card> cardDealt = new ArrayList<>();
        int numCards = 26;
//        int numCards = this.getSize();
        if (startAmount.equals("13 cards")) {
            numCards = 13;
//            numCards = 26;
        }
        for (int i = 0; i < numCards; i++) {
            Card cardToBeRemoved = this.getCard(i);
            cardDealt.add(cardToBeRemoved);
        }
        for (int i = 0; i < this.getSize(); i++) {
            if (cardDealt.contains(this.getCard(i))) {
                this.removeCard(i);
                i--;
            }
        }
        return cardDealt;
    }

    //MODIFIES: this
    //EFFECTS: shuffles DeckOfCards (ie. place cards in random orders)
    public void shuffleDeck() {
        int newIndex;
        Card shuffleCard;
        for (int i = 0; i < 52; i++) {
            newIndex = (int) (Math.random() * (51 + 1));
            shuffleCard = this.getCard(0);
            this.removeCard(0);
            this.getListOfCards().add(newIndex, shuffleCard);
        }
    }

}
