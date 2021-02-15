package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ListOfCardsTest {
    private ListOfCards cardList;
    private ListOfCards emptyCardList;

    @BeforeEach
    public void runBefore() {
        List<Card> initialCards = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            initialCards.add(new Card(i, "diamond"));
        }
        cardList = new Hand(initialCards);
        emptyCardList = new Hand();
    }

    @Test
    public void testConstructors() {
        assertEquals(0, emptyCardList.getSize());
        assertEquals(5, cardList.getSize());
    }

    @Test
    public void testHighestCard() {
        Card highestCard = new Card(2, "diamond");
        Card firstCard = new Card(13, "diamond");
        Card secondCard = new Card(1, "diamond");
        Card thirdCard = new Card(3, "diamond");
        cardList.setListOfCards(new ArrayList<>(Arrays.asList(thirdCard, firstCard, secondCard)));
        cardList.addCard(highestCard);
        assertEquals(highestCard, cardList.highestCard());
    }

    @Test
    public void testAddCard() {
        Card sixClubs = new Card(6, "clubs");
        cardList.addCard(sixClubs);
        assertEquals(6, cardList.getSize());
        assertTrue(cardList.contains(sixClubs));
    }

    @Test
    public void testRemoveCard() {
        cardList.removeCard(0);
        assertEquals(4, cardList.getSize());
        assertFalse(cardList.contains(new Card(1, "diamond")));
    }

    @Test
    public void testToString() {
        String handString = "[AD, 2D, 3D, 4D, 5D]";
        assertEquals(handString, cardList.toString());
    }
}
