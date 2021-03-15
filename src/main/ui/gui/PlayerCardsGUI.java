package ui.gui;

import model.PlayerCards;

import javax.swing.*;

public class PlayerCardsGUI extends JPanel {
    private PlayerCards cards;

    public PlayerCardsGUI(BigTwoGameGUI game) {
        cards = game.getPlayerCards();
    }

    public void draw() {
        cards.draw(this);
    }

    public void update() {
        // stub
        // updates cards of player cards after playing the turn
    }
}
