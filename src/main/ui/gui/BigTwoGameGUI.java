package ui.gui;

import exceptions.HandNotPlayableException;
import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.GameGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    public static final int POP_UP_WIDTH = 500;
    public static final int POP_UP_HEIGHT = 150;
    public static final int NEW_GAME_13 = 0;
    public static final int NEW_GAME_HALF_DECK = 1;
    public static final int LOAD_SAVED = 2;
//    public static final Font MSG_FONT = new Font("Times New Roman", 1, 32);
    public static final Font MSG_FONT = new Font("Phosphate", 1, 64);

    private Player user1;
    private Player user2;
    private Player dummyPlayer;
    private List<Player> playerList;

    private DeckOfCards deck;
    private TablePile table;
    private boolean firstTurn = true;
    private int playerTurn = 2;
    private boolean quitting = false;
    private GameStatus gs;
    private JsonWriter writer;
    private JsonReader reader;

    private ChipsDrawerGUI chipsGUI;
    private TablePileGUI tableGUI;
    private UserInteractionArea interaction;
    private GridBagConstraints constraints;
    private JLabel announce;

    public enum Actions {
        LOAD_GAME, SAVE, NO, QUIT, CANCEL
    }

    //EFFECTS: constructs a new Big 2 game
    public BigTwoGameGUI(int version) {
        setMinimumSize(new Dimension(GameGUI.WIDTH, GameGUI.HEIGHT));
        setMaximumSize(new Dimension(GameGUI.WIDTH, GameGUI.HEIGHT));
        setBackground(GameGUI.BACKGROUND);
        setLayout(new GridBagLayout());
        constraints = new GridBagConstraints();

        announce = new JLabel();
        announce.setFont(MSG_FONT);
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(announce, constraints);

        initializeGame();

        if (version == LOAD_SAVED) {
            loadGameStatus();
        } else {
            if (version == NEW_GAME_13) {
                initializeNewGame("13 cards");
            } else {
                initializeNewGame("half deck");
            }
            if (user1HasStartCard()) {
                playerTurn = 1;
            } else {
                playerTurn = 2;
            }
        }

//        if (version == NEW_GAME_13) {
//            initializeNewGame("13 cards");
//            if (user1HasStartCard()) {
//                playerTurn = 1;
//            } else {
//                playerTurn = 2;
//            }
//        } else {
//            loadGameStatus();
//        }

        playerList = new ArrayList<>(Arrays.asList(dummyPlayer, user1, user2));
        drawGame();
        update();
//        setVisible(true);
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
    //EFFECTS: initializes a new game
    public void initializeNewGame(String numCardsStart) {
        // TODO: implement choosing option
        List<Card> startCards = deck.dealCards(numCardsStart);
        user1 = new Player("user1", startCards, NUM_INITIAL_WHITE_CHIPS, NUM_INITIAL_BLUE_CHIPS,
                NUM_INITIAL_RED_CHIPS, NUM_INITIAL_GOLD_CHIPS);
        gs.setCardList(new PlayerCards(startCards), PLAYER1);
        gs.setDrawer(user1.getDrawer(), PLAYER1);

        startCards = deck.dealCards(numCardsStart);
        user2 = new Player("user2", startCards, NUM_INITIAL_WHITE_CHIPS, NUM_INITIAL_BLUE_CHIPS,
                NUM_INITIAL_RED_CHIPS, NUM_INITIAL_GOLD_CHIPS);
        gs.setCardList(new PlayerCards(startCards), GameStatus.PLAYER2);
        gs.setDrawer(user2.getDrawer(), GameStatus.PLAYER2);
    }

    /**
     * ========================================================================================================
     * GRAPHIC DRAWING ----- May not need at all
     * ========================================================================================================
     */

    public void drawGame() {
        tableGUI = new TablePileGUI(this);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 3;
        constraints.gridheight = 2;
        add(tableGUI, constraints);

        chipsGUI = new ChipsDrawerGUI(this);
        constraints.gridx = 3;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 2;
        constraints.anchor = GridBagConstraints.EAST;
        add(chipsGUI, constraints);

        interaction = new UserInteractionArea(this);
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 4;
        constraints.gridheight = 0;
        constraints.anchor = GridBagConstraints.SOUTH;
        add(interaction, constraints);
    }

    /**
     * ========================================================================================================
     * RUN THE GAME -- HELPERS
     * ========================================================================================================
     */

    public void nextPlayer() {
        if (playerTurn == 1) {
            playerTurn++;
        } else {
            playerTurn--;
        }
        gs.setPlayerTurn(playerTurn);
    }

    public void update() {
        announce.setText("It is now " + playerList.get(playerTurn).getName() + "'s turn.");
        tableGUI.update();
        chipsGUI.update();
        interaction.update();
    }

    /**
     * ------------------------------------------------------------------------------------
     * RUN GAME - SECONDARY HELPERS
     * ------------------------------------------------------------------------------------
     */

    //EFFECTS: returns true if user1 has the starting card
    // 3 diamond or increasing suits if 3 diamond is not in play
    private boolean user1HasStartCard() {
        Card startCard = findStartingCard();
        return hasCard(user1, startCard.getRank(), startCard.getSuit());
    }

    //EFFECTS: returns true if game is over
    // game is over when either player has played all their cards (ie. has no more cards left)
    private boolean gameOver() {
        return user1.getNumCards() == 0 || user2.getNumCards() == 0;
    }

    //MODIFIES: this
    //EFFECTS: loser gives amount chips lost to winner and displays play's chips
    //          if game is over due to user quitting, no distribution is done
    private void distributeWinning() {
        Player winner = null;
        Player loser = null;
        int lostPoints = 0;
        if (!quitting) {
            if (isWinner(user1)) {
                winner = user1;
                loser = user2;
            } else {
                winner = user2;
                loser = user1;
            }
            lostPoints = pointsLost(loser);
            winner.collectChips(loser.payChips(lostPoints));
        }
        createPopUp(winner.getName() + " is the winner! \n " + winner.getName()
                + " won " + lostPoints + " points worth of chips!", "Yay!", "Oh well",
                Actions.QUIT, Actions.QUIT);
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

    /**
     * ========================================================================================================
     * CHOSE TO PASS
     * ========================================================================================================
     */

    public void pass() {
        table.setHand(new Hand());
        gs.setCardList(table, GameStatus.TABLE);
//        tableGUI.update();
        nextPlayer();
        update();
    }

    /**
     * ========================================================================================================
     * CHOSE TO PLAY HAND
     * ========================================================================================================
     */

    //TODO: CLEAN UP
    //EFFECTS: play a hand
//    public void play() throws HandNotPlayableException {
    public void play(List<Integer> playCardsIndex, Player player) throws HandNotPlayableException {
        Hand selectedCards = new Hand(getPlayCards(playCardsIndex, player));
        if (playable(selectedCards, table)) {
            //TODO: fix alternating player
            playHand(selectedCards, player);
            if (!gameOver()) {
                nextPlayer();
                update();
            } else {
                distributeWinning();
            }
        } else {
            //TODO: DEAL WITH PRINTING OUT MSG FOR EXCEPTIONS
            throw new HandNotPlayableException("You can't play that hand");
        }
    }

    public List<Card> getPlayCards(List<Integer> cardsIndex, Player player) {
        List<Card> toPlay = new ArrayList<>();
        for (Integer i : cardsIndex) {
            toPlay.add(player.getCards().getCard(i));
        }
        return toPlay;
    }

    //EFFECTS: play the selected cards
    private void playHand(Hand selectedCards, Player player) {
        player.takeATurn(selectedCards);
        gs.removeCardsFromPlayer(selectedCards, player);
//        gs.removeCardsFromPlayer(selectedCards, playerList.indexOf(player));
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

    /**
     * ------------------------------------------------------------------------------------
     * PLAY - SECONDARY HELPERS
     * ------------------------------------------------------------------------------------
     */

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

    /**
     * ========================================================================================================
     * QUIT GAME
     * ========================================================================================================
     */

    //EFFECTS: ask whether user wants to save game status to file
    //      - if yes: save game status to file
    //      - if no: quit application
    public void quit() {
        createPopUp("Do you want to save the game?", "Yes", "No", Actions.SAVE, Actions.QUIT);
    }

    //EFFECTS: saves the game status to file
    private void saveGameStatus() {
        try {
            writer.open();
            writer.write(gs);
            writer.close();
            System.out.println("Saved game status to file: " + JSON_FILE);
        } catch (IOException e) {
            System.out.println("Unable to write game status to file: " + JSON_FILE);
            JFrame msg = new JFrame();
            JOptionPane.showMessageDialog(msg, "Unable to write game status to file: "
                    + BigTwoGameGUI.JSON_FILE);
        }
    }

    /**
     * ========================================================================================================
     * LOAD GAME
     * ========================================================================================================
     */

//    public void askToLoadGame() {
//        createPopUp("Do you want to load a saved game from file?", "Yes", "No",
//                Actions.LOAD_GAME, Actions.NO);
////        update();
//    }

    //MODIFIES: this
    //EFFECTS: loads the game status from file
    //TODO: CLEAN UP
    private void loadGameStatus() {
        try {
            gs = reader.read();
            initializeLoadedGame();
            System.out.println("Loaded game from file: " + JSON_FILE);
        } catch (IOException e) {
            System.out.println("Unable to load game from file: " + JSON_FILE);
            JFrame msg = new JFrame();
            JOptionPane.showMessageDialog(msg, "Unable to load game from file: " + BigTwoGameGUI.JSON_FILE);
        }
    }

    //MODIFIES: this
    //EFFECTS: initializes the game according to saved stats from file
    private void initializeLoadedGame() {

        int playerNumber = PLAYER1;
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

    /**
     * ========================================================================================================
     * HELPER FOLD FUNCTIONS
     * ========================================================================================================
     */

    public void createPopUp(String text, String button1Text, String button2Text, Actions button1, Actions button2) {
        JFrame parent = new JFrame();
        parent.setPreferredSize(new Dimension(POP_UP_WIDTH, POP_UP_HEIGHT));
        JLabel question = new JLabel(text);
        question.setFont(new Font("Times New Roman", 14, 18));

        JPanel buttonArea = new JPanel();

        parent.add(question, BorderLayout.NORTH);
        buttonArea.add(createButton(button1Text, button1, parent));
        buttonArea.add(createButton(button2Text, button2, parent));
        parent.add(buttonArea, BorderLayout.SOUTH);
        parent.pack();
        parent.setAlwaysOnTop(true);
        centreOnScreen(parent);
        parent.setVisible(true);
    }

    //EFFECTS: creates a button labeled text that does action on parent
    private JButton createButton(String text, Actions action, JFrame parent) {
        JButton button = new JButton(text);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executeActions(action, parent);
            }
        });
        return button;
    }

    /**
     * ========================================================================================================
     * ACTIONS
     * ========================================================================================================
     */

    public void executeActions(Actions action, JFrame parent) {
        if (action.equals(Actions.LOAD_GAME)) {
            loadGameStatus();
            parent.setVisible(false);
        } else if (action.equals(Actions.SAVE)) {
            saveGameStatus();
            parent.setVisible(false);
            JFrame msg = new JFrame();
            JOptionPane.showMessageDialog(msg, "Saved game status to file: "
                    + BigTwoGameGUI.JSON_FILE);
        } else if (action.equals(Actions.NO) || action.equals(Actions.CANCEL) || action == Actions.QUIT) {
            parent.setVisible(false);
            if (action == Actions.QUIT) {
                // show "has quit" text --> remove buttons and stuff
            }
        }
    }

//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        if (quitting) {
//            quitGameText(g);
//        }
//    }
//
//    public void quitGameText(Graphics g) {
//        g.setColor(Color.BLACK);
//        g.setFont(MSG_FONT);
//        FontMetrics fm = g.getFontMetrics();
//        centreString("You have quit the game", g, fm, GameGUI.HEIGHT / 2);
//    }
//
//    public void centreString(String text, Graphics g, FontMetrics fm, int y) {
//        int width = fm.stringWidth(text);
//        g.drawString(text, (GameGUI.WIDTH - width) / 2, y);
//    }

    /**
     * ========================================================================================================
     * OTHERS - graphic design helpers
     * ========================================================================================================
     */

    //TODO: SIMILAR DUPLICATE TO ONE IN GAMEGUI
    private void centreOnScreen(JFrame parent) {
        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
        parent.setLocation((scrn.width - parent.getWidth()) / 2, (scrn.height - parent.getHeight()) / 2);
    }

    /**
     * ========================================================================================================
     * GETTERS/SETTERS
     * ========================================================================================================
     */

    public TablePile getTable() {
        return table;
    }

    public Player getPlayer(int playerNumber) {
        return playerList.get(playerNumber);
    }

    public Player getCurrPlayer() {
        return playerList.get(playerTurn);
    }

    //getters
    public ChipsDrawer getDrawer(int playerNumber) {
        if (playerNumber == PLAYER1) {
            return user1.getDrawer();
        } else {
            return user2.getDrawer();
        }
    }
}
