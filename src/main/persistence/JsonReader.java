package persistence;

import model.Card;
import model.ChipsDrawer;
import model.GameStatus;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/*
 * Represents a reader that reads the game status from JSON data from file
 *
 * Citation: Based on code from JsonSerializationDemo.JsonReader.java
 *           URL: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
 */
public class JsonReader {
    private String source;

    //EFFECTS: constructs a new object to read source file
    public JsonReader(String source) {
        this.source = source;
    }

    //EFFECTS: read game status from file and returns it
    // throws IOException if an error occurs while reading data form file
    public GameStatus read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseGameStatus(jsonObject);
    }

    //EFFECTS: reads the source file as a string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    //EFFECTS: parses game status from JSONObject and returns it
    private GameStatus parseGameStatus(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int playerTurn = jsonObject.getInt("player turn");
        GameStatus gs = new GameStatus(name, playerTurn);
        addCards(gs, jsonObject, 1);
        addCards(gs, jsonObject, 2);
        addPlayerChips(gs, jsonObject, 1);
        addPlayerChips(gs, jsonObject, 2);
        addCards(gs, jsonObject, 0);
        return gs;
    }

    //MODIFIES: gs
    //EFFECTS: parses cards from JSONObject and adds them to the game status
    //          playerNumber determines where to add cards; 0 = table, 1 = player1, 2 = player2
    private void addCards(GameStatus gs, JSONObject jsonObject, int playerNumber) {
        String key = "player " + playerNumber + " cards";
        if (playerNumber == 0) {
            key = "table pile";
        }
        JSONArray jsonArray = jsonObject.getJSONArray(key);
        for (Object json : jsonArray) {
            JSONObject nextCard = (JSONObject) json;
            addCard(gs, nextCard, playerNumber);
        }
    }

    //MODIFIES: gs
    //EFFECTS: parses card from JSONObject and adds them to the game status
    //          playerNumber determines where to add cards; 0 = table, 1 = player1, 2 = player2
    private void addCard(GameStatus gs, JSONObject jsonObject, int playerNumber) {
        int rank = jsonObject.getInt("rank");
        String suit = jsonObject.getString("suit");
        Card card = new Card(rank, suit);
        gs.addCardToCardList(card, playerNumber);
    }

    //MODIFIES: gs
    //EFFECTS: parses player chips from JSONObject and adds them to the game status
    //          playerNumber determines where to add cards; 1 = player1, 2 = player2
    private void addPlayerChips(GameStatus gs, JSONObject jsonObject, int playerNumber) {
        JSONArray jsonArray = jsonObject.getJSONArray("player " + playerNumber + " chips");
        for (Object json : jsonArray) {
            JSONObject nextChip = (JSONObject) json;
            addChips(gs, nextChip, playerNumber);
        }
    }

    //MODIFIES: gs
    //EFFECTS: parses chips from JSONObject and adds them to the game status as a ChipsDrawer
    //          playerNumber determines where to add cards; 1 = player1, 2 = player2
    private void addChips(GameStatus gs, JSONObject jsonObject, int playerNumber) {
        int numWhite = jsonObject.getInt("white chips");
        int numBlue = jsonObject.getInt("blue chips");
        int numRed = jsonObject.getInt("red chips");
        int numGold = jsonObject.getInt("gold chips");
        ChipsDrawer drawer = new ChipsDrawer(numWhite, numBlue, numRed, numGold);
        gs.setDrawer(drawer, playerNumber);
    }
}
