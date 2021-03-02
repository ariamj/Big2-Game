package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

public class GameStatus implements Writable {
    private String name;
    private PlayerCards player1Cards;
    private PlayerCards player2Cards;
    private ChipsDrawer drawer1;
    private ChipsDrawer drawer2;
    private TablePile tableHand;
    private int playerTurn;

    //EFFECTS: constructs gameStatus with a name, initial list of cards for both players,
    // empty list of cards for the table pile, and default player 1 turn
    public GameStatus(String name, PlayerCards cards1, PlayerCards cards2, ChipsDrawer drawer1, ChipsDrawer drawer2) {
        this.name = name;
        this.player1Cards = cards1;
        this.player2Cards = cards2;
        this.drawer1 = drawer1;
        this.drawer2 = drawer2;
        this.tableHand = new TablePile();
        this.playerTurn = 0;
    }

    //getters
    public String getName() {
        return this.name;
    }

    //getters
    public PlayerCards getPlayerCards(int playerNumber) {
        if (playerNumber == 1) {
            return player1Cards;
        } else {
            return player2Cards;
        }
    }

    //getters
    public ChipsDrawer getDrawer(int playerNumber) {
        if (playerNumber == 1) {
            return drawer1;
        } else {
            return drawer2;
        }
    }

    //getters
    public TablePile getTableHand() {
        return tableHand;
    }

    //getters
    public int getPlayerTurn() {
        return playerTurn;
    }

    //MODIFIES: this
    //EFFECTS: updates which player's turn it is
    //          index of the player starts at 0 (which is player 1)
    public void updatePlayerTurn(int playerNumber) {
        playerTurn = playerNumber - 1;
    }

    //MODIFIES: this
    //EFFECTS: updates the current hand on the table
    public void updateTableHand(TablePile tablePile) {
        tableHand = tablePile;
    }

    //MODIFIES: this
    //EFFECTS: remove card from the players hand, player depends on playerNumber
    public void removeCardFromPlayer(Card card, int playerNumber) {
        if (playerNumber == 1) {
            int index = player1Cards.getCardsList().indexOf(card);
            player1Cards.removeCard(index);
        } else {
            int index = player2Cards.getCardsList().indexOf(card);
            player2Cards.removeCard(index);
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("player 1 cards", cardsToJson(player1Cards));
        json.put("player 1 chips", drawer1);
        json.put("player 2 cards", cardsToJson(player2Cards));
        json.put("player 2 chips", drawer2);
        json.put("table pile", cardsToJson(tableHand));
        json.put("player turn", playerTurn);
        return json;
    }

    //EFFECTS: returns cards as a JSON array
    private JSONArray cardsToJson(ListOfCards cards) {
        JSONArray jsonArray = new JSONArray();
        for (Card c : cards.getListOfCards()) {
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
