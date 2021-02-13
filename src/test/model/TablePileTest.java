package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TablePileTest {
    private TablePile pile;

    @BeforeEach
    public void runBefore() {
        pile = new TablePile();
    }

    @Test
    public void testTopHandInPileOneCard() {
        Card card = new Card(3, "diamond");
        Hand hand = new Hand(new ArrayList<>(Arrays.asList(card)));
        pile.playHandInPile(hand);
        List<Card> cardsInPile = new ArrayList<>();
        cardsInPile.add(card);
        assertEquals(1, cardsInPile.size(), pile.getSize());

        //tests for is correct card in pile....
    }

    @Test
    public void testTopHandInPileTwoCards() {
        Hand hand = new Hand(new ArrayList<>(Arrays.asList(new Card(3, "diamond"),
                new Card(3,"spade"))));
        pile.playHandInPile(hand);
        assertEquals(2, pile.getSize());

        //tests for is correct card in pile....
    }
}
