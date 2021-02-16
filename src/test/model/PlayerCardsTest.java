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
        playerCards = new PlayerCards(initialCards);
    }

    @Test
    public void testPlayHandOneCard() {
        Hand hand = new Hand(new ArrayList<>(Arrays.asList(D3)));
        Hand played = playerCards.playHand(hand);
        assertEquals(1, played.getSize());
        assertTrue(played.getCard(0).equalCards(new Card(3, "diamond")));
        assertEquals(10, playerCards.getSize());
    }

    @Test
    public void testPlayHandFiveCards() {
        Hand hand = new Hand(new ArrayList<>(Arrays.asList(D3, S3, H3, C3, D10)));
        Hand played = playerCards.playHand(hand);
        assertEquals(5, played.getSize());
        assertTrue(played.getCard(0).equalCards(new Card(3, "diamond")));
        assertTrue(played.getCard(1).equalCards(new Card(3, "spade")));
        assertTrue(played.getCard(2).equalCards(new Card(3, "heart")));
        assertTrue(played.getCard(3).equalCards(new Card(3, "clubs")));
        assertTrue(played.getCard(4).equalCards(new Card(10, "diamond")));
        assertEquals(6, playerCards.getSize());
    }

    @Test
    public void testToString() {
        String playerCardsString = "[3D, 3C, 3H, 3S, AH, KH, QH, JH, 10H, 9H, 10D]";
        assertEquals(playerCardsString, playerCards.toString());
    }

    @Test
    public void testGetCardsList() {
        List<Card> cardList = new ArrayList<>(Arrays.asList(D3, C3, H3, S3, H1, H13, H12, H11, H10, H9, D10));
        assertEquals(cardList, playerCards.getCardsList());
    }
}