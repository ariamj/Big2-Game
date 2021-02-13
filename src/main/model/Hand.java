package model;

import java.util.Collections;
import java.util.List;

/**
 * Represents a hand of cards with 9 valid types (referred to as poker hands):
 * - Single
 * - Pair
 * - Three of a kind
 * - Straight
 * - Flush
 * - Full house
 * - Four of a kind
 * - Straight flush
 * - Royal straight flush (technically just the highest straight flush)
 */
public class Hand extends ListOfCards {

    public Hand() {
        super();
    }

    public Hand(List<Card> cards) {
        super(cards);
    }

    //setters
    public void setHand(ListOfCards cards) {
        super.setListOfCards(cards.getListOfCards());
    }

    //getters
    public List<Card> getHand() {
        return super.getListOfCards();
    }

    /**
     * EFFECTS: returns true is this is greater than other, false otherwise
     */
    public boolean isValidPlay(Hand other) {
        return false;
    }

    /**
     * EFFECTS: returns true if hand is a valid type hand, false otherwise
     */
    public boolean isValidTypeHand() {
        return (isValidSingleCardHand(this) || isValidPairHand(this)
                || isValidThreeOfAKindHand(this) || isValidFiveCardHand(this));
    }

    /**
     * EFFECTS: returns true if hand is a valid single type hand
     */
    private boolean isValidSingleCardHand(Hand hand) {
        return hand.getSize() == 1;
    }

    /**
     * REQUIRES: size of hand is >= 2
     * EFFECTS: returns true if hand is a valid pair type hand
     */
    private boolean isValidPairHand(Hand hand) {
        int firstCardRank = hand.getHand().get(0).getRank();
        int secondCardRank = hand.getHand().get(1).getRank();
        return (hand.getSize() == 2) && (firstCardRank == secondCardRank);
    }

    /**
     * EFFECTS: returns true if hand is a valid three of a kind type hand
     */
    private boolean isValidThreeOfAKindHand(Hand hand) {
        if (hand.getSize() != 3) {
            return false;
        }
        int firstCardRank = hand.getHand().get(0).getRank();
        int secondCardRank = hand.getHand().get(1).getRank();
        int thirdCardRank = hand.getHand().get(2).getRank();
        return firstCardRank == secondCardRank && secondCardRank == thirdCardRank;
    }

    /**
     * EFFECTS: returns true if hand is a valid 5 card hand type
     */
    private boolean isValidFiveCardHand(Hand hand) {
        return (hand.getSize() == 5) && (isValidStraightHand(hand) || isValidFlushHand(hand)
                || isValidFullHouseHand(hand) || isValidFourOfAKindHand(hand) || isValidStriaghtFlushHand(hand)
                || isValidRoyalStriahgtFlushHand(hand));
    }

    /**
     * EFFECTS: returns true if hand is a valid straight type hand
     */
    private boolean isValidStraightHand(Hand hand) {
        int rank = 0;
        for (Card card : hand.getHand()) {
            if (!(rank == 13 && card.getRank() == 1) && !(card.getRank() > rank)) {
                return false;
            }
            rank = card.getRank();
        }
        return true;
    }

    /**
     * EFFECTS: returns true if hand is a valid flush type hand
     */
    private boolean isValidFlushHand(Hand hand) {
        String suit = hand.getHand().get(0).getSuit();
        for (Card card : hand.getHand()) {
            if (!card.getSuit().equals(suit)) {
                return false;
            }
        }
        return true;
    }

    /**
     * EFFECTS: returns true if hand is a valid full house type hand
     */
    private boolean isValidFullHouseHand(Hand hand) {
        int numFirstRank = 0;
        int numSecondRank = 0;
        int firstRank = hand.getHand().get(0).getRank();
        int secondRank = hand.getHand().get(0).getRank();
        for (Card card : hand.getHand()) {
            if (card.getRank() == firstRank) {
                numFirstRank++;
            } else if (numSecondRank == 0) {
                secondRank = card.getRank();
                numSecondRank++;
            } else if (card.getRank() == secondRank) {
                numSecondRank++;
            } else {
                return false;
            }
        }
        return (numFirstRank == 3 && numSecondRank == 2) || (numFirstRank == 2 && numSecondRank == 3);
    }

    /**
     * EFFECTS: returns true if hand is a valid four of a kind type hand
     */
    private boolean isValidFourOfAKindHand(Hand hand) {
        int numRank1 = 0;
        int rank1 = hand.getHand().get(0).getRank();
        for (Card card : hand.getHand()) {
            if (card.getRank() == rank1) {
                numRank1++;
            }
        }
        if (numRank1 != 4) {
            int numRank2 = 0;
            int rank2 = hand.getHand().get(1).getRank();
            for (Card card : hand.getHand()) {
                if (card.getRank() == rank2) {
                    numRank2++;
                }
            }
            return numRank2 == 4;
        }
        return true;
    }

    /**
     * EFFECTS: returns true if hand is a valid straight flush type hand
     */
    private boolean isValidStriaghtFlushHand(Hand hand) {
        return isValidStraightHand(hand) && isValidFlushHand(hand);
    }

    /**
     * EFFECTS: returns true if hand is a valid royal straight flush type hand
     */
    private boolean isValidRoyalStriahgtFlushHand(Hand hand) {
        return isValidStriaghtFlushHand(hand) && hand.getHand().contains(1);
    }
}
