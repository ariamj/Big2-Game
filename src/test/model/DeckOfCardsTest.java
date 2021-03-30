package model;

import model.exceptions.TooFewCardsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DeckOfCardsTest {

    private DeckOfCards deck;

    @BeforeEach
    public void runBefore() {
        deck = new DeckOfCards();
    }

    @Test
    public void testConstructor() {
        assertEquals(52, deck.getSize());
        int numDiamonds = 0;
        int numClubs = 0;
        int numHearts = 0;
        int numSpades = 0;
        for (Card card : deck.getListOfCards()) {
            if (card.getSuit().equals("diamond")) {
                numDiamonds++;
            } else if (card.getSuit().equals("clubs")) {
                numClubs++;
            } else if (card.getSuit().equals("heart")) {
                numHearts++;
            } else if (card.getSuit().equals("spade")) {
                numSpades++;
            }
        }
        assertEquals(13, numDiamonds);
        assertEquals(13, numClubs);
        assertEquals(13, numHearts);
        assertEquals(13, numSpades);
    }

    @Test
    public void testDealCards13Cards() {
        List<Card> cardsDealt = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            cardsDealt.add(deck.getCard(i));
        }
        Hand dealtHand1 = new Hand(cardsDealt);
        try {
            List<Card> dealtHandTest = deck.dealCards("13 cards");
            assertEquals(13, dealtHandTest.size());
            assertEquals(dealtHand1.getListOfCards(), dealtHandTest);
        } catch (TooFewCardsException e) {
            fail("TooFewCardsException not expected");
        }
    }

    @Test
    public void testDealCardsLessThan26CardsInDeck() {
        for (int i = 0; i < 27; i++) {
            deck.removeCard(0);
        }
        try {
            deck.dealCards("13 cards");
            fail("TooFewCardsException expected");
        } catch (TooFewCardsException e) {
            assertEquals(25, deck.getSize());
        }
    }

    @Test
    public void testDealCardsHalfDeck() {
        List<Card> cardsDealt = new ArrayList<>();
        for (int i = 0; i < deck.getSize() / 2; i++) {
            cardsDealt.add(deck.getCard(i));
        }
        Hand dealtHand1 = new Hand(cardsDealt);
        try {
            List<Card> dealtHandTest = deck.dealCards("half deck");
            assertEquals(26, dealtHandTest.size());
            assertEquals(dealtHand1.getListOfCards(), dealtHandTest);
        } catch (TooFewCardsException e) {
            fail("TooFewCardsException not expected");
        }
    }

    @Test
    public void testShuffleDeck() {
        DeckOfCards originalDeck = new DeckOfCards();
        deck.shuffleDeck();
        boolean allCardsInSamePositions = true;
        int index = 0;
        for (Card card : deck.getListOfCards()) {
            allCardsInSamePositions = allCardsInSamePositions && card.equalCards(originalDeck.getCard(index));
            index++;
        }
        assertFalse(allCardsInSamePositions);
    }
}
