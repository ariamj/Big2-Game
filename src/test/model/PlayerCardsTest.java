package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerCardsTest {
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

    private PlayerCards playerCards;

    @BeforeEach
    public void runBefore() {
        List<Card> initialCards = new ArrayList<>();
        initialCards.addAll(new ArrayList<>(Arrays.asList(D3, C3, H3, S3, H1, H13, H12, H11, H10, H9, D10)));
        ListOfCards cards = new Hand(initialCards);
        playerCards = new PlayerCards(cards);
    }

    @Test
    public void testPlayHandOneCard() {
        Hand hand = new Hand(new ArrayList<>(Arrays.asList(D3)));
        Hand played = playerCards.playHand(hand);
        assertEquals(1, played.getSize());
        assertEquals(3, played.getHand().get(0).getRank());
        assertEquals("diamond", played.getHand().get(0).getSuit());
        assertEquals(10, playerCards.getSize());
    }

    @Test
    public void testPlayHandFiveCards() {
        Hand hand = new Hand(new ArrayList<>(Arrays.asList(D3, S3, H3, C3, D10)));
        Hand played = playerCards.playHand(hand);
        assertEquals(5, played.getSize());
        assertEquals(3, played.getHand().get(0).getRank());
        assertEquals("diamond", played.getHand().get(0).getSuit());
        assertEquals(3, played.getHand().get(1).getRank());
        assertEquals("spade", played.getHand().get(1).getSuit());
        assertEquals(3, played.getHand().get(2).getRank());
        assertEquals("heart", played.getHand().get(2).getSuit());
        assertEquals(3, played.getHand().get(3).getRank());
        assertEquals("clubs", played.getHand().get(3).getSuit());
        assertEquals(10, played.getHand().get(4).getRank());
        assertEquals("diamond", played.getHand().get(4).getSuit());
        assertEquals(6, playerCards.getSize());
    }

    @Test
    public void testPlayHandCantPlayHand() {
        Hand hand = new Hand(new ArrayList<>(Arrays.asList(D10,D3)));
        Hand emptyHand = new Hand();
        assertEquals(emptyHand.getListOfCards(), playerCards.playHand(hand).getListOfCards());
    }

    @Test
    public void testToString() {
        String playerCardsString = "[3D, 3C, 3H, 3S, AH, KH, QH, JH, 10H, 9H, 10D]";
        assertEquals(playerCardsString, playerCards.toString());
    }
}