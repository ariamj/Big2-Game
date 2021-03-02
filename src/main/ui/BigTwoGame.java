package ui;

import model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Represents the game of Big 2
 */
public class BigTwoGame {
    private static final List<String> RANK_VALUES = new ArrayList<>(Arrays.asList("X", "A", "2", "3", "4", "5", "6",
            "7", "8", "9", "10", "J", "Q", "K"));
    private static final int NUM_INITIAL_WHITE_CHIPS = 20;
    private static final int NUM_INITIAL_BLUE_CHIPS = 10;
    private static final int NUM_INITIAL_RED_CHIPS = 5;
    private static final int NUM_INITIAL_GOLD_CHIPS = 1;
    private static final int ANY_CARD_POINT_VALUE = 1;
    private static final int TWO_CARD_POINT_VALUE = 5;
    private static final int PASS = 0;
    private static final int PLAY = 1;
    private static final int QUIT = 2;

    private Player user1;
    private Player user2;
    private DeckOfCards deck;
    private TablePile table;
    private boolean firstTurn = true;
    private boolean quitting = false;
    private Scanner input;

    //EFFECTS: runs the Big 2 game
    public BigTwoGame() {
        runBigTwoGame();
    }

    //EFFECTS: runs the Big 2 game
    // choose 13 cards or half a deck then continue con alternating between players to play a turn
    // player with the 3 of diamond starts the game
    // game is over when one player has played all their cards, pay/collect chips accordingly
    private void runBigTwoGame() {
        initializeGame();
        displayChips();
        List<Player> playerList = new ArrayList<>(Arrays.asList(user1, user2));
        int playerIndex = 1;
        if (user1HasStartCard()) {
            playATurn(user1);
            firstTurn = false;
        }
        while (!gameOver() && !quitting) {
            playATurn(playerList.get(playerIndex));
            if (playerIndex == 0) {
                playerIndex++;
            } else {
                playerIndex--;
            }
            if (firstTurn) {
                firstTurn = false;
            }
        }
        distributeWinning();
    }

    //EFFECTS: initializes fields and deals the deck
    private void initializeGame() {
        input = new Scanner(System.in);
        table = new TablePile();
        deck = new DeckOfCards();
        deck.shuffleDeck();
        String cardsAmount = chooseAmountOfInitialCards();
        List<Card> startCards = deck.dealCards(cardsAmount);
        user1 = new Player("user1", startCards, NUM_INITIAL_WHITE_CHIPS, NUM_INITIAL_BLUE_CHIPS,
                NUM_INITIAL_RED_CHIPS, NUM_INITIAL_GOLD_CHIPS);
        startCards = deck.dealCards(cardsAmount);
        user2 = new Player("user2", startCards, NUM_INITIAL_WHITE_CHIPS, NUM_INITIAL_BLUE_CHIPS,
                NUM_INITIAL_RED_CHIPS, NUM_INITIAL_GOLD_CHIPS);
    }

    //EFFECTS: ask whether to play starting with 13 cards only or half a deck
    private String chooseAmountOfInitialCards() {
        System.out.println("How many cards would you like to start with?");
        System.out.println("Type \"13 cards\" to start with 13 cards and \"half deck\" for half a deck: ");
        return input.nextLine();
    }

    //EFFECTS: player gets to play a turn whether that be to pass or to play or quit
    // pass = do nothing and next player goes
    // play = play a valid hand onto the table
    // quit = quit game
    private void playATurn(Player player) {
        System.out.print("Hit enter to confirm you are " + player.getName() + ": ");
        input.nextLine();
        int nextTask = processCommand(getCommand(player, 1), "pass", "play");
        if (nextTask == QUIT) {
            hasQuit();
            return;
        }
        if (nextTask == PLAY) {
            Hand handPlayed = playAHand();
            while (!canPlayHand(handPlayed, table)) {
                System.out.println("You can't play that hand... ");
                int newNextTask = processCommand(getCommand(player, 2), "Y", "N");
                if (newNextTask == PASS || newNextTask == QUIT) {
                    if (newNextTask == QUIT) {
                        hasQuit();
                    }
                    return;
                }
                handPlayed = playAHand();
            }
            player.takeATurn(handPlayed);
            table.playHandInPile(handPlayed);
        }
    }

    //EFFECTS: get user input on what they would like to do next (pass, play, or quit)
    private String getCommand(Player player, int msgNumber) {
        displayCards(player);
        if (msgNumber == 1) {
            System.out.println("Would you like to pass or play? \n Type 'pass' to pass, 'play' to play, 'q' to quit: ");
        } else if (msgNumber == 2) {
            System.out.println("Want to pass after all? 'Y' for yes, 'N' for no, 'q' to quit: ");
        }
        return input.nextLine();
    }

    //EFFECTS: return what user would like to do for their turn
    // returns PASS if user wants to pass, PLAY if user wants to play, QUIT id user wants to quit
    private int processCommand(String command, String pass, String play) {
        while ((firstTurn && command.equalsIgnoreCase(pass))
                || (!command.equalsIgnoreCase(pass) && !command.equalsIgnoreCase(play)
                && !command.equalsIgnoreCase("q"))) {
            if (firstTurn && command.equalsIgnoreCase(pass)) {
                System.out.println("You cannot pass as the starting player...");
            }
            System.out.print("Try again: ");
            command = input.nextLine();
        }
        if (command.equalsIgnoreCase("q")) {
            return QUIT;
        } else if (command.equalsIgnoreCase(pass)) {
            table.setHand(new Hand());
            return PASS;
        }
        return PLAY;
    }

    //EFFECTS: returns cards that user has picked to play as a hand
    private Hand playAHand() {
        List<Card> cardsToPlay = new ArrayList<>();
        System.out.println("Type card you would like to play (rank followed by suit name) "
                + "or 'p' to play selected cards: ");
        String card = "";
        while (!card.equalsIgnoreCase("p")) {
            card = input.nextLine();
            if (card.length() > 3 && RANK_VALUES.contains(card.substring(0, 2).trim().toUpperCase())
                    && ListOfCards.SUITS.contains(card.substring(2).trim().toLowerCase())) {
                int rank = RANK_VALUES.indexOf(card.substring(0, 2).trim().toUpperCase());
                cardsToPlay.add(new Card(rank, card.substring(2).trim().toLowerCase()));
            } else if (!card.equalsIgnoreCase("p")) {
                System.out.print("Try again: ");
            }
        }
        return new Hand(cardsToPlay);
    }

    //EFFECTS: returns true if given hand is a valid hand to be played
    // ie. ranking of hand is higher or equal to most recent played hand
    // and is a valid type hand
    private boolean canPlayHand(Hand hand, Hand tableHand) {
        if (firstTurn && !hand.contains(findStartingCard())) {
            return false;
        }
        return hand.isValidTypeHand() && hand.isValidPlay(tableHand);
    }

    //EFFECTS: returns true if user1 has the starting card
    // 3 diamond or increasing suits if 3 diamond is not in play
    private boolean user1HasStartCard() {
        Card startCard = findStartingCard();
        return hasCard(user1, startCard.getRank(), startCard.getSuit());
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
        displayChips();
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

    //EFFECTS: sets quitting to be true (user has quit the game)
    private void hasQuit() {
        quitting = true;
    }

    //EFFECTS: returns true if game is over
    // game is over when either player has played all their cards (ie. has no more cards left)
    private boolean gameOver() {
        return user1.getNumCards() == 0 || user2.getNumCards() == 0;
    }

    //EFFECTS: print out the table cards and player's cards
    private void displayCards(Player player) {
        System.out.println("Most recent played hand: " + table.toString());
        System.out.println("Your cards: " + player.getCards().toString());
    }

    //EFFECTS: displays all users' chips
    private void displayChips() {
        System.out.println("Chips for " + user1.getName() + ": " + user1.getDrawer().toString());
        System.out.println("Chips for " + user2.getName() + ": " + user2.getDrawer().toString());
    }
}
