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

    //EFFECTS: returns true is this is greater than other, false otherwise
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

    private boolean canPLayStraightFlush(Hand other) {
        return canPlayFlush(other);
    }

    private boolean canPLayRoyalStraightFlush(Hand other) {
        return canPLayStraightFlush(other);
    }

    //EFFECTS: returns true if hand is a valid type hand, false otherwise
    public boolean isValidTypeHand() {
        return (isValidSingleCardHand(this) || isValidPairHand(this)
                || isValidThreeOfAKindHand(this) || isValidFiveCardHand(this));
    }

    //EFFECTS: returns true if hand is a valid single type hand
    private boolean isValidSingleCardHand(Hand hand) {
        return hand.getSize() == 1;
    }

    //REQUIRES: size of hand is >= 2
    //EFFECTS: returns true if hand is a valid pair type hand
    private boolean isValidPairHand(Hand hand) {
        int firstCardRank = hand.getHand().get(0).getRank();
        int secondCardRank = hand.getHand().get(1).getRank();
        return (hand.getSize() == 2) && (firstCardRank == secondCardRank);
    }

    //EFFECTS: returns true if hand is a valid three of a kind type hand
    private boolean isValidThreeOfAKindHand(Hand hand) {
        if (hand.getSize() != 3) {
            return false;
        }
        int firstCardRank = hand.getHand().get(0).getRank();
        int secondCardRank = hand.getHand().get(1).getRank();
        int thirdCardRank = hand.getHand().get(2).getRank();
        return firstCardRank == secondCardRank && secondCardRank == thirdCardRank;
    }

    //EFFECTS: returns true if hand is a valid 5 card hand type
    private boolean isValidFiveCardHand(Hand hand) {
        return (hand.getSize() == 5) && (isValidStraightHand(hand) || isValidFlushHand(hand)
                || isValidFullHouseHand(hand) || isValidFourOfAKindHand(hand) || isValidStraightFlushHand(hand)
                || isValidRoyalStraightFlushHand(hand));
    }

    //EFFECTS: returns true if hand is a valid straight type hand
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

    //EFFECTS: returns true if hand is a valid flush type hand
    private boolean isValidFlushHand(Hand hand) {
        String suit = hand.getHand().get(0).getSuit();
        for (Card card : hand.getHand()) {
            if (!card.getSuit().equals(suit)) {
                return false;
            }
        }
        return true;
    }

    //EFFECTS: returns true if hand is a valid full house type hand
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

    //EFFECTS: returns true if hand is a valid four of a kind type hand
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

    //EFFECTS: returns true if hand is a valid straight flush type hand
    private boolean isValidStraightFlushHand(Hand hand) {
        return isValidStraightHand(hand) && isValidFlushHand(hand);
    }

    //EFFECTS: returns true if hand is a valid royal straight flush type hand
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

    //helper
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

    //helper
    private boolean kingAceBorder(int rank1, int rank2) {
        return ((rank1 == 1 || rank1 == 2) && (rank2 <= 13 && rank2 >= 3));
    }
}
