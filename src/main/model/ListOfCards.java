package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a list of Card
 */
public abstract class ListOfCards {
    public static final List<String> SUITS = new ArrayList<>(Arrays.asList("diamond", "clubs", "heart", "spade"));

    protected List<Card> listOfCards;

    //EFFECTS: Makes a new empty list of cards
    public ListOfCards() {
        listOfCards = new ArrayList<>();
    }

    //EFFECTS: Makes a new list of cards with initial cards
    public ListOfCards(List<Card> loc) {
        setListOfCards(loc);
    }

    //EFFECTS: returns true if list of cards contain card
    public boolean contains(Card card) {
        int rank = card.getRank();
        String suit = card.getSuit();
        for (Card cd : listOfCards) {
            if (cd.getRank() == rank && cd.getSuit().equals(suit)) {
                return true;
            }
        }
        return false;
    }

    //EFFECTS: Returns the highest card in this list of cards
    protected Card highestCard() {
        int highestRank = 0;
        int indexOfHighest = 0;
        for (int i = 0; i < listOfCards.size(); i++) {
            int currCardRank = listOfCards.get(i).getRank();
            if (highestRank != 2 && ((currCardRank == 1 && highestRank == 13) || currCardRank > highestRank)) {
                highestRank = listOfCards.get(i).getRank();
                indexOfHighest = i;
            }
        }
        return listOfCards.get(indexOfHighest);
    }

    //MODIFIES: this
    //EFFECTS: adds a Card into this
    protected void addCard(Card card) {
        this.listOfCards.add(card);
    }

    //MODIFIES: this
    //EFFECTS: remove card at index i from this
    protected void removeCard(int i) {
        this.listOfCards.remove(i);
    }

    //setters
    protected void setListOfCards(List<Card> loc) {
        listOfCards = loc;
    }

    //getters
    protected List<Card> getListOfCards() {
        return listOfCards;
    }

    //EFFECTS: returns the card in listOfCards at index i
    protected Card getCard(int i) {
        return listOfCards.get(i);
    }

    //EFFECTS: returns the number of cards in this list of cards
    protected int getSize() {
        return listOfCards.size();
    }

    //EFFECTS: returns string representation of this list of cards
    public String toString() {
        StringBuilder hand = new StringBuilder("[");
        for (int i = 0; i < listOfCards.size(); i++) {
            hand.append(listOfCards.get(i).toString());
            if (i != listOfCards.size() - 1) {
                hand.append(", ");
            }
        }
        return hand + "]";
    }
}
