package model;

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

    //REQUIRES: this has at least 26 cards (ie. size is >= 26)
    //MODIFIES: this
    //EFFECTS: deals out startAmount amount of cards
    public List<Card> dealCards(String startAmount) {
        List<Card> cardDealt = new ArrayList<>();
        int numCards = this.getSize();
        if (startAmount.equals("13 cards")) {
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
        return cardDealt;
    }
}
