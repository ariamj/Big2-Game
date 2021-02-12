package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Represents a deck of cards containing 52 different cards
public class DeckOfCards {
    private static final List<String> SUITS = new ArrayList<>(Arrays.asList("diamond", "clubs", "heart", "spade"));

    private List<Card> deckOfCards;

    /**
     * Creates a new deck of 52 cards.
     * Each rank has one of each suit and no cards are repeated.
     */
    public DeckOfCards() {
        deckOfCards = new ArrayList<>();
        for (int i = 1; i <= 13; i++) {
            for (String suit : SUITS) {
                deckOfCards.add(new Card(i, suit));
            }
        }
    }

    //REQUIRES: DeckOfCards is not empty
    //MODIFIES: this
    //EFFECTS: removes the first card in DeckOfCards and returns it
    public Card removeCard() {
        Card firstCard = deckOfCards.get(0);
        deckOfCards.remove(0);
        return firstCard;
    }

//    //MODIFIES: this
//    //EFFECTS: shuffles DeckOfCards (ie. place cards in random orders)
//    public DeckOfCards shuffleDeck() {
//        return null;
//    }

    //EFFECTS: returns the number of cards in the deck
    public int getSize() {
        return deckOfCards.size();
    }

//    //getters
//    public List<Card> getDeckOfCards() {
//        return null;
//    }

    //EFFECTS: Returns a string representation of DeckOfCards
    public String toString() {
        String cardDeck = "[";
        for (int i = 0; i < deckOfCards.size(); i++) {
            cardDeck += deckOfCards.get(i).toString();
            if (i != deckOfCards.size() - 1) {
                cardDeck += ", ";
            }
        }
        return cardDeck + "]";
    }
}
