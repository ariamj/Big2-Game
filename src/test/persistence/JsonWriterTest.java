package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest{

    @Test
    public void testWriterInvalidFile() {
        JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
        try {
            GameStatus gs = new GameStatus("Game number 1", 1);
            writer.open();
            fail("FileNotFoundException was expected");
        } catch (FileNotFoundException fe) {
            //pass
        }
    }

    @Test
    public void testWriterEmptyGameStatus() {
        JsonWriter writer = new JsonWriter("./data/testWriterEmptyGameStatus.json");
        try {
            GameStatus gs = new GameStatus("Game number 1", 1);
            writer.open();
            writer.write(gs);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyGameStatus.json");
            gs = reader.read();
            assertEquals("Game number 1", gs.getName());
            checkCardLists(0, 0, 0, gs);
            assertEquals(1, gs.getPlayerTurn());
        } catch (IOException e) {
            fail("IOException not expected");
        }
    }

    @Test
    public void testGeneralGameStatus() {
        JsonWriter writer = new JsonWriter("./data/testWriterGeneralGameStatus.json");
        GameStatus gs = initializeGeneralGameStatus();
        try {
            writer.open();
            writer.write(gs);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralGameStatus.json");
            gs = reader.read();
            assertEquals("Game number 1", gs.getName());
            checkCardLists(2, 2, 2, gs);
            assertEquals(2, gs.getPlayerTurn());
            checkDrawer(18, 12, 5, 1, GameStatus.PLAYER1, gs);
            checkDrawer(22, 8, 5, 1, GameStatus.PLAYER2, gs);
        } catch (IOException e) {
            fail("IOException not expected");
        }
    }

    private GameStatus initializeGeneralGameStatus() {
        GameStatus gs = new GameStatus("Game number 1", 2);
        Card d3 = new Card(3, "diamond");
        Card s3 = new Card(3, "spade");
        Card d4 = new Card(4, "diamond");
        Card s4 = new Card(4, "spade");
        Card d5 = new Card(5, "diamond");
        Card s5 = new Card(5, "spade");
        ListOfCards cards1 = new PlayerCards(new ArrayList<>(Arrays.asList(d3, s3)));
        ListOfCards cards2 = new PlayerCards(new ArrayList<>(Arrays.asList(d4, s4)));
        ListOfCards cards0 = new PlayerCards(new ArrayList<>(Arrays.asList(d5, s5)));
        gs.setCardList(cards1, 1);
        gs.setCardList(cards2, 2);
        gs.setCardList(cards0, 0);
        ChipsDrawer drawer1 = new ChipsDrawer(18, 12, 5, 1);
        ChipsDrawer drawer2 = new ChipsDrawer(22, 8, 5, 1);
        gs.setDrawer(drawer1, 1);
        gs.setDrawer(drawer2, 2);
        return gs;
    }
}
