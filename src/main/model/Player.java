package model;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private static final Chips WHITE_CHIP = new Chips("white");
    private static final Chips BLUE_CHIP = new Chips("blue");
    private static final Chips RED_CHIP = new Chips("red");
    private static final Chips GOLD_CHIP = new Chips("gold");

    private String name;
    private PlayerCards cards;
    private ChipsDrawer drawer;

    public Player(String name, List<Card> startCards, int numWhiteChips, int numBlueChips,
                  int numRedChips, int numGoldChips) {
        this.name = name;
        cards = new PlayerCards(startCards);
        drawer = new ChipsDrawer(numWhiteChips, numBlueChips, numRedChips, numGoldChips);
    }

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

    //MODIFIES: this
    //EFFECTS: removes the amount of chips corresponding to pointsLost form drawer
    public List<Chips> payChips(int pointsLost) {
        List<Chips> paidChips = new ArrayList<>();
        while (pointsLost >= 1) {
            while (pointsLost >= 5) {
                while (pointsLost >= 10) {
                    while (pointsLost >= 25) {
                        drawer.removeChipFromDrawer(GOLD_CHIP);
                        paidChips.add(GOLD_CHIP);
                        pointsLost -= 25;
                    }
                    drawer.removeChipFromDrawer(RED_CHIP);
                    paidChips.add(RED_CHIP);
                    pointsLost -= 10;
                }
                drawer.removeChipFromDrawer(BLUE_CHIP);
                paidChips.add(BLUE_CHIP);
                pointsLost -= 5;
            }
            drawer.removeChipFromDrawer(WHITE_CHIP);
            paidChips.add(WHITE_CHIP);
            pointsLost -= 1;
        }
        return paidChips;
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

    //getters
    public int getNumCards() {
        return cards.getSize();
    }
}
