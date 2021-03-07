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
        Card d2 = new Card(2, "diamond");
        Card d13 = new Card(13, "diamond");
        Card d1 = new Card(1, "diamond");
        Card d3 = new Card(3, "diamond");
        Card d4 = new Card(4, "diamond");
        cardList.setListOfCards(new ArrayList<>(Arrays.asList(d3, d13, d4, d1, d2)));
        assertEquals(d2, cardList.highestCard());
        cardList.setListOfCards(new ArrayList<>(Arrays.asList(d3, d4, d1, d13)));
        assertEquals(d1, cardList.highestCard());
        cardList.setListOfCards(new ArrayList<>(Arrays.asList(d3, d4, d1)));
        assertEquals(d1, cardList.highestCard());
        cardList.setListOfCards(new ArrayList<>(Arrays.asList(d2, d3, d13, d1)));
        assertEquals(d2, cardList.highestCard());
    }

    @Test
    public void testThisIsHigherCardBoth3To13() {
        assertTrue(cardList.thisIsHigherCard(13, 3));
        assertFalse(cardList.thisIsHigherCard(3, 13));
        assertFalse(cardList.thisIsHigherCard(3, 3));
    }

    @Test
    public void testThisIsHigherCardOnlySecondAceTwo() {
        assertFalse(cardList.thisIsHigherCard(3, 1));
        assertFalse(cardList.thisIsHigherCard(3, 2));
        assertFalse(cardList.thisIsHigherCard(3, 14));
    }

    @Test
    public void testThisIsHigherCardFirstAceSecond3To13() {
        assertTrue(cardList.thisIsHigherCard(1, 13));
        assertTrue(cardList.thisIsHigherCard(1, 3));
        assertFalse(cardList.thisIsHigherCard(1, 14));
    }

    @Test
    public void testThisIsHigherCardFirstAceSecondTwo() {
        assertFalse(cardList.thisIsHigherCard(1, 2));
    }

    @Test
    public void testThisIsHigherCardSecondTwo() {
        assertTrue(cardList.thisIsHigherCard(2, 13));
        assertTrue(cardList.thisIsHigherCard(2, 1));
        assertFalse(cardList.thisIsHigherCard(2, 2));
        assertFalse(cardList.thisIsHigherCard(2, 14));
        assertFalse(cardList.thisIsHigherCard(14, 2));
        assertFalse(cardList.thisIsHigherCard(14, 1));
        assertFalse(cardList.thisIsHigherCard(14, 3));
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
