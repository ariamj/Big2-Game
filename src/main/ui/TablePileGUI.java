package ui;

import model.Hand;

import javax.swing.*;
import java.awt.*;

/**
 * Represents the area where the cards on the table is shown
 */
public class TablePileGUI extends JPanel {
    public static final int WIDTH = GameGUI.WIDTH / 2;
    public static final int HEIGHT = GameGUI.HEIGHT / 3;

    private Hand table;
    private BigTwoGameGUI game;
    private JPanel cardsArea;

    //EFFECTS: creates an area for the cards
    public TablePileGUI(BigTwoGameGUI game) {
        setSize(new Dimension(WIDTH, HEIGHT));
        setBackground(GameGUI.BACKGROUND);
        this.game = game;
        drawTable();
        setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: draw the cards onto the table
    public void drawTable() {
        cardsArea = new JPanel();
        cardsArea.setBackground(GameGUI.BACKGROUND);
        table = game.getTable();
        CardsGUI.drawCardList(cardsArea, table);
        add(cardsArea, BorderLayout.CENTER);
    }

    //MODIFIES: this
    //EFFECTS: updates the table
    public void update() {
        if (cardsArea != null) {
            remove(cardsArea);
        }
        drawTable();
        updateUI();
    }
}
