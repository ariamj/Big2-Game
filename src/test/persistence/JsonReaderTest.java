package persistence;

import model.GameStatus;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Citation: Based on code from JsonSerializationDemo.JsonReaderTest.java
 * URL: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
 */
public class JsonReaderTest extends JsonTest {

    @Test
    public void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            GameStatus gs = reader.read();
            fail("Expected IOException");
        } catch (IOException e) {
            //pass
        }
    }

    @Test
    public void testEmptyGameStatus() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyGameStatus.json");
        try {
            GameStatus gs = reader.read();
            assertEquals("Game number 1", gs.getLabel());
            checkCardLists(0, 0, 0, gs);
            assertEquals(1, gs.getPlayerTurn());
        } catch (IOException e) {
            fail("IOException not expected");
        }
    }

    @Test
    public void testGeneralGameStatus() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralGameStatus.json");
        try {
            GameStatus gs = reader.read();
            assertEquals("Game number 1", gs.getLabel());
            checkCardLists(3, 3, 2, gs);
            assertEquals(1, gs.getPlayerTurn());
            checkDrawer(20, 10, 5, 1, GameStatus.PLAYER1, gs);
            checkDrawer(20, 10, 5, 1, GameStatus.PLAYER2, gs);
        } catch (IOException e) {
            fail("IOException not expected");
        }
    }
}
