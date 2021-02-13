package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerCardsTest {
    private PlayerCards playerCards;

    @BeforeEach
    public void runBefore() {
        ListOfCards cards = new Hand(new ArrayList<>(Arrays.asList(new Card(3, "diamond"),
                new Card(3, "spade"))));
        playerCards = new PlayerCards(cards);
    }

    @Test
    public void testPlayHandOneCard() {
        Hand hand = new Hand(new ArrayList<>(Arrays.asList(new Card(3, "diamond"))));
        Hand played = playerCards.playHand(hand);
        assertEquals(1, played.getSize());
        assertEquals(3, played.getHand().get(0).getRank());
        assertEquals("diamond", played.getHand().get(0).getSuit());

        //not sure....removing from player cards part....
        assertEquals(1, playerCards.getSize());
    }

    @Test
    public void testPlayHandTwoCards() {
        Hand hand = new Hand(new ArrayList<>(Arrays.asList(new Card(3, "diamond"),
                new Card(3, "spade"))));
        Hand played = playerCards.playHand(hand);
        assertEquals(2, played.getSize());
        assertEquals(3, played.getHand().get(0).getRank());
        assertEquals("diamond", played.getHand().get(0).getSuit());
    }
}