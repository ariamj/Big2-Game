package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardTest {
    private Card card;
    private Card jackCard;
    private Card queenCard;
    private Card kingCard;
    private Card aceCard;

    @BeforeEach
    public void runBefore() {
        card = new Card(3, "Diamond");
        jackCard = new Card(11, "Clubs");
        queenCard = new Card(12, "Heart");
        kingCard = new Card(13, "Spade");
        aceCard = new Card(1, "Clubs");
    }

    @Test
    public void testGetRank() {
        assertEquals(3, card.getRank());
    }

    @Test
    public void testGetSuit() {
        assertEquals("diamond", card.getSuit());
    }

    @Test
    public void testToString() {
        assertEquals("3D", card.toString());
        assertEquals("JC", jackCard.toString());
        assertEquals("QH", queenCard.toString());
        assertEquals("KS", kingCard.toString());
        assertEquals("AC", aceCard.toString());
    }
}
