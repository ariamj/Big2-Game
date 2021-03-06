package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class HandTest {
    private final Card D3 = new Card(3, "diamond");
    private final Card C3 = new Card(3, "clubs");
    private final Card H3 = new Card(3, "heart");
    private final Card S3 = new Card(3, "spade");
    private final Card H8 = new Card(8, "heart");
    private final Card S8 = new Card(8, "spade");
    private final Card H9 = new Card(9, "heart");
    private final Card S9 = new Card(9, "spade");
    private final Card D10 = new Card(10, "diamond");
    private final Card H10 = new Card(10, "heart");
    private final Card S10 = new Card(10, "spade");
    private final Card H11 = new Card(11, "heart");
    private final Card S11 = new Card(11, "spade");
    private final Card D12 = new Card(12, "diamond");
    private final Card C12 = new Card(12, "clubs");
    private final Card H12 = new Card(12, "heart");
    private final Card S12 = new Card(12, "spade");
    private final Card D13 = new Card(13, "diamond");
    private final Card C13 = new Card(13, "clubs");
    private final Card H13 = new Card(13, "heart");
    private final Card S13 = new Card(13, "spade");
    private final Card D1 = new Card(1, "diamond");
    private final Card C1 = new Card(1, "clubs");
    private final Card H1 = new Card(1, "heart");
    private final Card S1 = new Card(1, "spade");
    private final Card H2 = new Card(2, "heart");
    private final Card C2 = new Card(2, "clubs");
    private final Card S2 = new Card(2, "spade");

    private Hand startHand;

    @BeforeEach
    public void runBefore() {
        startHand = new Hand();
    }

    @Test
    public void testSetHand() {
        ListOfCards newHand = new Hand(new ArrayList<>(Arrays.asList(D3, C3, H3, S3)));
        startHand.setHand(newHand);
        assertEquals(4, startHand.getSize());
        assertTrue(startHand.contains(D3));
        assertTrue(startHand.contains(C3));
        assertTrue(startHand.contains(H3));
        assertTrue(startHand.contains(S3));
        assertFalse(startHand.contains(S1));
    }

    @Test
    public void testIsValidPlaySizeFalse() {
        Hand emptyHand = new Hand();
        Hand hand = new Hand(new ArrayList<>(Arrays.asList(H1, D13, C13, H13, S13)));
        Hand falseHand = new Hand(new ArrayList<>(Arrays.asList(H1, D13, C13, H13, S13, S1)));
        assertFalse(falseHand.isValidPlay(emptyHand));
        assertFalse(falseHand.isValidPlay(hand));
        assertFalse(emptyHand.isValidPlay(hand));
    }

    @Test
    public void testIsValidPlayOnEmptyHand() {
        Hand emptyHand1 = new Hand();
        Hand singleOnEmptyHand2 = new Hand();
        singleOnEmptyHand2.addCard(D1);
        assertTrue(singleOnEmptyHand2.isValidPlay(emptyHand1));
    }

    @Test
    public void testIsValidPlayFalseType() {
        Hand singleHand1 = new Hand();
        singleHand1.addCard(D13);
        Hand pairHand1 = new Hand(new ArrayList<>(Arrays.asList(D13, H13)));
        Hand threeHand1 = new Hand(new ArrayList<>(Arrays.asList(D13, H13, S13)));
        Hand straight1 = new Hand(new ArrayList<>(Arrays.asList(H9, D10, H11, H12, D13)));
        assertFalse(pairHand1.isValidPlay(singleHand1));
        assertFalse(singleHand1.isValidPlay(pairHand1));
        assertFalse(singleHand1.isValidPlay(threeHand1));
        assertFalse(singleHand1.isValidPlay(straight1));
    }

    @Test
    public void testIsValidPlaySingleCardOtherIsAceTwo() {
        Hand singleHand13 = new Hand();
        singleHand13.addCard(D13);
        Hand singleHandD1 = new Hand();
        singleHandD1.addCard(D1);
        Hand singleHand2 = new Hand();
        singleHand2.addCard(S2);
        assertFalse(singleHand13.isValidPlay(singleHandD1));
        assertFalse(singleHandD1.isValidPlay(singleHand2));
        assertFalse(singleHand13.isValidPlay(singleHand2));
        assertTrue(singleHand2.isValidPlay(singleHandD1));
    }

    @Test
    public void testIsValidPlaySingleCardThisIsAceTwo() {
        Hand singleHandD1 = new Hand();
        singleHandD1.addCard(D1);
        Hand singleHand2 = new Hand();
        singleHand2.addCard(S2);
        Hand singleHand13 = new Hand();
        singleHand13.addCard(D13);
        assertTrue(singleHandD1.isValidPlay(singleHand13));
        assertTrue(singleHand2.isValidPlay(singleHandD1));
        assertTrue(singleHand2.isValidPlay(singleHand13));
        assertFalse(singleHandD1.isValidPlay(singleHand2));
    }

    @Test
    public void testIsValidPlaySingleCardSameAndNonAceTwoRank() {
        Hand singleHand12 = new Hand();
        singleHand12.addCard(H12);
        Hand singleHand13 = new Hand();
        singleHand13.addCard(D13);
        Hand singleHandD1 = new Hand();
        singleHandD1.addCard(D1);
        Hand singleHandH1 = new Hand();
        singleHandH1.addCard(H1);
        assertTrue(singleHandH1.isValidPlay(singleHandD1));
        assertTrue(singleHand13.isValidPlay(singleHand12));
        assertFalse(singleHandD1.isValidPlay(singleHandH1));
        assertFalse(singleHand12.isValidPlay(singleHand13));
    }

    @Test
    public void testIsValidPlayPairOtherIsAceTwo() {
        Hand pairHand13 = new Hand(new ArrayList<>(Arrays.asList(D13, H13)));
        Hand pairHand1 = new Hand(new ArrayList<>(Arrays.asList(D1, H1)));
        Hand pairHand2 = new Hand(new ArrayList<>(Arrays.asList(S2, H2)));
        assertFalse(pairHand13.isValidPlay(pairHand1));
        assertFalse(pairHand1.isValidPlay(pairHand2));
        assertFalse(pairHand13.isValidPlay(pairHand2));
        assertTrue(pairHand2.isValidPlay(pairHand1));
    }

    @Test
    public void testIsValidPlayPairThisIsAceTwo() {
        Hand pairHand13 = new Hand(new ArrayList<>(Arrays.asList(D13, H13)));
        Hand pairHand1 = new Hand(new ArrayList<>(Arrays.asList(D1, H1)));
        Hand pairHand2 = new Hand(new ArrayList<>(Arrays.asList(S2, H2)));
        assertTrue(pairHand1.isValidPlay(pairHand13));
        assertTrue(pairHand2.isValidPlay(pairHand1));
        assertTrue(pairHand2.isValidPlay(pairHand13));
        assertFalse(pairHand1.isValidPlay(pairHand2));
    }

    @Test
    public void testIsValidPlayPairSameAndNonAceTwoRank() {
        Hand pairHand13 = new Hand(new ArrayList<>(Arrays.asList(D13, H13)));
        Hand pairHand1 = new Hand(new ArrayList<>(Arrays.asList(D1, H1)));
        Hand pairHand1b = new Hand(new ArrayList<>(Arrays.asList(S1, C1)));
        Hand pairHand12 = new Hand(new ArrayList<>(Arrays.asList(S12, H12)));
        assertTrue(pairHand1b.isValidPlay(pairHand1));
        assertTrue(pairHand13.isValidPlay(pairHand12));
        assertFalse(pairHand1.isValidPlay(pairHand1b));
        assertFalse(pairHand12.isValidPlay(pairHand13));
    }

    @Test
    public void testIsValidPlayThreeOfAKindOtherIsAceTwo() {
        Hand threeHand13 = new Hand(new ArrayList<>(Arrays.asList(D13, H13, S13)));
        Hand threeHand1 = new Hand(new ArrayList<>(Arrays.asList(D1, H1, S1)));
        Hand threeHand2 = new Hand(new ArrayList<>(Arrays.asList(C2, H2, S2)));
        assertFalse(threeHand13.isValidPlay(threeHand1));
        assertFalse(threeHand1.isValidPlay(threeHand2));
        assertFalse(threeHand13.isValidPlay(threeHand2));
        assertTrue(threeHand2.isValidPlay(threeHand1));
    }

    @Test
    public void testIsValidPlayThreeOfAKindThisIsAceTwo() {
        Hand threeHand13 = new Hand(new ArrayList<>(Arrays.asList(D13, H13, S13)));
        Hand threeHand1 = new Hand(new ArrayList<>(Arrays.asList(D1, H1, S1)));
        Hand threeHand2 = new Hand(new ArrayList<>(Arrays.asList(C2, H2, S2)));
        assertTrue(threeHand1.isValidPlay(threeHand13));
        assertTrue(threeHand2.isValidPlay(threeHand1));
        assertTrue(threeHand2.isValidPlay(threeHand13));
        assertFalse(threeHand1.isValidPlay(threeHand2));
    }

    @Test
    public void testIsValidPlayThreeOfAKindNonAceTwoRank() {
        Hand threeHand13 = new Hand(new ArrayList<>(Arrays.asList(D13, H13, S13)));
        Hand threeHand12 = new Hand(new ArrayList<>(Arrays.asList(C12, H12, S12)));
        assertTrue(threeHand13.isValidPlay(threeHand12));
        assertFalse(threeHand12.isValidPlay(threeHand13));
    }

    @Test
    public void testIsValidPlayFiveCardHandDifferentTypes() {
        Hand straight1 = new Hand(new ArrayList<>(Arrays.asList(H9, D10, H11, H12, D13)));
        Hand flush1 = new Hand(new ArrayList<>(Arrays.asList(H10, H13, H11, H9, H8)));
        Hand fullHouse1 = new Hand(new ArrayList<>(Arrays.asList(D10, C13, D13, H10, H13)));
        Hand straightFlush1 = new Hand(new ArrayList<>(Arrays.asList(H8, H9, H10, H11, H12)));
        assertTrue(flush1.isValidPlay(straight1));
        assertTrue(straightFlush1.isValidPlay(straight1));
        assertFalse(straight1.isValidPlay(fullHouse1));
    }

    @Test
    public void testIsValidPlayStraightHand() {
        Hand straight1 = new Hand(new ArrayList<>(Arrays.asList(H9, D10, H11, H12, D13)));
        Hand straight2 = new Hand(new ArrayList<>(Arrays.asList(H9, D10, H11, H12, H13)));
        Hand straight3 = new Hand(new ArrayList<>(Arrays.asList(D10, H11, H12, H13, D1)));
        Hand straight4 = new Hand(new ArrayList<>(Arrays.asList(H8, H9, D10, H11, H12)));
        assertTrue(straight2.isValidPlay(straight1));
        assertTrue(straight3.isValidPlay(straight1));
        assertTrue(straight1.isValidPlay(straight4));
        assertFalse(straight1.isValidPlay(straight2));
        assertFalse(straight1.isValidPlay(straight3));
        assertFalse(straight4.isValidPlay(straight1));
    }

    @Test
    public void testIsValidPlayFlushHand() {
        Hand flush1 = new Hand(new ArrayList<>(Arrays.asList(H10, H13, H11, H9, H8)));
        Hand flush2 = new Hand(new ArrayList<>(Arrays.asList(H10, H13, H11, H9, H1)));
        Hand flush3 = new Hand(new ArrayList<>(Arrays.asList(S10, S13, S11, S9, S8)));
        assertTrue(flush2.isValidPlay(flush1));
        assertTrue(flush3.isValidPlay(flush1));
        assertFalse(flush1.isValidPlay(flush2));
        assertFalse(flush1.isValidPlay(flush3));
    }

    @Test
    public void testIsValidPlayFullHouseHand() {
        Hand fullHouse1 = new Hand(new ArrayList<>(Arrays.asList(D10, C13, D13, H10, H13)));
        Hand fullHouse2 = new Hand(new ArrayList<>(Arrays.asList(D10, C1, D1, H10, H1)));
        Hand fullHouse3 = new Hand(new ArrayList<>(Arrays.asList(D10, C12, S12, H10, H12)));
        assertTrue(fullHouse2.isValidPlay(fullHouse1));
        assertTrue(fullHouse1.isValidPlay(fullHouse3));
        assertFalse(fullHouse1.isValidPlay(fullHouse2));
        assertFalse(fullHouse3.isValidPlay(fullHouse1));
    }

    @Test
    public void testIsValidPlayFourOfAKindHand() {
        Hand four1 = new Hand(new ArrayList<>(Arrays.asList(H1, D13, C13, H13, S13)));
        Hand four2 = new Hand(new ArrayList<>(Arrays.asList(D1, C1, D10, H1, S1)));
        Hand four3 = new Hand(new ArrayList<>(Arrays.asList(D12, C12, D10, H12, S12)));
        assertTrue(four2.isValidPlay(four1));
        assertTrue(four1.isValidPlay(four3));
        assertFalse(four1.isValidPlay(four2));
        assertFalse(four3.isValidPlay(four1));
    }

    @Test
    public void testIsValidPlayStraightFlushHand() {
        Hand straightFlush1 = new Hand(new ArrayList<>(Arrays.asList(H8, H9, H10, H11, H12)));
        Hand straightFlush2 = new Hand(new ArrayList<>(Arrays.asList(H9, H10, H11, H12, H13)));
        assertTrue(straightFlush2.isValidPlay(straightFlush1));
        assertFalse(straightFlush1.isValidPlay(straightFlush2));
    }

    @Test
    public void testIsValidPlayRoyalStraightFlushHand() {
        Hand royal1 = new Hand(new ArrayList<>(Arrays.asList(H10, H11, H12, H13, H1)));
        Hand royal2 = new Hand(new ArrayList<>(Arrays.asList(S10, S11, S12, S13, S1)));
        assertTrue(royal2.isValidPlay(royal1));
        assertFalse(royal1.isValidPlay(royal2));
    }

    @Test
    public void testIsValidTypeHandSingleAndPair() {
        Hand single = new Hand();
        single.addCard(D3);
        assertTrue(single.isValidTypeHand());
        Hand pair = new Hand(new ArrayList<>(Arrays.asList(H10, D10)));
        assertTrue(pair.isValidTypeHand());
        Hand falsePair = new Hand(new ArrayList<>(Arrays.asList(D3, H10)));
        assertFalse(falsePair.isValidTypeHand());
    }

    @Test
    public void testIsValidTypeHandThreeCards() {
        Hand threeOfAKind = new Hand(new ArrayList<>(Arrays.asList(D3, C3, H3)));
        assertTrue(threeOfAKind.isValidTypeHand());
        Hand falseThreeOfAKind = new Hand(new ArrayList<>(Arrays.asList(D3, C3, H12)));
        assertFalse(falseThreeOfAKind.isValidTypeHand());
        Hand falseThreeOfAKindV2 = new Hand(new ArrayList<>(Arrays.asList(H12, D3, C3)));
        assertFalse(falseThreeOfAKindV2.isValidTypeHand());
    }

    @Test
    public void testIsValidTypeHandStraightHand() {
        Hand straight = new Hand(new ArrayList<>(Arrays.asList(H9, D10, H11, H12, H13)));
        assertTrue(straight.isValidTypeHand());
        Hand falseStraight = new Hand(new ArrayList<>(Arrays.asList(D10, C3, H9, H10, H3)));
        assertFalse(falseStraight.isValidTypeHand());
    }

    @Test
    public void testIsValidTypeHandFullHouseHandTrue() {
        Hand fullHouse1 = new Hand(new ArrayList<>(Arrays.asList(C3, D10, D3, H10, H3)));
        assertTrue(fullHouse1.isValidTypeHand());
        Hand fullHouse2 = new Hand(new ArrayList<>(Arrays.asList(D10, C3, D3, H10, H3)));
        assertTrue(fullHouse2.isValidTypeHand());
    }

    @Test
    public void testIsValidTypeHandFullHouseHandFalse() {
        Hand falseFullHouse1 = new Hand(new ArrayList<>(Arrays.asList(D10, C3, H9, H10, H3)));
        assertFalse(falseFullHouse1.isValidTypeHand());
        Hand falseFullHouse2 = new Hand(new ArrayList<>(Arrays.asList(H2, C3, D10, D3, H3)));
        assertFalse(falseFullHouse2.isValidTypeHand());
        Hand falseFullHouse3 = new Hand(new ArrayList<>(Arrays.asList(C3, D10, D3, H2, H3)));
        assertFalse(falseFullHouse3.isValidTypeHand());
        Hand falseFullHouse4 = new Hand(new ArrayList<>(Arrays.asList(C3, D10, D3, H10, S10, H3)));
        assertFalse(falseFullHouse4.isValidFullHouseHand());
    }

    @Test
    public void testIsValidTypeHandFourOfAKindHand() {
        Hand fourOfAKind = new Hand(new ArrayList<>(Arrays.asList(H1, D3, C3, H3, S3)));
        assertTrue(fourOfAKind.isValidTypeHand());
        Hand fourOfAKindV2 = new Hand(new ArrayList<>(Arrays.asList(D3, C3, D10, H3, S3)));
        assertTrue(fourOfAKindV2.isValidTypeHand());
        Hand falseFourOfAKind = new Hand(new ArrayList<>(Arrays.asList(H1, H9, C3, H3, S3)));
        assertFalse(falseFourOfAKind.isValidTypeHand());
    }

    @Test
    public void testIsValidTypeHandStraightFlushHand() {
        Hand straightFlush = new Hand(new ArrayList<>(Arrays.asList(H9, H10, H11, H12, H13)));
        assertTrue(straightFlush.isValidTypeHand());
        Hand falseStraightFlush = new Hand(new ArrayList<>(Arrays.asList(C3, H10, H11, H12, H13)));
        assertFalse(falseStraightFlush.isValidTypeHand());
    }

    @Test
    public void testIsValidTypeHandRoyalStraightFlushHand() {
        Hand royalStraightFlush = new Hand(new ArrayList<>(Arrays.asList(H10, H11, H12, H13, H1)));
        assertTrue(royalStraightFlush.isValidTypeHand());
        Hand falseRoyalStraightFlush1 = new Hand(new ArrayList<>(Arrays.asList(H9, H10, H11, H12, H13)));
        assertFalse(falseRoyalStraightFlush1.isValidRoyalStraightFlushHand());
        Hand falseRoyalStraightFlush2 = new Hand(new ArrayList<>(Arrays.asList(H10, H11, H12, H13, D3)));
        assertFalse(falseRoyalStraightFlush2.isValidTypeHand());
        Hand falseRoyalStraightFlush3 = new Hand(new ArrayList<>(Arrays.asList(H10, H3, H1, H12, H13)));
        assertFalse(falseRoyalStraightFlush3.isValidRoyalStraightFlushHand());
    }
}
