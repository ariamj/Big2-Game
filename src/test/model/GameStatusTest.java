package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameStatusTest {
    private GameStatus gs;

    @BeforeEach
    public void runBefore() {
        gs = new GameStatus("Game number 1");
    }

    @Test
    public void testSetDrawer() {
        ChipsDrawer drawer1 = new ChipsDrawer(18, 12, 5, 1);
        ChipsDrawer drawer2 = new ChipsDrawer(22, 8, 5, 1);
        gs.setDrawer(drawer1, 1);
        gs.setDrawer(drawer2, 2);
        ChipsDrawer player1Drawer = gs.getDrawer(GameStatus.PLAYER1);
        ChipsDrawer player2Drawer = gs.getDrawer(GameStatus.PLAYER2);
        assertEquals((18 + 12 + 5 + 1), player1Drawer.getDrawer().size());
        assertEquals((22 + 8 + 5 + 1), player2Drawer.getDrawer().size());
        assertEquals(drawer1, player1Drawer);
        assertEquals(drawer2, player2Drawer);
    }

    @Test
    public void testGetName() {
        assertEquals("Game number 1", gs.getName());
    }

    @Test
    public void testSetPlayerTurn() {
        assertEquals(1, gs.getPlayerTurn());
        gs.setPlayerTurn(2);
        assertEquals(2, gs.getPlayerTurn());
    }

    @Test
    public void testSetCardListTable() {
        Card d3 = new Card(3, "diamond");
        Card s3 = new Card(3, "spade");
        ListOfCards cards1 = new PlayerCards(new ArrayList<>(Arrays.asList(d3, s3)));
        gs.setCardList(cards1, GameStatus.TABLE);
        List<Card> cardList = gs.getCardList(GameStatus.TABLE);
        assertEquals(2, cardList.size());
        assertTrue(cardList.contains(d3));
        assertTrue(cardList.contains(s3));
    }

    @Test
    public void testSetCardListPlayerOne() {
        Card d4 = new Card(4, "diamond");
        Card s4 = new Card(4, "spade");
        ListOfCards cards2 = new PlayerCards(new ArrayList<>(Arrays.asList(d4, s4)));
        gs.setCardList(cards2, GameStatus.PLAYER1);
        List<Card> cardList = gs.getCardList(GameStatus.PLAYER1);
        assertEquals(2, cardList.size());
        assertTrue(cardList.contains(d4));
        assertTrue(cardList.contains(s4));
    }

    @Test
    public void testSetCardListPlayerTwo() {
        Card d5 = new Card(5, "diamond");
        Card s5 = new Card(5, "spade");
        ListOfCards cards0 = new PlayerCards(new ArrayList<>(Arrays.asList(d5, s5)));
        gs.setCardList(cards0, GameStatus.PLAYER2);
        List<Card> cardList = gs.getCardList(GameStatus.PLAYER2);
        assertEquals(2, cardList.size());
        assertTrue(cardList.contains(d5));
        assertTrue(cardList.contains(s5));
    }

    @Test
    public void testAddCardToCardList() {
        Card d6 = new Card(6, "diamond");
        Card s6 = new Card(6, "spade");
        gs.addCardToCardList(d6, GameStatus.PLAYER1);
        gs.addCardToCardList(s6, GameStatus.PLAYER2);
        List<Card> cardList1 = gs.getCardList(GameStatus.PLAYER1);
        List<Card> cardList2 = gs.getCardList(GameStatus.PLAYER2);
        assertEquals(1, cardList1.size());
        assertEquals(1, cardList2.size());
        assertTrue(cardList1.contains(d6));
        assertTrue(cardList2.contains(s6));
    }

    @Test
    public void testRemoveCardsFromPlayer() {
        Card d4 = new Card(4, "diamond");
        Card s4 = new Card(4, "spade");
        ListOfCards cards2 = new PlayerCards(new ArrayList<>(Arrays.asList(d4, s4)));
        gs.setCardList(cards2, GameStatus.PLAYER1);
        ListOfCards removeList = new Hand(new ArrayList<>(Arrays.asList(s4)));
        gs.removeCardsFromPlayer(removeList, GameStatus.PLAYER1);
        List<Card> cardList1 = gs.getCardList(GameStatus.PLAYER1);
        assertEquals(1, cardList1.size());
        assertTrue(cardList1.contains(d4));
        assertFalse(cardList1.contains(s4));
    }

    @Test
    public void testToJson() {
        Card d3 = new Card(3, "diamond");
        gs.addCardToCardList(d3, GameStatus.TABLE);
        JSONObject jsonObject = gs.toJson();
        assertEquals("Game number 1", jsonObject.get("name"));
        assertEquals(0, jsonObject.getJSONArray("player 1 cards").length());
        assertEquals(1, jsonObject.getJSONArray("player 1 chips").length());
        assertEquals(0, jsonObject.getJSONArray("player 2 cards").length());
        assertEquals(1, jsonObject.getJSONArray("player 2 chips").length());
        assertEquals(1, jsonObject.getJSONArray("table pile").length());
        assertEquals(GameStatus.PLAYER1, jsonObject.getInt("player turn"));
    }
}
