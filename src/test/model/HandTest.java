package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HandTest {
    private Card d3 = new Card(3, "diamond");
    private Card c3 = new Card(3, "clubs");
    private Card h3 = new Card(3, "heart");
    private Card s3 = new Card(3, "spade");
    private Card h1 = new Card(1, "heart");
    private Card h13 = new Card(13, "heart");
    private Card h12 = new Card(12, "heart");
    private Card h11 = new Card(11, "heart");
    private Card h10 = new Card(10, "heart");
    private Card d10 = new Card(10, "diamond");
    private Hand hand;

    @BeforeEach
    public void runBefore() {
        List<Card> hand1 = new ArrayList<>();
        hand1.addAll(new ArrayList<>(Arrays.asList(d3,c3,h3,s3,h1,h13,h12,h11,h10,d10)));
        hand = new Hand(hand1);
    }

    @Test
    public void testIsValidTypeHandSingleAndPairAndThreeCards() {
        Hand single = new Hand(new ArrayList<>(Arrays.asList(d3)));
        assertTrue(single.isValidTypeHand());
        Hand pair = new Hand(new ArrayList<>(Arrays.asList(h10, d10)));
        assertTrue(pair.isValidTypeHand());
        Hand falsePair = new Hand(new ArrayList<>(Arrays.asList(d3,h10)));
        assertFalse(falsePair.isValidTypeHand());
        Hand threeOfAKind = new Hand(new ArrayList<>(Arrays.asList(d3,c3,h3)));
        assertTrue(threeOfAKind.isValidTypeHand());
        Hand falseThreeOfAKind = new Hand(new ArrayList<>(Arrays.asList(d3,c3,h12)));
        assertFalse(falseThreeOfAKind.isValidTypeHand());
    }

    @Test
    public void testIsValidTypeHandFiveCards() {
        Hand fullHouse = new Hand(new ArrayList<>(Arrays.asList(d10,c3,d3,h10,h3)));
        assertTrue(fullHouse.isValidTypeHand());
        Hand fourOfAKind = new Hand(new ArrayList<>(Arrays.asList(h1,d3,c3,h3,s3)));
        assertTrue(fourOfAKind.isValidTypeHand());
        Hand royalStraightFlush = new Hand(new ArrayList<>(Arrays.asList(h10,h11,h12,h13,h1)));
        assertTrue(royalStraightFlush.isValidTypeHand());
    }

    @Test
    public void testIsValidPlay() {

    }
}
