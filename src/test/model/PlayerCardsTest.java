package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerCardsTest {
    private PlayerCards playerCards;

    @BeforeEach
    public void runBefore() {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(3, "diamond"));
        cards.add(new Card(3, "spade"));
        playerCards = new PlayerCards(cards);
    }

    @Test
    public void testPlayHandOneCard() {
        List<Card> hand = new ArrayList<>();
        hand.add(new Card(3, "diamond"));
        List<Card> played = playerCards.playHand(hand);
        assertEquals(1, played.size());
        assertEquals(3, played.get(0).getRank());
        assertEquals("diamond", played.get(0).getSuit());

        //not sure....removing from player cards part....
        assertEquals(0, playerCards.getSize());
    }

    @Test
    public void testPlayHandTwoCards() {
        List<Card> hand = new ArrayList<>();
        hand.add(new Card(3, "diamond"));
        hand.add(new Card(3, "spade"));
        List<Card> played = playerCards.playHand(hand);
        assertEquals(2, played.size());
        assertEquals(3, played.get(0).getRank());
        assertEquals("diamond", played.get(0).getSuit());
    }
}