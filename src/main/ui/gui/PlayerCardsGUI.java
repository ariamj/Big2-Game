package ui.gui;

import model.PlayerCards;

public class PlayerCardsGUI {
    private PlayerCards cards;

    public PlayerCardsGUI(BigTwoGameGUI game) {
        cards = game.getPlayerCards();
    }

    public void draw() {
        cards.draw();
    }

    public void update() {
        // stub
        // updates cards of player cards after playing the turn
    }
}
