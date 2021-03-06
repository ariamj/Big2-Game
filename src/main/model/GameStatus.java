package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

public class GameStatus implements Writable {
    public static final int TABLE = 0;
    public static final int PLAYER1 = 1;
    public static final int PLAYER2 = 2;

    private String name;
    private List<Card> player1Cards;
    private List<Card> player2Cards;
    private ChipsDrawer drawer1;
    private ChipsDrawer drawer2;
    private List<Card> tableHand;
    private int playerTurn;

    //EFFECTS: constructs gameStatus with a name, empty list of cards for both players,
    // empty list of cards for the table pile, and default player turn 1
//    public GameStatus(String name, PlayerCards cards1, PlayerCards cards2, ChipsDrawer drawer1, ChipsDrawer drawer2) {
    public GameStatus(String name) {
        this.name = name;
        this.player1Cards = new ArrayList<>();
        this.player2Cards = new ArrayList<>();
        this.drawer1 = new ChipsDrawer();
        this.drawer2 = new ChipsDrawer();
        this.tableHand = new ArrayList<>();
        this.playerTurn = 1;
    }

    public void setDrawer(ChipsDrawer drawer, int playerNumber) {
        if (playerNumber == PLAYER1) {
            drawer1 = drawer;
        } else {
            drawer2 = drawer;
        }
    }

    //getters
    public String getName() {
        return this.name;
    }

    //getters
    public ChipsDrawer getDrawer(int playerNumber) {
        if (playerNumber == PLAYER1) {
            return drawer1;
        } else {
            return drawer2;
        }
    }

    //getters
    public int getPlayerTurn() {
        return playerTurn;
    }

    //MODIFIES: this
    //EFFECTS: updates which player's turn it is
    public void setPlayerTurn(int playerNumber) {
        playerTurn = playerNumber;
    }

//    //MODIFIES: this
//    //EFFECTS: updates the current hand on the table
//    public void updateTableHand(TablePile tablePile) {
//        tableHand = tablePile.getListOfCards();
//    }

    //MODIFIES: this
    //EFFECTS: sets specified list of cards to cards
    public void setCardList(ListOfCards cards, int playerNumber) {
        if (playerNumber == TABLE) {
            tableHand = cards.getListOfCards();
        } else if (playerNumber == PLAYER1) {
            player1Cards = cards.getListOfCards();
        } else {
            player2Cards = cards.getListOfCards();
        }
    }

    public void addCardToCardList(Card card, int playerNumber) {
        getCardList(playerNumber).add(card);
    }

    //MODIFIES: this
    //EFFECTS: remove card from the players hand, player depends on playerNumber
    public void removeCardsFromPlayer(ListOfCards cards, int playerNumber) {
        for (Card card : cards.getListOfCards()) {
            getCardList(playerNumber).remove(card);
        }
    }

    //EFFECTS: determines whose list of cards to modify/retrieve and returns it
    public List<Card> getCardList(int playerNumber) {
        if (playerNumber == TABLE) {
            return tableHand;
        } else if (playerNumber == PLAYER1) {
            return player1Cards;
        } else {
            return player2Cards;
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("player 1 cards", cardsToJson(player1Cards));
        json.put("player 1 chips", chipsToJson(drawer1));
        json.put("player 2 cards", cardsToJson(player2Cards));
        json.put("player 2 chips", chipsToJson(drawer2));
        json.put("table pile", cardsToJson(tableHand));
        json.put("player turn", playerTurn);
        return json;
    }

    //EFFECTS: returns cards as a JSON array
    //Citation: based on code from JsonSerializationDemo.WorkRoom.thingiesToJson()
    //          URL: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    private JSONArray cardsToJson(List<Card> cards) {
        JSONArray jsonArray = new JSONArray();
        for (Card c : cards) {
            jsonArray.put(c.toJson());
        }
        return jsonArray;
    }

    //EFFECTS: returns chips as a JSON array
    private JSONArray chipsToJson(ChipsDrawer chips) {
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(chips.toJson());
        return jsonArray;
    }
}
