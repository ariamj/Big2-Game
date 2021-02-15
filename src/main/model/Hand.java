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

    //EFFECTS: Makes an empty new Hand
    public Hand() {
        super();
    }

    //EFFECTS: Makes a new Hand with initial cards
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
     * EFFECTS: returns true if playing this hand is a valid move, false otherwise
     * - valid based on number of cards already played on table and order of types
     */
    public boolean isValidPlay(Hand other) {
        int otherSize = other.getSize();
        if (this.getSize() > 5 || this.getSize() < 1) {
            return false;
        } else if (otherSize == 0) {
            return true;
        } else if (otherSize == 1 && isValidSingleCardHand(this)) {
            return canPlaySingleHand(other);
        } else if (otherSize == 2 && isValidPairHand(this)) {
            return canPlayPairHand(other);
        } else if (otherSize == 3 && isValidThreeOfAKindHand(this)) {
            return canPlayThreeOfAKindHand(other);
        } else {
            return canPlayFiveCardHand(other);
        }
    }

    /**
     * REQUIRES: this hand is a valid single type hand
     * EFFECTS: returns true if playing this single hand is valid, false otherwise
     * - if this rank is = other rank: this suit must be > other suit
     * - if this rank is > other rank: is valid
     */
    private boolean canPlaySingleHand(Hand other) {
        int otherRank = other.getListOfCards().get(0).getRank();
        int thisRank = listOfCards.get(0).getRank();
        String otherSuit = other.getListOfCards().get(0).getSuit();
        if (thisRank == otherRank) {
            return SUITS.indexOf(listOfCards.get(0).getSuit()) > SUITS.indexOf(otherSuit);
        } else {
            if (thisRank == 1 || thisRank == 2) {
                return kingAceBorder(thisRank, otherRank);
            } else if (otherRank == 1 || otherRank == 2) {
                return !kingAceBorder(otherRank, thisRank);
            }
            return thisRank > otherRank;
        }
    }

    /**
     * REQUIRES: this hand is a valid pair type hand
     * EFFECTS: returns true if playing this pair hand is valid, false otherwise
     * - if this rank is = other rank: this highest suit must be > other highest suit
     * - if this rank is > other rank: is valid
     */
    private boolean canPlayPairHand(Hand other) {
        int otherRank = other.getListOfCards().get(0).getRank();
        int thisRank = listOfCards.get(0).getRank();
        if (thisRank == otherRank) {
            int highestThisSuit = Math.max(SUITS.indexOf(listOfCards.get(0).getSuit()),
                    SUITS.indexOf(listOfCards.get(1).getSuit()));
            int highestOtherSuit = Math.max(SUITS.indexOf(other.getListOfCards().get(0).getSuit()),
                    SUITS.indexOf(other.getListOfCards().get(1).getSuit()));
            return highestThisSuit > highestOtherSuit;
        } else {
            if (thisRank == 1 || thisRank == 2) {
                return kingAceBorder(thisRank, otherRank);
            } else if (otherRank == 1 || otherRank == 2) {
                return !kingAceBorder(otherRank, thisRank);
            }
            return thisRank > otherRank;
        }
    }

    /**
     * REQUIRES: this hand is a valid three of a kind type hand
     * EFFECTS: returns true if playing this three of a kind hand is valid, false otherwise
     * - this rank must be > other rank
     */
    private boolean canPlayThreeOfAKindHand(Hand other) {
        int otherRank = other.getListOfCards().get(0).getRank();
        int thisRank = listOfCards.get(0).getRank();
        if (thisRank == 1 || thisRank == 2) {
            return kingAceBorder(thisRank, otherRank);
        } else if (otherRank == 1 || otherRank == 2) {
            return !kingAceBorder(otherRank, thisRank);
        }
        return thisRank > otherRank;
    }

    /*
     REQUIRES: this hand is a valid five card type hand
     EFFECTS: returns true if playing this five card hand is valid, false otherwise
                - if other is a straight: valid if this is either a higher straight or any other five card hand
                - if  other is a flush: valid if this is either a higher flush or any other 5 card hand > flush
                - if  other is a full house: valid if this is either a higher full house or any other 5 card
                                             hand > full house
                - if  other is a 4 of a kind: valid if this is either a higher 4 of a kind or any other 5 card
                                              hand > 4 of a kind
                - if  other is a straight flush: valid if this is either a higher straight flush or royal
                                                 straight flush
                - if  other is a royal straight flush: valid if this is a higher royal straight flush
     */
    private boolean canPlayFiveCardHand(Hand other) {
        if (isValidStraightHand(other) && !isValidStraightFlushHand(other)) {
            return canPlayStraight(other) || isValidFlushHand(this) || isValidFullHouseHand(this)
                    || isValidFourOfAKindHand(this) || isValidStraightFlushHand(this);
        } else if (isValidFlushHand(other) && !isValidStraightFlushHand(other)) {
            return !isValidStraightHand(this) && (canPlayFlush(other) || isValidFullHouseHand(this)
                    || isValidFourOfAKindHand(this) || isValidStraightFlushHand(this));
        } else if (isValidFullHouseHand(other)) {
            return !((isValidStraightHand(this) || isValidFlushHand(this)) && !isValidStraightFlushHand(this))
                    && (canPLayFullHouse(other) || isValidFourOfAKindHand(this) || isValidStraightFlushHand(this));
        } else if (isValidFourOfAKindHand(other)) {
            return !(((isValidStraightHand(this) || isValidFlushHand(this)) && !isValidStraightFlushHand(this))
                    || isValidFullHouseHand(this)) && (canPLayFourOfAKind(other) || isValidStraightFlushHand(this));
        } else if (isValidStraightFlushHand(other) && !isValidRoyalStraightFlushHand(other)) {
            return !(((isValidStraightHand(this) || isValidFlushHand(this)) && !isValidStraightFlushHand(this))
                    || isValidFullHouseHand(this) || isValidFourOfAKindHand(this)) && (canPLayStraightFlush(other));
        } else {
            return !(((isValidStraightHand(this) || isValidFlushHand(this)) && !isValidStraightFlushHand(this))
                    || isValidFullHouseHand(this) || isValidFourOfAKindHand(this))
                    && canPLayRoyalStraightFlush(other);
        }
    }

    /*
    REQUIRES: this is a valid straight type hand
    EFFECTS: returns true if playing this straight hand is valid, false otherwise
              - if this highest rank = other highest rank: this suit of highest rank must be > other suit of
                                                           highest rank
              - if this highest rank > other highest rank: is valid
     */
    private boolean canPlayStraight(Hand other) {
        Card thisHighestCard = this.highestCard();
        Card otherHighestCard = other.highestCard();
        int thisHighestRank = thisHighestCard.getRank();
        int otherHighestRank = otherHighestCard.getRank();
        if (thisHighestRank == otherHighestRank) {
            int indexOfThisHighestSuit = SUITS.indexOf(thisHighestCard.getSuit());
            int indexOfOtherHighestSuit = SUITS.indexOf(otherHighestCard.getSuit());
            return indexOfThisHighestSuit > indexOfOtherHighestSuit;
        } else {
            if (thisHighestRank == 1 || thisHighestRank == 2) {
                return kingAceBorder(thisHighestRank, otherHighestRank);
            } else if (otherHighestRank == 1 || otherHighestRank == 2) {
                return !(kingAceBorder(otherHighestRank, thisHighestRank));
            }
            return thisHighestRank > otherHighestRank;
        }
    }

    /*
    REQUIRES: this is a valid flush type hand
    EFFECTS: returns true if playing this flush hand is valid, false otherwise
              - if this suit = other suit: this highest rank must be > other highest rank
              - if this suit > other suit: is valid
     */
    private boolean canPlayFlush(Hand other) {
        String otherSuit = other.getHand().get(0).getSuit();
        String thisSuit = this.getHand().get(0).getSuit();
        int thisRank = this.highestCard().getRank();
        int otherRank = other.highestCard().getRank();
        if (thisSuit.equals(otherSuit)) {
            if (thisRank == 1 || thisRank == 2) {
                return kingAceBorder(thisRank, otherRank);
            } else if (otherRank == 1 || otherRank == 2) {
                return !(kingAceBorder(otherRank, thisRank));
            }
            return thisRank > otherRank;
        } else {
            return SUITS.indexOf(thisSuit) > SUITS.indexOf(otherSuit);
        }
    }

    /*
    REQUIRES: this is a valid full house type hand
    EFFECTS: returns true if playing this full house hand is valid, false otherwise
              - this rank of three of a kind must be > other rank of three of a kind
     */
    private boolean canPLayFullHouse(Hand other) {
        int other3Rank = other.findNumCardRepeatRank(3);
        int this3Rank = this.findNumCardRepeatRank(3);
        if (this3Rank == 1 || this3Rank == 2) {
            return kingAceBorder(this3Rank, other3Rank);
        } else if (other3Rank == 1 || other3Rank == 2) {
            return !kingAceBorder(other3Rank, this3Rank);
        }
        return this3Rank > other3Rank;
    }

    /*
    REQUIRES: this is a valid 4 of a kind type hand
    EFFECTS: returns true if playing this 4 of a kind hand is valid, false otherwise
              - this rank of 4 of a kind must be > other rank of 4 of a kind
     */
    private boolean canPLayFourOfAKind(Hand other) {
        int other4Rank = other.findNumCardRepeatRank(4);
        int this4Rank = this.findNumCardRepeatRank(4);
        if (this4Rank == 1 || this4Rank == 2) {
            return kingAceBorder(this4Rank, other4Rank);
        } else if (other4Rank == 1 || other4Rank == 2) {
            return !kingAceBorder(other4Rank, this4Rank);
        }
        return this4Rank > other4Rank;
    }

    /*
    REQUIRES: this is a valid straight flush type hand
    EFFECTS: returns true if playing this straight flush hand is valid, false otherwise
              - if this suit = other suit: this highest rank must be > other highest rank
              - if this suit > other suit: is valid
     */
    private boolean canPLayStraightFlush(Hand other) {
        return canPlayFlush(other);
    }

    /*
    REQUIRES: this is a valid royal straight flush type hand
    EFFECTS: returns true if playing this royal straight flush hand is valid, false otherwise
              - this suit must be > other suit
     */
    private boolean canPLayRoyalStraightFlush(Hand other) {
        return canPLayStraightFlush(other);
    }

    //EFFECTS: returns true if hand is a valid type hand, false otherwise
    public boolean isValidTypeHand() {
        return (isValidSingleCardHand(this) || isValidPairHand(this)
                || isValidThreeOfAKindHand(this) || isValidFiveCardHand(this));
    }

    /*
    EFFECTS: returns true if hand is a valid single type hand
              - hand only has one card
     */
    private boolean isValidSingleCardHand(Hand hand) {
        return hand.getSize() == 1;
    }

    /*
    REQUIRES: size of hand is >= 2
    EFFECTS: returns true if hand is a valid pair type hand
              - hand only has 2 cards
              - rank of both cards are equal
     */
    private boolean isValidPairHand(Hand hand) {
        int firstCardRank = hand.getHand().get(0).getRank();
        int secondCardRank = hand.getHand().get(1).getRank();
        return (hand.getSize() == 2) && (firstCardRank == secondCardRank);
    }

    /*
    EFFECTS: returns true if hand is a valid three of a kind type hand
              - hand only has 3 cards
              - rank of all 3 cards are equal
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

    /*
    EFFECTS: returns true if hand is a valid 5 card hand type
              - hand has only 5 cards
     */
    private boolean isValidFiveCardHand(Hand hand) {
        return (hand.getSize() == 5) && (isValidStraightHand(hand) || isValidFlushHand(hand)
                || isValidFullHouseHand(hand) || isValidFourOfAKindHand(hand) || isValidStraightFlushHand(hand)
                || isValidRoyalStraightFlushHand(hand));
    }

    /*
    EFFECTS: returns true if hand is a valid straight type hand
              - rank of cards are in a sequence
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

    /*
    EFFECTS: returns true if hand is a valid flush type hand
              - suit of all cards must be equal
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

    /*
    EFFECTS: returns true if hand is a valid full house type hand
              - hand has both a three of a kind and a pair
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

    /*
    EFFECTS: returns true if hand is a valid four of a kind type hand
              - 4 of 5 cards must have equal ranks
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

    /*
    EFFECTS: returns true if hand is a valid straight flush type hand
              - hand is a valid straight type hand and a valid flush type hand
     */
    private boolean isValidStraightFlushHand(Hand hand) {
        return isValidStraightHand(hand) && isValidFlushHand(hand);
    }

    /*
    EFFECTS: returns true if hand is a valid royal straight flush type hand
              - hand is a valid straight flush type hand and contains an ace
     */
    private boolean isValidRoyalStraightFlushHand(Hand hand) {
        boolean hasAce = false;
        for (Card card : hand.getListOfCards()) {
            if (card.getRank() == 1) {
                hasAce = true;
                break;
            }
        }
        return isValidStraightFlushHand(hand) && hasAce;
    }

    //EFFECTS: returns the rank of the cards that this hand has repeat num times (ie. pairs, triples, etc.)
    private int findNumCardRepeatRank(int num) {
        int rank = 0;
        int numRank = 0;
        for (int i = 0; i < this.getSize(); i++) {
            rank = this.listOfCards.get(i).getRank();
            for (Card card : this.listOfCards) {
                if (card.getRank() == rank) {
                    numRank++;
                }
            }
            if (numRank == num) {
                break;
            }
        }
        return rank;
    }

    /*
    EFFECTS: returns true is rank1 is > rank2 dealing with the border of King and Ace
                  - 2 is higher than ace and ace is higher than king
     */
    private boolean kingAceBorder(int rank1, int rank2) {
        return ((rank1 == 1 || rank1 == 2) && (rank2 <= 13 && rank2 >= 3));
    }
}
