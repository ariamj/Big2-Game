package model;

import java.util.List;

// Represents a deck of cards containing 52 different cards
public class DeckOfCards {

    private List<Card> deckOfCards;

    public DeckOfCards() {
        //stub
    }

    //REQUIRES: DeckOfCards is not empty
    //MODIFIES: this
    //EFFECTS: removes the first card in DeckOfCards
    public Card removeCard() {
        return null;
    }

    //MODIFIES: this
    //EFFECTS: shuffles DeckOfCards (ie. place cards in random orders)
    public DeckOfCards shuffleDeck() {
        return null;
    }

    //EFFECTS: Returns a string representation of DeckOfCards
    public String toString() {
        return "";
    }
}
