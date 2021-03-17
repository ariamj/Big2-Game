package model;

import javax.swing.*;
import java.awt.*;
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
        int highestRank = 3;
        int indexOfHighest = 0;
        for (int i = 0; i < listOfCards.size(); i++) {
            int currCardRank = listOfCards.get(i).getRank();
            if (highestRank == 2) {
                break;
            }
            if (thisIsHigherCard(currCardRank, highestRank)) {
                highestRank = listOfCards.get(i).getRank();
                indexOfHighest = i;
            }
        }
        return listOfCards.get(indexOfHighest);
    }

    //EFFECTS: returns true if r1 is a higher ranking card than r2
    //          - 1 is > 13
    protected boolean thisIsHigherCard(int r1, int r2) {
        if ((r1 <= 13 && r1 >= 3) && (r2 <= 13 && r2 >= 3)) {
            return r1 > r2;
        } else if ((r1 <= 13 && r1 >= 3) && (r2 == 1 || r2 == 2)) {
            return false;
        } else if ((r1 == 1) && (r2 <= 13 && r2 >= 3)) {
            return true;
        } else if (r1 == 1 && r2 == 2) {
            return false;
        } else {
            return (r1 == 2) && ((r2 <= 13 && r2 >= 3) || r2 == 1);
        }
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
    public List<Card> getListOfCards() {
        return listOfCards;
    }

    //EFFECTS: returns the card in listOfCards at index i
    public Card getCard(int i) {
        return listOfCards.get(i);
    }

    //EFFECTS: returns the number of cards in this list of cards
    public int getSize() {
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

    //TODO: GUI STUFF TO CLEAN UP
//    public abstract void draw(JPanel parent);

    public void draw(JPanel parent) {
        //TODO: IMPLEMENT DRAW
        for (Card card : listOfCards) {
            card.draw(parent);
//            card.draw();
        }
    }

}
