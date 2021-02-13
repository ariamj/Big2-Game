package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeckOfCardsTest {

    private DeckOfCards deck;

    @BeforeEach
    public void runBefore() {
        deck = new DeckOfCards();
    }

    @Test
    public void testConstructor() {
        assertEquals(52, deck.getSize());
    }

//    @Test
//    public void testRemoveCard() {
//        Card card = deck.removeCard();
//        assertEquals(51, deck.getSize());
//        assertEquals(1, card.getRank());
//        assertEquals("diamond", card.getSuit());
//    }
}
