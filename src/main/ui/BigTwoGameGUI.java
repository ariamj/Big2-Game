package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.exceptions.FirstTurnException;
import ui.exceptions.HandNotPlayableException;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static model.GameStatus.PLAYER1;

/**
 * Represents the game of Big 2
 */
public class BigTwoGameGUI extends JPanel {
    public static final String JSON_FILE = "./data/gameStatus.json";
    private static final int NUM_INITIAL_WHITE_CHIPS = 20;
    private static final int NUM_INITIAL_BLUE_CHIPS = 10;
    private static final int NUM_INITIAL_RED_CHIPS = 5;
    private static final int NUM_INITIAL_GOLD_CHIPS = 1;
    private static final int ANY_CARD_POINT_VALUE = 1;
    private static final int TWO_CARD_POINT_VALUE = 5;
    public static final int NEW_GAME_13 = 0;
    public static final int NEW_GAME_HALF_DECK = 1;
    public static final int LOAD_SAVED = 2;
    public static final Font ANNOUNCE_FONT = new Font("Phosphate", 1, 64);

    private Player user1;
    private Player user2;
    private Player dummyPlayer;
    private List<Player> playerList;

    private DeckOfCards deck;
    private TablePile table;
    private boolean firstTurn = true;
    private static boolean newRound = false;
    private int playerTurn = 2;
    private static GameStatus gs;
    private static JsonWriter writer;
    private JsonReader reader;

    private ChipsDrawerGUI chipsGUI;
    private TablePileGUI tableGUI;
    private UserInteractionArea interaction;
    private GridBagConstraints constraints;
    private JLabel announce;

    //EFFECTS: constructs a new Big 2 game
    public BigTwoGameGUI(int version) {
        setMinimumSize(new Dimension(GameGUI.WIDTH, GameGUI.HEIGHT));
        setMaximumSize(new Dimension(GameGUI.WIDTH, GameGUI.HEIGHT));
        setBackground(GameGUI.BACKGROUND);
        setLayout(new GridBagLayout());
        constraints = new GridBagConstraints();
        announce = new JLabel();
        announce.setFont(ANNOUNCE_FONT);
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(announce, constraints);

        initializeGame();
        setGameVersion(version);
        playerList = new ArrayList<>(Arrays.asList(dummyPlayer, user1, user2));
        drawGame();
        update();
    }

    //MODIFIES: this
    //EFFECTS: initializes fields and deals the deck
    private void initializeGame() {
        gs = new GameStatus("Game number 1");
        writer = new JsonWriter(JSON_FILE);
        reader = new JsonReader(JSON_FILE);
        table = new TablePile();
        deck = new DeckOfCards();
        deck.shuffleDeck();
    }

    //MODIFIES: this
    //EFFECTS: initializes the game based on which version wanted
    //          - LOAD_SAVE: loads a saved game from file
    //          - NEW_GAME_13: starts the game with 13 cards each
    //          - else: starts the game with half a deck each (ie. 26 cards each)
    private void setGameVersion(int version) {
        if (version == LOAD_SAVED) {
            loadGameStatus();
        } else {
            if (version == NEW_GAME_13) {
                initializeNewGameOrRound("13 cards");
            } else if (version == NEW_GAME_HALF_DECK) {
                initializeNewGameOrRound("half deck");
            }
            if (user1HasStartCard()) {
                playerTurn = 1;
            } else {
                playerTurn = 2;
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: initializes a new game or new round if newRound == true
    private void initializeNewGameOrRound(String numCardsStart) {
        if (newRound) {
            loadGameStatus();
        }
        initializePlayer1(numCardsStart, GameStatus.PLAYER1);
        initializePlayer2(numCardsStart, GameStatus.PLAYER2);
        newRound = false;
    }

    //MODIFIES: this
    //EFFECTS: initializes a user1's cards and drawer
    private void initializePlayer1(String numCardsStart, int playerNumber) {
        ChipsDrawer drawer = gs.getDrawer(playerNumber);
        List<Card> startCards = deck.dealCards(numCardsStart);
        if (newRound) {
            user1 = new Player("user" + playerNumber, startCards, drawer.getNumWhiteChips(),
                    drawer.getNumBlueChips(), drawer.getNumRedChips(), drawer.getNumGoldChips());
        } else {
            user1 = new Player("user" + playerNumber, startCards, NUM_INITIAL_WHITE_CHIPS,
                    NUM_INITIAL_BLUE_CHIPS, NUM_INITIAL_RED_CHIPS, NUM_INITIAL_GOLD_CHIPS);
        }
        gs.setDrawer(user1.getDrawer(), playerNumber);
        gs.setCardList(new PlayerCards(startCards), playerNumber);
    }

    //MODIFIES: this
    //EFFECTS: initializes a user2's cards and drawer
    private void initializePlayer2(String numCardsStart, int playerNumber) {
        ChipsDrawer drawer = gs.getDrawer(playerNumber);
        List<Card> startCards = deck.dealCards(numCardsStart);
        if (newRound) {
            user2 = new Player("user" + playerNumber, startCards, drawer.getNumWhiteChips(),
                    drawer.getNumBlueChips(), drawer.getNumRedChips(), drawer.getNumGoldChips());
        } else {
            user2 = new Player("user" + playerNumber, startCards, NUM_INITIAL_WHITE_CHIPS,
                    NUM_INITIAL_BLUE_CHIPS, NUM_INITIAL_RED_CHIPS, NUM_INITIAL_GOLD_CHIPS);
        }
        gs.setDrawer(user2.getDrawer(), playerNumber);
        gs.setCardList(new PlayerCards(startCards), playerNumber);
    }

    //MODIFIES: this
    //EFFECTS: draws all the components of the game onto this
    private void drawGame() {
        tableGUI = new TablePileGUI(this);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 3;
        constraints.gridheight = 2;
        add(tableGUI, constraints);

        chipsGUI = new ChipsDrawerGUI(this);
        constraints.gridx = 3;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.gridheight = 2;
        constraints.anchor = GridBagConstraints.EAST;
        add(chipsGUI, constraints);

        interaction = new UserInteractionArea(this);
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 5;
        constraints.gridheight = 0;
        constraints.anchor = GridBagConstraints.SOUTH;
        add(interaction, constraints);
    }

    //MODIFIES: this
    //EFFECTS: update playerTurn with next player
    private void nextPlayer() {
        if (playerTurn == 1) {
            playerTurn++;
        } else {
            playerTurn--;
        }
        gs.setPlayerTurn(playerTurn);
    }

    //MODIFIES: this
    //EFFECTS: updates the game
    private void update() {
        announce.setText("It is now " + playerList.get(playerTurn).getName() + "'s turn.");
        tableGUI.update();
        chipsGUI.update();
        interaction.update();
    }

    //EFFECTS: returns true if user1 has the starting card
    // 3 diamond or increasing suits if 3 diamond is not in play
    private boolean user1HasStartCard() {
        Card startCard = findStartingCard();
        return hasCard(user1, startCard.getRank(), startCard.getSuit());
    }

    //EFFECTS: returns true if game is over
    // game is over when either player has played all their cards (ie. has no more cards left)
    public boolean gameOver() {
        return user1.getNumCards() == 0 || user2.getNumCards() == 0;
    }

    //MODIFIES: this
    //EFFECTS: loser gives amount chips lost to winner
    private void distributeWinning() {
        Player winner = null;
        Player loser = null;
        int lostPoints = 0;
        if (isWinner(user1)) {
            winner = user1;
            loser = user2;
        } else {
            winner = user2;
            loser = user1;
        }
        lostPoints = pointsLost(loser);
        winner.collectChips(loser.payChips(lostPoints));
        gs.setDrawer(user1.getDrawer(), GameStatus.PLAYER1);
        gs.setDrawer(user2.getDrawer(), GameStatus.PLAYER2);
        saveGameStatus();
        chipsGUI.update();
        Helper.createPopUp(winner.getName() + " is the winner! \n " + winner.getName() + " won "
                        + lostPoints + " points worth of chips!", "Yay!", "Oh well",
                Helper.GAME_OVER, Helper.GAME_OVER, this);
    }

    //EFFECTS: returns the amount of points player has lost based on number of cards and what cards play still has
    private int pointsLost(Player player) {
        int pointsLost = 0;
        for (Card card : player.getCards().getCardsList()) {
            if (card.getRank() == 2) {
                pointsLost += TWO_CARD_POINT_VALUE;
            } else {
                pointsLost += ANY_CARD_POINT_VALUE;
            }
        }
        return pointsLost;
    }

    //EFFECTS: returns true if player is the winner (the one with no more cards left)
    private boolean isWinner(Player player) {
        return player.getNumCards() == 0;
    }

    //MODIFIES: this
    //EFFECTS: player passed; if not first turn, reset table
    //          else: throw FirstTurnException
    public void pass() throws FirstTurnException {
        if (!firstTurn) {
            table.setHand(new Hand());
            gs.setCardList(table, GameStatus.TABLE);
            nextPlayer();
            update();
        } else {
            throw new FirstTurnException("You can't pass as the starting player");
        }
    }

    //MODIFIES: this
    //EFFECTS: play a hand, if game is over, distribute winnings
    //          - throws HandNotPlayableException is the chosen hand cannot be played
    public void play(List<Card> playCards, Player player) throws HandNotPlayableException {
        Hand selectedCards = new Hand(playCards);
        if (playable(selectedCards, table)) {
            playHand(selectedCards, player);
            if (!gameOver()) {
                nextPlayer();
                update();
            } else {
                distributeWinning();
            }
        } else {
            throw new HandNotPlayableException("You can't play that hand");
        }
    }

    //MODIFIES: this
    //EFFECTS: play the selected cards
    private void playHand(Hand selectedCards, Player player) {
        player.takeATurn(selectedCards);
        gs.removeCardsFromPlayer(selectedCards, player);
        table.playHandInPile(selectedCards);
        gs.setCardList(selectedCards, GameStatus.TABLE);
        firstTurn = false;
    }

    //EFFECTS: returns true if given hand is a valid hand to be played
    // ie. ranking of hand is higher or equal to most recent played hand
    // and is a valid type hand
    private boolean playable(Hand hand, TablePile tableHand) {
        if (firstTurn && !hand.contains(findStartingCard())) {
            return false;
        }
        return hand.isValidTypeHand() && hand.isValidPlay(tableHand);
    }

    //EFFECTS: determine what the first card is
    // starts from 3 of diamond then increase suits
    private Card findStartingCard() {
        int initRank = 3;
        int index = 0;
        while (!cardInPlay(initRank, ListOfCards.SUITS.get(index))) {
            index++;
            if (index == 4) {
                initRank++;
                index = 0;
            }
        }
        return new Card(initRank, ListOfCards.SUITS.get(index));
    }

    //EFFECTS: returns true if the desired starting card with rank rank and suit suit
    // is in play (ie. either user has it)
    private boolean cardInPlay(int rank, String suit) {
        return hasCard(user1, rank, suit) || hasCard(user2, rank, suit);
    }

    //EFFECTS: returns true if player has the desired starting card
    private boolean hasCard(Player player, int rank, String suit) {
        for (Card card : player.getCards().getCardsList()) {
            if (card.getRank() == rank && card.getSuit().equals(suit)) {
                return true;
            }
        }
        return false;
    }

    //EFFECTS: ask whether user wants to save game status to file
    //      - if yes: save game status to file
    //      - if no: quit application
    public void quit() {
        Helper.createPopUp("Do you want to save the game?", "Save",
                "Don't save", Helper.SAVE, Helper.DONT_SAVE, this);
    }

    //EFFECTS: saves the game status to file
    public static void saveGameStatus() {
        try {
            writer.open();
            writer.write(gs);
            writer.close();
        } catch (IOException e) {
            Helper.showMsg("Unable to write game status to file: " + JSON_FILE);
        }
    }

    //MODIFIES: this
    //EFFECTS: loads the game status from file
    private void loadGameStatus() {
        try {
            gs = reader.read();
            if (!newRound) {
                initializeLoadedGame();
            }
        } catch (IOException e) {
            Helper.showMsg("Unable to load game from file: " + JSON_FILE);
        }
    }

    //MODIFIES: this
    //EFFECTS: initializes the game according to saved stats from file
    private void initializeLoadedGame() {
        int playerNumber = GameStatus.PLAYER1;
        ChipsDrawer drawer = gs.getDrawer(playerNumber);
        user1 = new Player("user1", gs.getCardList(playerNumber), drawer.getNumWhiteChips(),
                drawer.getNumBlueChips(), drawer.getNumRedChips(), drawer.getNumGoldChips());
        playerNumber = GameStatus.PLAYER2;
        drawer = gs.getDrawer(playerNumber);
        user2 = new Player("user2", gs.getCardList(playerNumber), drawer.getNumWhiteChips(),
                drawer.getNumBlueChips(), drawer.getNumRedChips(), drawer.getNumGoldChips());
        table.setHand(new Hand(gs.getCardList(GameStatus.TABLE)));

        playerTurn = gs.getPlayerTurn();
        firstTurn = false;
    }

    //setter
    public static void setIsNewRound() {
        newRound = true;
    }

    //getter
    public TablePile getTable() {
        return table;
    }

    //getter
    public Player getPlayer(int playerNumber) {
        return playerList.get(playerNumber);
    }

    //getter
    public Player getCurrPlayer() {
        return playerList.get(playerTurn);
    }

    //getter (according to playerNumber)
    public ChipsDrawer getDrawer(int playerNumber) {
        if (playerNumber == PLAYER1) {
            return user1.getDrawer();
        } else {
            return user2.getDrawer();
        }
    }

    //getter
    public static boolean getNewRound() {
        return newRound;
    }
}
