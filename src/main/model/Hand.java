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
        int otherSize = other.getSize();
        if (otherSize == 1 && isValidSingleCardHand(this)) {
            //check single
            return canPlaySingleHand(other);
        } else if (otherSize == 2 && isValidPairHand(this)) {
            //check pair
            return canPlayPairHand(other);
        } else if (otherSize == 3 && isValidThreeOfAKindHand(this)) {
            //check three of a kind
            return canPlayThreeOfAKindHand(other);
        } else if (otherSize == 5 && isValidFiveCardHand(this)) {
            //check five card hands
            return canPlayFiveCardHand(other);
        } else {
            return false;
        }
    }

    private boolean canPlaySingleHand(Hand other) {
        int otherRank = other.getListOfCards().get(0).getRank();
        String otherSuit = other.getListOfCards().get(0).getSuit();
        if (listOfCards.get(0).getRank() == otherRank) {
            return SUITS.indexOf(listOfCards.get(0).getSuit()) > SUITS.indexOf(otherSuit);
        } else if (listOfCards.get(0).getRank() > otherRank) {
            return true;
        }
        return false;
    }

    private boolean canPlayPairHand(Hand other) {
        int otherRank = other.getListOfCards().get(0).getRank();
        if (listOfCards.get(0).getRank() == otherRank) {
            int highestThisSuit = (int) Math.max(SUITS.indexOf(listOfCards.get(0).getSuit()),
                    SUITS.indexOf(listOfCards.get(1).getSuit()));
            int highestOtherSuit = (int) Math.max(SUITS.indexOf(other.getListOfCards().get(0).getSuit()),
                    SUITS.indexOf(other.getListOfCards().get(1).getSuit()));
            return highestThisSuit > highestOtherSuit;
        } else if (listOfCards.get(0).getRank() > otherRank) {
            return true;
        }
        return false;
    }

    private boolean canPlayThreeOfAKindHand(Hand other) {
        int otherRank = other.getListOfCards().get(0).getRank();
        int thisRank = listOfCards.get(0).getRank();
        return thisRank > otherRank;
    }

    private boolean canPlayFiveCardHand(Hand other) {
        if (isValidStraightHand(other)) {
            //check >= straight can play
            return canPlayStraight(other) || isValidFlushHand(this) || isValidFullHouseHand(this)
                    || isValidFourOfAKindHand(this) || isValidStraightHand(this);
        } else if (isValidFlushHand(other)) {
            //check >= flush can play
            return !isValidStraightHand(this) && (canPlayFlush(other) || isValidFullHouseHand(this)
                    || isValidFourOfAKindHand(this) || isValidStraightFlushHand(this));
        } else if (isValidFullHouseHand(other)) {
            //check >= full house can play
            return !(isValidStraightHand(this) || isValidFlushHand(this)) && (canPLayFullHouse(other)
                    || isValidFourOfAKindHand(this) || isValidStraightFlushHand(this));
        } else if (isValidFourOfAKindHand(other)) {
            //check >= four of a kind can play
            return !(isValidStraightHand(this) || isValidFlushHand(this) || isValidFullHouseHand(this))
                    && (canPLayFourOfAKind(other) || isValidStraightFlushHand(this));
        } else if (isValidStraightFlushHand(other) && !isValidRoyalStraightFlushHand(other)) {
            //check >= straight flush can play
            return !(isValidStraightHand(this) || isValidFlushHand(this) || isValidFullHouseHand(this)
                    || isValidFourOfAKindHand(this)) && (canPLayStraightFlush(other));
        } else {
            //check >= royal straight flush can play
            return !(isValidStraightHand(this) || isValidFlushHand(this) || isValidFullHouseHand(this)
                    || isValidFourOfAKindHand(this) || isValidStraightFlushHand(this))
                    && canPLayRoyalStraightFlush(other);
        }
    }

    private boolean canPlayStraight(Hand other) {
        Card thisHighestCard = this.highestCard();
        Card otherHighestCard = other.highestCard();
        int thisHighestRank = thisHighestCard.getRank();
        int otherHighestRank = otherHighestCard.getRank();
        if (thisHighestRank == otherHighestRank) {
            int indexOfThisHighestSuit = SUITS.indexOf(thisHighestCard.getSuit());
            int indexOfOtherHighestSuit = SUITS.indexOf(otherHighestCard.getSuit());
            return indexOfThisHighestSuit > indexOfOtherHighestSuit;
        } else if (thisHighestRank > otherHighestRank) {
            return true;
        }
        return false;
    }

    private boolean canPlayFlush(Hand other) {
        return false;
    }

    private boolean canPLayFullHouse(Hand other) {
        return false;
    }

    private boolean canPLayFourOfAKind(Hand other) {
        return false;
    }

    private boolean canPLayStraightFlush(Hand other) {
        return false;
    }

    private boolean canPLayRoyalStraightFlush(Hand other) {
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
                || isValidFullHouseHand(hand) || isValidFourOfAKindHand(hand) || isValidStraightFlushHand(hand)
                || isValidRoyalStraightFlushHand(hand));
    }

    /**
     * EFFECTS: returns true if hand is a valid straight type hand
     */
    private boolean isValidStraightHand(Hand hand) {
        int rank = hand.getListOfCards().get(0).getRank() - 1;
        for (Card card : hand.getHand()) {
            if (!(rank == 13 && card.getRank() == 1) && !(card.getRank() == rank + 1)) {
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
    private boolean isValidStraightFlushHand(Hand hand) {
        return isValidStraightHand(hand) && isValidFlushHand(hand);
    }

    /**
     * EFFECTS: returns true if hand is a valid royal straight flush type hand
     */
    private boolean isValidRoyalStraightFlushHand(Hand hand) {
        boolean hasAce = false;
        for (String suit : SUITS) {
            hasAce = hasAce || hand.getHand().contains(new Card(1, suit));
        }
        return isValidStraightFlushHand(hand) && hasAce;
    }
}
