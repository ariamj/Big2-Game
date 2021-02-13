package model;

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
public class Hand {
    private List<Card> hand;

    public Hand(List<Card> cards) {
        hand = cards;
    }

    //getters
    public List<Card> getHand() {
        return this.hand;
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
    public boolean isValidTypeHand(Hand hand) {
        return (isValidSingleCardHand(hand) || isValidPairHand(hand)
                || isValidThreeOfAKindHand(hand) || isValidFiveCardHand(hand));
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
        int firstCardRank = hand.getHand().get(0).getRank();
        int secondCardRank = hand.getHand().get(1).getRank();
        int thirdCardRank = hand.getHand().get(2).getRank();
        return hand.getSize() == 3 && firstCardRank == secondCardRank && secondCardRank == thirdCardRank;
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
        return false;
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
        return false;
    }

    /**
     * EFFECTS: returns true if hand is a valid four of a kind type hand
     */
    private boolean isValidFourOfAKindHand(Hand hand) {
        return false;
    }

    /**
     * EFFECTS: returns true if hand is a valid straight flush type hand
     */
    private boolean isValidStriaghtFlushHand(Hand hand) {
        return false;
    }

    /**
     * EFFECTS: returns true if hand is a valid royal straight flush type hand
     */
    private boolean isValidRoyalStriahgtFlushHand(Hand hand) {
        return false;
    }

    //EFFECTS: returns number of cards in hand
    public int getSize() {
        return hand.size();
    }

    //EFFECTS: returns string representation of the hand
    public String toString() {
        String hand = "[";
        for (int i = 0; i < this.hand.size(); i++) {
            hand += this.hand.get(i).toString();
            if (i != this.hand.size() - 1) {
                hand += ", ";
            }
        }
        return hand + "]";
    }
}
