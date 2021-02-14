package ui;

import model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class BigTwoGame {
    private static final List<String> SUITS = new ArrayList<>(Arrays.asList("diamond", "clubs", "heart", "spade"));
    private static final List<String> RANK_VALUES = new ArrayList<>(Arrays.asList("X", "A", "2", "3", "4", "5", "6",
            "7", "8", "9", "10", "J", "Q", "K"));
    private static final int NUM_INITIAL_WHITE_CHIPS = 20;
    private static final int NUM_INITIAL_BLUE_CHIPS = 10;
    private static final int NUM_INITIAL_RED_CHIPS = 5;
    private static final int NUM_INITIAL_GOLD_CHIPS = 1;
    private static final int ANY_CARD_POINT_VALUE = 1;
    private static final int TWO_CARD_POINT_VALUE = 5;

    private Player user1;
    private Player user2;
    private DeckOfCards deck;
    private TablePile table;
    private Scanner input;

    public BigTwoGame() {
        runBigTwoGame();
    }

    public void runBigTwoGame() {
        initializeGame();
        List<Player> playerList = new ArrayList<>(Arrays.asList(user1, user2));
        int playerIndex = 1;
        if (user1HasStartCard()) {
            playATurn(user1);
        }
        while (!gameOver()) {
            playATurn(playerList.get(playerIndex));
            if (playerIndex == 0) {
                playerIndex++;
            } else {
                playerIndex--;
            }
        }
        if (isWinner(user1)) {
            user1.collectChips(user2.payChips(pointsLost(user2)));
        } else {
            user2.collectChips(user1.payChips(pointsLost(user1)));
        }
        System.out.println("Chips for " + user1.getName() + ": " + user1.getDrawer().toString());
        System.out.println("Chips for " + user2.getName() + ": " + user2.getDrawer().toString());
    }

    public void initializeGame() {
        input = new Scanner(System.in);
        table = new TablePile();
        deck = new DeckOfCards();
        String cardsAmount = chooseAmountOfInitialCards();
        List<Card> startCards = deck.dealCards(cardsAmount);
        user1 = new Player("user1", startCards, NUM_INITIAL_WHITE_CHIPS, NUM_INITIAL_BLUE_CHIPS,
                NUM_INITIAL_RED_CHIPS, NUM_INITIAL_GOLD_CHIPS);
        startCards = deck.dealCards(cardsAmount);
        user2 = new Player("user2", startCards, NUM_INITIAL_WHITE_CHIPS, NUM_INITIAL_BLUE_CHIPS,
                NUM_INITIAL_RED_CHIPS, NUM_INITIAL_GOLD_CHIPS);
    }

    //EFFECTS: ask whether to play starting with 13 cards only of half a deck
    public String chooseAmountOfInitialCards() {
        System.out.println("How many cards would you like to start with?");
        System.out.println("Type \"13 cards\" to start with 13 cards and \"half deck\" for half a deck: ");
        String cardsAmount = input.nextLine();
        return cardsAmount;
    }

    public boolean user1HasStartCard() {
        Card startCard = findStartingCard();
        return hasCard(user1, startCard.getRank(), startCard.getSuit());
    }

    //EFFECTS: determine what the first card is
    public Card findStartingCard() {
        int initRank = 3;
        int index = 0;
        while (!cardInPlay(initRank, SUITS.get(index))) {
            index++;
            //for when shuffle deck
//            if (index == 4) {
//                initRank++;
//                index = 0;
//            }
        }
        return new Card(initRank, SUITS.get(index));
    }

    //EFFECTS: returns true if the 3 of diamond in in play (ie. either user or computer has it
    public boolean cardInPlay(int rank, String suit) {
        return hasCard(user1, rank, suit) || hasCard(user2, rank, suit);
    }

    //EFFECTS: returns true if the computer has the 3 of diamond
    public boolean hasCard(Player player, int rank, String suit) {
        for (Card card : player.getCards().getCardsList()) {
            if (card.getRank() == rank && card.getSuit().equals(suit)) {
                return true;
            }
        }
        return false;
    }

    //play a turn -- pass or play
    public void playATurn(Player player) {
        System.out.print("Hit enter to confirm you are " + player.getName() + ": ");
        input.nextLine();
        System.out.println("Your cards: " + player.getCards().toString());
        System.out.println("Would you like to pass or play?");
        System.out.println("Type 'pass' to pass and 'play' to play: ");
        String command = input.nextLine();
        while (!command.equalsIgnoreCase("pass") && !command.equalsIgnoreCase("play")) {
            System.out.print("Try again: ");
            command = input.nextLine();
        }
        if (command.equalsIgnoreCase("pass")) {
            return;
        }
        Hand handPlayed = playAHand();
        while (!canPlayHand(handPlayed, table)) {
            System.out.println("Try again: ");
            handPlayed = playAHand();
        }
        player.takeATurn(handPlayed);
        table.playHandInPile(handPlayed);
        System.out.println("Most recent played hand: " + table.toString());
    }

    /**
     * EFFECTS: returns true if given hand is a valid hand
     * ie. ranking of hand is higher or equal to most recent played hand
     * and is a valid type hand
     */
    public boolean canPlayHand(Hand hand, Hand tableHand) {
        return hand.isValidTypeHand() && hand.isValidPlay(tableHand);
    }

    //EFFECTS: pick cards to play
    public Hand playAHand() {
        List<Card> cardsToPlay = new ArrayList<>();
        System.out.println("Type card you would like to play (rank followed by suit name) "
                + "or 'p' to play selected cards: ");
        String card = "";
        while (!card.equalsIgnoreCase("p")) {
            card = input.nextLine();
            if (card.length() > 3 && RANK_VALUES.contains(card.substring(0, 2).trim())
                    && SUITS.contains(card.substring(2).trim().toLowerCase())) {
                int rank = RANK_VALUES.indexOf(card.substring(0, 2).trim());
                cardsToPlay.add(new Card(rank, card.substring(2).trim().toLowerCase()));
            } else if (!card.equalsIgnoreCase("p")) {
                System.out.print("Try again: ");
            }
        }
        return new Hand(cardsToPlay);
    }

    //check game over -- player has no more cards left
    public boolean gameOver() {
        return user1.getNumCards() == 0 || user2.getNumCards() == 0;
    }

    //determine winner
    public boolean isWinner(Player player) {
        if (player.getNumCards() == 0) {
            return true;
        }
        return false;
    }

    //get points lost for player
    public int pointsLost(Player player) {
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
}
