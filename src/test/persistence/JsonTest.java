package persistence;

import model.GameStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {

    protected void checkCardLists(int numCards1, int numCards2, int numTableCards, GameStatus gs) {
        assertEquals(numCards1, gs.getCardList(1).size());
        assertEquals(numCards2, gs.getCardList(2).size());
        assertEquals(numTableCards, gs.getCardList(0).size());
    }

    protected void checkDrawer(int numWhite, int numBlue, int numRed, int numGold, int playerNumber, GameStatus gs) {
        assertEquals(numWhite, gs.getDrawer(playerNumber).getNumWhiteChips());
        assertEquals(numBlue, gs.getDrawer(playerNumber).getNumBlueChips());
        assertEquals(numRed, gs.getDrawer(playerNumber).getNumRedChips());
        assertEquals(numGold, gs.getDrawer(playerNumber).getNumGoldChips());
    }
}
