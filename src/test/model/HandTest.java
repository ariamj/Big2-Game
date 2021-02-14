package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HandTest {
    private final Card D3 = new Card(3, "diamond");
    private final Card C3 = new Card(3, "clubs");
    private final Card H3 = new Card(3, "heart");
    private final Card S3 = new Card(3, "spade");
    private final Card H1 = new Card(1, "heart");
    private final Card H13 = new Card(13, "heart");
    private final Card H12 = new Card(12, "heart");
    private final Card H11 = new Card(11, "heart");
    private final Card H10 = new Card(10, "heart");
    private final Card H9 = new Card(9, "heart");
    private final Card D10 = new Card(10, "diamond");

    private Hand hand;

    @BeforeEach
    public void runBefore() {
        List<Card> hand1 = new ArrayList<>();
        hand1.addAll(new ArrayList<>(Arrays.asList(D3, C3, H3, S3, H1, H13, H12, H11, H10, D10)));
        hand = new Hand(hand1);
    }

    @Test
    public void testIsValidPlay() {
        // NOT IMPLEMENTED!!!
        Hand otherHand = new Hand(new ArrayList<>(Arrays.asList(H9)));
        assertFalse(hand.isValidPlay(otherHand));
    }

    @Test
    public void testIsValidTypeHandSingleAndPairAndThreeCards() {
        Hand single = new Hand(new ArrayList<>(Arrays.asList(D3)));
        assertTrue(single.isValidTypeHand());
        Hand pair = new Hand(new ArrayList<>(Arrays.asList(H10, D10)));
        assertTrue(pair.isValidTypeHand());
        Hand falsePair = new Hand(new ArrayList<>(Arrays.asList(D3, H10)));
        assertFalse(falsePair.isValidTypeHand());
        Hand threeOfAKind = new Hand(new ArrayList<>(Arrays.asList(D3, C3, H3)));
        assertTrue(threeOfAKind.isValidTypeHand());
        Hand falseThreeOfAKind = new Hand(new ArrayList<>(Arrays.asList(D3, C3, H12)));
        assertFalse(falseThreeOfAKind.isValidTypeHand());
    }

    @Test
    public void testIsValidTypeHandFiveCards() {
        Hand flush = new Hand(new ArrayList<>(Arrays.asList(H9, H3, H11, H12, H13)));
        assertTrue(flush.isValidTypeHand());
        Hand falseFlush = new Hand(new ArrayList<>(Arrays.asList(D10, C3, H9, H10, H3)));
        assertFalse(falseFlush.isValidTypeHand());
    }

    @Test
    public void testIsValidTypeHandStraightHand() {
        Hand straight = new Hand(new ArrayList<>(Arrays.asList(H9, D10, H11, H12, H13)));
        assertTrue(straight.isValidTypeHand());
        Hand falseStraight = new Hand(new ArrayList<>(Arrays.asList(D10, C3, H9, H10, H3)));
        assertFalse(falseStraight.isValidTypeHand());
    }

    @Test
    public void testIsValidTypeHandFullHouseHand() {
        Hand fullHouse = new Hand(new ArrayList<>(Arrays.asList(D10, C3, D3, H10, H3)));
        assertTrue(fullHouse.isValidTypeHand());
        Hand falseFullHouse = new Hand(new ArrayList<>(Arrays.asList(D10, C3, H9, H10, H3)));
        assertFalse(falseFullHouse.isValidTypeHand());
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
    public void testIsValidTypeHandStriaghtFlushHand() {
        Hand straightFlush = new Hand(new ArrayList<>(Arrays.asList(H9, H10, H11, H12, H13)));
        assertTrue(straightFlush.isValidTypeHand());
        Hand falseStraightFlush = new Hand(new ArrayList<>(Arrays.asList(C3, H10, H11, H12, H13)));
        assertFalse(falseStraightFlush.isValidTypeHand());
    }

    @Test
    public void testIsValidTypeHandRoyalStriaghtFlushHand() {
        Hand royalStraightFlush = new Hand(new ArrayList<>(Arrays.asList(H10, H11, H12, H13, H1)));
        assertTrue(royalStraightFlush.isValidTypeHand());
        Hand falseRoyalStraightFlush = new Hand(new ArrayList<>(Arrays.asList(H10, H11, H12, H13, D3)));
        assertFalse(falseRoyalStraightFlush.isValidTypeHand());
    }

    @Test
    public void testToString() {
        Hand firstHand = new Hand(new ArrayList<>(Arrays.asList(D3, H9, H1, H13, C3, H11)));
        String firstHandString = "[3D, 9H, AH, KH, 3C, JH]";
        assertEquals(firstHandString, firstHand.toString());
    }
}
