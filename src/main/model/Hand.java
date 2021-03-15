package model;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
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
    private static final List<String> FIVE_HAND_HIERARCHY = new ArrayList<>(Arrays.asList("straight",
            "flush", "fullHouse", "fourOfAKind", "straightFlush", "royalStraightFlush"));

    //EFFECTS: Makes an empty new Hand
    public Hand() {
        super();
    }

    //EFFECTS: Makes a new Hand with initial cards
    public Hand(List<Card> cards) {
        super(cards);
    }

//    @Override
//    public void draw(Graphics g) {
    public void draw(JPanel parent) {
        //TODO: IMPLEMENT DRAW
        for (Card card : listOfCards) {
//            card.draw(g);
//            card.draw();
        }
        String sep = System.getProperty("file.separator");
        ImageIcon trial = new ImageIcon(System.getProperty("user.dir") + sep
                + "images" + sep + "anotherDress.jpg");
        JLabel imageAsLabel = new JLabel(trial);
        parent.add(imageAsLabel);
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
        } else if (otherSize == 1 && isValidSingleCardHand()) {
            return canPlaySingleHand(other);
        } else if (otherSize == 2 && isValidPairHand()) {
            return canPlayPairHand(other);
        } else if (otherSize == 3 && isValidThreeOfAKindHand()) {
            return canPlayThreeOfAKindHand(other);
        } else if (otherSize == 5 && isValidFiveCardHand()) {
            return canPlayFiveCardHand(other);
        }
        return false;
    }

    /**
     * REQUIRES: this hand is a valid single type hand
     * EFFECTS: returns true if playing this single hand is valid, false otherwise
     * - if this rank is = other rank: this suit must be > other suit
     * - if this rank is > other rank: is valid
     */
    private boolean canPlaySingleHand(Hand other) {
        int otherRank = other.getCard(0).getRank();
        int thisRank = getCard(0).getRank();
        String otherSuit = other.getCard(0).getSuit();
        if (thisRank == otherRank) {
            return SUITS.indexOf(getCard(0).getSuit()) > SUITS.indexOf(otherSuit);
        }
        return thisIsHigherCard(thisRank, otherRank);
    }

    /**
     * REQUIRES: this hand is a valid pair type hand
     * EFFECTS: returns true if playing this pair hand is valid, false otherwise
     * - if this rank is = other rank: this highest suit must be > other highest suit
     * - if this rank is > other rank: is valid
     */
    private boolean canPlayPairHand(Hand other) {
        int otherRank = other.getCard(0).getRank();
        int thisRank = getCard(0).getRank();
        if (thisRank == otherRank) {
            int highestThisSuit = Math.max(SUITS.indexOf(getCard(0).getSuit()),
                    SUITS.indexOf(getCard(1).getSuit()));
            int highestOtherSuit = Math.max(SUITS.indexOf(other.getCard(0).getSuit()),
                    SUITS.indexOf(other.getCard(1).getSuit()));
            return highestThisSuit > highestOtherSuit;
        }
        return thisIsHigherCard(thisRank, otherRank);
    }

    /**
     * REQUIRES: this hand is a valid three of a kind type hand
     * EFFECTS: returns true if playing this three of a kind hand is valid, false otherwise
     * - this rank must be > other rank
     */
    private boolean canPlayThreeOfAKindHand(Hand other) {
        int otherRank = other.getCard(0).getRank();
        int thisRank = getCard(0).getRank();
        return thisIsHigherCard(thisRank, otherRank);
    }

    /*
     REQUIRES: this hand is a valid five card type hand
     EFFECTS: returns true if playing this five card hand is valid, false otherwise
              - if other and this hierarchy are equal:
                    valid if this is higher than other in respective type
              - else: valid if hierarchy of this hand type is > other hand type
                        according to FIVE_HAND_HIERARCHY
     */
    private boolean canPlayFiveCardHand(Hand other) {
        int otherTypeRank = other.findHandType();
        int thisTypeRank = findHandType();
        if (thisTypeRank == otherTypeRank) {
            if (thisTypeRank == 0) {
                return canPlayStraight(other);
            } else if (thisTypeRank == 1) {
                return canPlayFlush(other);
            } else if (thisTypeRank == 2) {
                return canPlayFullHouse(other);
            } else if (thisTypeRank == 3) {
                return canPlayFourOfAKind(other);
            } else if (thisTypeRank == 4) {
                return canPlayStraightFlush(other);
            } else {
                return canPLayRoyalStraightFlush(other);
            }
        }
        return thisTypeRank > otherTypeRank;
    }

    //REQUIRES: this is a valid five card type hand
    //EFFECTS: returns index ranking of hand type according to FIVE_HAND_HIERARCHY
    private int findHandType() {
        if (!isValidStraightFlushHand() && isValidStraightHand()) {
            return FIVE_HAND_HIERARCHY.indexOf("straight");
        } else if (!isValidStraightFlushHand() && isValidFlushHand()) {
            return FIVE_HAND_HIERARCHY.indexOf("flush");
        } else if (isValidFullHouseHand()) {
            return FIVE_HAND_HIERARCHY.indexOf("fullHouse");
        } else if (isValidFourOfAKindHand()) {
            return FIVE_HAND_HIERARCHY.indexOf("fourOfAKind");
        } else if (!isValidRoyalStraightFlushHand()) {
            return FIVE_HAND_HIERARCHY.indexOf("straightFlush");
        } else {
            return FIVE_HAND_HIERARCHY.indexOf("royalStraightFlush");
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
        }
        return thisIsHigherCard(thisHighestRank, otherHighestRank);
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
            return thisIsHigherCard(thisRank, otherRank);
        } else {
            return SUITS.indexOf(thisSuit) > SUITS.indexOf(otherSuit);
        }
    }

    /*
    REQUIRES: this is a valid full house type hand
    EFFECTS: returns true if playing this full house hand is valid, false otherwise
              - this rank of three of a kind must be > other rank of three of a kind
     */
    private boolean canPlayFullHouse(Hand other) {
        int other3Rank = other.findNumCardRepeatRank(3);
        int this3Rank = this.findNumCardRepeatRank(3);
        return thisIsHigherCard(this3Rank, other3Rank);
    }

    /*
    REQUIRES: this is a valid 4 of a kind type hand
    EFFECTS: returns true if playing this 4 of a kind hand is valid, false otherwise
              - this rank of 4 of a kind must be > other rank of 4 of a kind
     */
    private boolean canPlayFourOfAKind(Hand other) {
        int other4Rank = other.findNumCardRepeatRank(4);
        int this4Rank = this.findNumCardRepeatRank(4);
        return thisIsHigherCard(this4Rank, other4Rank);
    }

    /*
    REQUIRES: this is a valid straight flush type hand
    EFFECTS: returns true if playing this straight flush hand is valid, false otherwise
              - if this suit = other suit: this highest rank must be > other highest rank
              - if this suit > other suit: is valid
     */
    private boolean canPlayStraightFlush(Hand other) {
        return canPlayFlush(other);
    }

    /*
    REQUIRES: this is a valid royal straight flush type hand
    EFFECTS: returns true if playing this royal straight flush hand is valid, false otherwise
              - this suit must be > other suit
     */
    private boolean canPLayRoyalStraightFlush(Hand other) {
        return canPlayStraightFlush(other);
    }

    //EFFECTS: returns true if hand is a valid type hand, false otherwise
    public boolean isValidTypeHand() {
        return (isValidSingleCardHand() || isValidPairHand()
                || isValidThreeOfAKindHand() || isValidFiveCardHand());
    }

    /*
    EFFECTS: returns true if hand is a valid single type hand
              - hand only has one card
     */
    private boolean isValidSingleCardHand() {
        return this.getSize() == 1;
    }

    /*
    REQUIRES: size of hand is >= 2
    EFFECTS: returns true if hand is a valid pair type hand
              - hand only has 2 cards
              - rank of both cards are equal
     */
    private boolean isValidPairHand() {
        if (this.getSize() != 2) {
            return false;
        }
        int firstCardRank = this.getHand().get(0).getRank();
        int secondCardRank = this.getHand().get(1).getRank();
        return firstCardRank == secondCardRank;
    }

    /*
    EFFECTS: returns true if hand is a valid three of a kind type hand
              - hand only has 3 cards
              - rank of all 3 cards are equal
     */
    private boolean isValidThreeOfAKindHand() {
        if (this.getSize() != 3) {
            return false;
        }
        int firstCardRank = this.getHand().get(0).getRank();
        int secondCardRank = this.getHand().get(1).getRank();
        int thirdCardRank = this.getHand().get(2).getRank();
        return firstCardRank == secondCardRank && secondCardRank == thirdCardRank;
    }

    /*
    EFFECTS: returns true if hand is a valid 5 card hand type
              - hand has only 5 cards
     */
    private boolean isValidFiveCardHand() {
        return (this.getSize() == 5) && (isValidStraightHand() || isValidFlushHand()
                || isValidFullHouseHand() || isValidFourOfAKindHand());
    }

    /*
    EFFECTS: returns true if hand is a valid straight type hand
              - rank of cards are in a sequence
     */
    private boolean isValidStraightHand() {
        int rank = this.getCard(0).getRank() - 1;
        for (Card card : this.getHand()) {
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
    private boolean isValidFlushHand() {
        String suit = this.getHand().get(0).getSuit();
        for (Card card : this.getHand()) {
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
    protected boolean isValidFullHouseHand() {
        int numFirstRank = 0;
        int numSecondRank = 0;
        int firstRank = this.getHand().get(0).getRank();
        int secondRank = this.getHand().get(0).getRank();
        for (Card card : this.getHand()) {
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
        return (numFirstRank == 3 && numSecondRank == 2) || (numSecondRank == 3 && numFirstRank == 2);
    }

    /*
    EFFECTS: returns true if hand is a valid four of a kind type hand
              - 4 of 5 cards must have equal ranks
     */
    private boolean isValidFourOfAKindHand() {
        int numRank1 = 0;
        int rank1 = this.getHand().get(0).getRank();
        for (Card card : this.getHand()) {
            if (card.getRank() == rank1) {
                numRank1++;
            }
        }
        if (numRank1 != 4) {
            int numRank2 = 0;
            int rank2 = this.getHand().get(1).getRank();
            for (Card card : this.getHand()) {
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
    private boolean isValidStraightFlushHand() {
        return isValidStraightHand() && isValidFlushHand();
    }

    /*
    EFFECTS: returns true if hand is a valid royal straight flush type hand
              - hand is a valid straight flush type hand and contains an ace
     */
    protected boolean isValidRoyalStraightFlushHand() {
        boolean hasAce = false;
        for (Card card : this.getListOfCards()) {
            if (card.getRank() == 1) {
                hasAce = true;
                break;
            }
        }
        return isValidStraightFlushHand() && hasAce;
    }

    //EFFECTS: returns the rank of the cards that this hand has repeat num times (ie. pairs, triples, etc.)
    private int findNumCardRepeatRank(int num) {
        int rank = 0;
        int numRank = 0;
        for (int i = 0; i < this.getSize(); i++) {
            rank = this.getCard(i).getRank();
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
}
