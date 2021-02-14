package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ListOfCards {
    protected static final List<String> SUITS = new ArrayList<>(Arrays.asList("diamond", "clubs", "heart", "spade"));

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

    //EFFECTS: adds a Card into this
    protected void addCard(Card card) {
        listOfCards.add(card);
    }

    //EFFECTS: remove card at index i from this
    protected Card removeCard(int i) {
        Card removed = listOfCards.remove(i);
        return removed;
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
    public String toString() {
        String hand = "[";
        for (int i = 0; i < listOfCards.size(); i++) {
            hand += listOfCards.get(i).toString();
            if (i != listOfCards.size() - 1) {
                hand += ", ";
            }
        }
        return hand + "]";
    }
}
