package model;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a player of the game of Big 2 with a name, cards, and drawer of chips
 */
public class Player extends JPanel {
    private static final Chips WHITE_CHIP = new Chips("white");
    private static final Chips BLUE_CHIP = new Chips("blue");
    private static final Chips RED_CHIP = new Chips("red");
    private static final Chips GOLD_CHIP = new Chips("gold");

    private String name;
    private PlayerCards cards;
    private ChipsDrawer drawer;

    //EFFECTS: makes a new player with a name, initial cards, and starting amount of chips
    public Player(String name, List<Card> startCards, int numWhiteChips, int numBlueChips,
                  int numRedChips, int numGoldChips) {
        this.name = name;
        cards = new PlayerCards(startCards);
        drawer = new ChipsDrawer(numWhiteChips, numBlueChips, numRedChips, numGoldChips);
    }

    //REQUIRES: cards is not empty
    //MODIFIES: this
    //EFFECTS: play hand by removing cards in hand from cards
    public void takeATurn(Hand hand) {
        cards.playHand(hand);
    }

    //MODIFIES: this
    //EFFECTS: adds the chips other players paid to player's drawer
    public void collectChips(List<Chips> chipsPaid) {
        for (Chips chip : chipsPaid) {
            drawer.addChipToDrawer(chip);
        }
    }

    //REQUIRES: drawer is not empty
    //MODIFIES: this
    //EFFECTS: removes the amount of chips corresponding to pointsLost form drawer starting with gold chips
    public List<Chips> payChips(int pointsLost) {
        List<Chips> paidChips = new ArrayList<>();
        while (pointsLost >= 1) {
            while (pointsLost >= 5) {
                while (pointsLost >= 10) {
                    while (pointsLost >= 25) {
                        pointsLost = removeChips(GOLD_CHIP, paidChips, pointsLost, 25);
                    }
                    if (pointsLost < 10) {
                        break;
                    }
                    pointsLost = removeChips(RED_CHIP, paidChips, pointsLost, 10);
                }
                if (pointsLost < 5) {
                    break;
                }
                pointsLost = removeChips(BLUE_CHIP, paidChips, pointsLost, 5);
            }
            if (pointsLost < 1) {
                break;
            }
            pointsLost = removeChips(WHITE_CHIP, paidChips, pointsLost, 1);
        }
        return paidChips;
    }

    //REQUIRES: drawer is not empty
    //MODIFIES: this
    //EFFECTS: removes chip from drawer and update points lost
    private int removeChips(Chips chip, List<Chips> paidChips, int pointsLost, int pointsValue) {
        drawer.removeChipFromDrawer(chip);
        paidChips.add(GOLD_CHIP);
        pointsLost -= pointsValue;
        return pointsLost;
    }

    //EFFECTS: returns the numberof cards in cards
    public int getNumCards() {
        return cards.getSize();
    }

    //getters
    public String getName() {
        return name;
    }

    //getters
    public PlayerCards getCards() {
        return cards;
    }

    //getters
    public ChipsDrawer getDrawer() {
        return drawer;
    }
}
