package model;

import java.util.ArrayList;
import java.util.List;

public abstract class ListOfCards {

    protected List<Card> listOfCards;

    public ListOfCards() {
        listOfCards = new ArrayList<>();
    }

    public ListOfCards(List<Card> loc) {
        setListOfCards(loc);
    }

    //EFFECTS: returns true if list of cards contain a card with rank rank
    protected boolean contains(Card card) {
        int rank = card.getRank();
        String suit = card.getSuit();
        for (Card cd : listOfCards) {
            if (cd.getRank() == rank && cd.getSuit().equals(suit)) {
                return true;
            }
        }
        return false;
    }

    //EFFECTS: adds a Card into this
    protected void addCard(Card card) {
        listOfCards.add(card);
    }

    //EFFECTS: remove card at index i from this
    protected void removeCard(int i) {
        listOfCards.remove(i);
    }

    //setters
    protected void setListOfCards(List<Card> loc) {
        listOfCards = loc;
    }

    //getters
    protected List<Card> getListOfCards() {
        return listOfCards;
    }

    //EFFECTS: returns the number of cards in the deck
    protected int getSize() {
        return listOfCards.size();
    }

    //EFFECTS: returns string representation of the hand
    protected String toString(List<Card> loc) {
        String hand = "[";
        for (int i = 0; i < loc.size(); i++) {
            hand += loc.get(i).toString();
            if (i != loc.size() - 1) {
                hand += ", ";
            }
        }
        return hand + "]";
    }
}
