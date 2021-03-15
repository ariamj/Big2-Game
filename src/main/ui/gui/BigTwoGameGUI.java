package ui.gui;

import com.sun.scenario.effect.impl.sw.java.JSWBlend_COLOR_BURNPeer;
import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;
import exceptions.HandNotPlayableException;
import exceptions.InvalidCardException;
import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Represents the game of Big 2
 */
public class BigTwoGameGUI extends JPanel {
    private static final String JSON_FILE = "./data/gameStatus.json";
    private static final List<String> RANK_VALUES = new ArrayList<>(Arrays.asList("X", "A", "2", "3", "4", "5", "6",
            "7", "8", "9", "10", "J", "Q", "K"));
    private static final int NUM_INITIAL_WHITE_CHIPS = 20;
    private static final int NUM_INITIAL_BLUE_CHIPS = 10;
    private static final int NUM_INITIAL_RED_CHIPS = 5;
    private static final int NUM_INITIAL_GOLD_CHIPS = 1;
    private static final int ANY_CARD_POINT_VALUE = 1;
    private static final int TWO_CARD_POINT_VALUE = 5;
    public static final int POP_UP_WIDTH = 500;
    public static final int POP_UP_HEIGHT = 150;

    private Player user1;
    private Player user2;
    private Player dummyPlayer;
    private List<Player> playerList;
    private DeckOfCards deck;
    private TablePile table;
    private boolean firstTurn = true;
    private int playerTurn = 2;
    private boolean quitting = false;
    private Scanner input;
    private GameStatus gs;
    private JsonWriter writer;
    private JsonReader reader;
    boolean load = false;

    public enum Actions {
        LOAD_GAME, SAVE, NO, QUIT, PLAY_CARD, CANCEL
    }

    //EFFECTS: constructs a new Big 2 game
    public BigTwoGameGUI() {
        gs = new GameStatus("Game number 1");
        writer = new JsonWriter(JSON_FILE);
        reader = new JsonReader(JSON_FILE);
        table = new TablePile();
        deck = new DeckOfCards();
        deck.shuffleDeck();

//        createTurnOptions();
        askToLoadGame();
        if (!load) {
            initializeNewGame();
        }
        playerList = new ArrayList<>(Arrays.asList(dummyPlayer, user1, user2));
    }

    //MODIFIES: this
    //EFFECTS: initializes a new game
    public void initializeNewGame() {
        // TODO: implement choosing option
        List<Card> startCards = deck.dealCards("13 cards");
        user1 = new Player("user1", startCards, NUM_INITIAL_WHITE_CHIPS, NUM_INITIAL_BLUE_CHIPS,
                NUM_INITIAL_RED_CHIPS, NUM_INITIAL_GOLD_CHIPS);
        gs.setCardList(new PlayerCards(startCards), GameStatus.PLAYER1);
        gs.setDrawer(user1.getDrawer(), GameStatus.PLAYER1);

        startCards = deck.dealCards("13 cards");
        user2 = new Player("user2", startCards, NUM_INITIAL_WHITE_CHIPS, NUM_INITIAL_BLUE_CHIPS,
                NUM_INITIAL_RED_CHIPS, NUM_INITIAL_GOLD_CHIPS);
        gs.setCardList(new PlayerCards(startCards), GameStatus.PLAYER2);
        gs.setDrawer(user2.getDrawer(), GameStatus.PLAYER2);
    }

    /**
     * ========================================================================================================
     * GRAPHIC DRAWING
     * ========================================================================================================
     */

    public void drawGame() {
//        chipsGUI.draw(this);
//        drawChips();
//        drawPlayerCards();
//        drawTablePile();
    }

    /**
     * ========================================================================================================
     * RUN THE GAME
     * ========================================================================================================
     */

    public void runGame() {
        if (firstTurn) {
            if (user1HasStartCard()) {
//                playATurn(user1);
                firstTurn = false;
            }
        }
        while (!gameOver() && !quitting) {
            gs.setPlayerTurn(playerTurn);
//            playATurn(playerList.get(playerTurn));
            if (playerTurn == 1) {
                playerTurn++;
            } else {
                playerTurn--;
            }
            if (firstTurn) {
                firstTurn = false;
            }
        }
        distributeWinning();
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
        if (!quitting) {
            if (isWinner(user1)) {
                user1.collectChips(user2.payChips(pointsLost(user2)));
            } else {
                user2.collectChips(user1.payChips(pointsLost(user1)));
            }
        }
//        displayChips();
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
        // stub
    }

    /**
     * ========================================================================================================
     * CHOSE TO PLAY HAND
     * ========================================================================================================
     */

    //TODO: CLEAN UP
    //EFFECTS: play a hand
    public void play() throws HandNotPlayableException {
        // stub
//        Hand selectedCards = selectCards();
        try {
            Hand selectedCards = selectCards();
            //for test purposes
            System.out.println(selectedCards.toString());
            if (playable(selectedCards, table)) {
                //TODO: fix alternating player
                playHand(selectedCards, user1);
            } else {
                //TODO: DEAL WITH PRINTING OUT MSG FOR EXCEPTIONS
                throw new HandNotPlayableException("You can't play that hand");
            }
        } catch (InvalidCardException e) {
            //TODO: DEAL WITH PRINTING OUT MSG FOR EXCEPTIONS
            System.out.println(e.getMessage());
        }
        //if game is not over:
        //nextPlayer();
        //else:
        //end game and stuff
    }

    //EFFECTS: play the selected cards
    private void playHand(Hand selectedCards, Player player) {
        player.takeATurn(selectedCards);
        gs.removeCardsFromPlayer(selectedCards, playerList.indexOf(player));
        table.playHandInPile(selectedCards);
        gs.setCardList(selectedCards, GameStatus.TABLE);
    }

    //EFFECTS: select cards to play
    private Hand selectCards() throws InvalidCardException {
        List<Card> cardsToPlay = new ArrayList<>();
        String cardStr = "";
        do {
            cardStr = JOptionPane.showInputDialog(getParent(),
                    "Type card you would like to play (rank followed by suit name)"
                    + "\n Or 'p' to play hand", null);
            if (cardStr.length() > 3 && RANK_VALUES.contains(cardStr.substring(0, 2).trim().toUpperCase())
                    && ListOfCards.SUITS.contains(cardStr.substring(2).trim().toLowerCase())) {
                int rank = RANK_VALUES.indexOf(cardStr.substring(0, 2).trim().toUpperCase());
                cardsToPlay.add(new Card(rank, cardStr.substring(2).trim().toLowerCase()));
            } else if (!cardStr.equalsIgnoreCase("p")) {
                throw new InvalidCardException("That is not a card");
            }
        } while (!cardStr.equalsIgnoreCase("p"));
        return new Hand(cardsToPlay);
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
        }
    }

    /**
     * ========================================================================================================
     * LOAD GAME
     * ========================================================================================================
     */

    public void askToLoadGame() {
        createPopUp("Do you want to load a saved game from file?", "Yes", "No",
                Actions.LOAD_GAME, Actions.NO);
    }

    //MODIFIES: this
    //EFFECTS: loads the game status from file
    private void loadGameStatus() {
        try {
            gs = reader.read();
            initializeLoadedGame();
            System.out.println("Loaded game from file: " + JSON_FILE);
        } catch (IOException e) {
            System.out.println("Unable to load game from file: " + JSON_FILE);
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

    /**
     * ========================================================================================================
     * BUTTONS
     * ========================================================================================================
     */

//    //EFFECTS: create buttons to pass, play, and quit
//    public void createTurnOptions() {
//        JPanel buttonsArea = new JPanel();
////        buttonsArea.setLayout(new GridLayout(0, 1));
//        buttonsArea.setLayout(new FlowLayout());
////        buttonsArea.setSize(new Dimension(0, 0));
//        add(buttonsArea, BorderLayout.SOUTH);
//
//        buttonsArea.add(addPassButton());
//        buttonsArea.add(addPlayButton());
//        buttonsArea.add(addQuitButton());
//    }

    public JButton addPassButton() {
        JButton passButton = new JButton("pass");
        passButton.setActionCommand("pass");
        passButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pass();
            }
        });
        return passButton;
    }

    public JButton addPlayButton() {
        JButton playButton = new JButton("play");
        playButton.setActionCommand("play");
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    play();
                } catch (HandNotPlayableException he) {
                    System.out.println(he.getMessage());
//                    throw new HandNotPlayableException();
                }
            }
        });
        return playButton;
    }

    public JButton addQuitButton() {
        JButton quitButton = new JButton("quit");
        quitButton.setActionCommand("quit");
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quit();
            }
        });
        return quitButton;
    }

    /**
     * ========================================================================================================
     * HELPER FOLD FUNCTIONS
     * ========================================================================================================
     */

    private void createPopUp(String text, String button1Text, String button2Text, Actions button1, Actions button2) {
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

    private void executeActions(Actions action, JFrame parent) {
        if (action.equals(Actions.LOAD_GAME)) {
            load = true;
            loadGameStatus();
            parent.setVisible(false);
        } else if (action.equals(Actions.SAVE)) {
            saveGameStatus();
            parent.setVisible(false);
        } else if (action.equals(Actions.NO) || action.equals(Actions.CANCEL) || action == Actions.QUIT) {
            parent.setVisible(false);
            if (action == Actions.QUIT) {
                // show "has quit" text --> remove buttons and stuff
            }
        } else if (action.equals(Actions.PLAY_CARD)) {
            //stub
            String cardStr = JOptionPane.showInputDialog(parent,
                    "Type card you would like to play (rank followed by suit name)", null);
        }
    }

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

    public PlayerCards getPlayerCards() {
        //TODO: FIX FOR ALTERNATING PLAYER
        return user1.getCards();
    }

    public ChipsDrawer getPlayerChips() {
        //TODO: FIX FOR ALTERNATING PLAYER
        return user1.getDrawer();
    }
}
