package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Represents a deck of cards containing 52 different cards
public class DeckOfCards extends ListOfCards {

    /**
     * Creates a new deck of 52 cards.
     * Each rank has one of each suit and no cards are repeated.
     */
    public DeckOfCards() {
        super();
        for (int i = 1; i <= 13; i++) {
            for (String suit : SUITS) {
                this.addCard(new Card(i, suit));
            }
        }
    }

    //EFFECTS: deals out numCards amount of cards
    public List<Card> dealCards(String amount) {
        List<Card> cardDealt = new ArrayList<>();
        int numCards = this.getSize();
        if (amount.equals("13 cards")) {
            numCards = 26;
        }
        for (int i = 0; i < numCards; i += 2) {
            Card cardToBeRemoved = this.getListOfCards().get(i);
            cardDealt.add(cardToBeRemoved);
        }
        for (int i = 0; i < this.getSize(); i++) {
            if (cardDealt.contains(this.getListOfCards().get(i))) {
                this.removeCard(i);
                i--;
            }
        }
//        return new Hand(cardDealt);
        return cardDealt;
    }

//    //MODIFIES: this
//    //EFFECTS: shuffles DeckOfCards (ie. place cards in random orders)
//    public DeckOfCards shuffleDeck() {
//        return null;
//    }

}
