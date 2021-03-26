package ui.gui;

import model.Hand;
import ui.GameGUI;

import javax.swing.*;
import java.awt.*;

public class TablePileGUI extends JPanel {
    public static final int WIDTH = GameGUI.WIDTH / 2;
    public static final int HEIGHT = GameGUI.HEIGHT / 3;

    private Hand table;
    private BigTwoGameGUI game;
    private JPanel cardsArea;
    private CardsGUI cardsGUI;

    public TablePileGUI(BigTwoGameGUI game) {
        setSize(new Dimension(WIDTH, HEIGHT));
        setBackground(GameGUI.BACKGROUND);

        this.game = game;
        this.cardsGUI = new CardsGUI();
        drawTable();

        setVisible(true);
    }

    public void drawTable() {
        cardsArea = new JPanel();
        cardsArea.setBackground(GameGUI.BACKGROUND);
        table = game.getTable();
        cardsGUI.drawCardList(cardsArea, table);
        add(cardsArea, BorderLayout.CENTER);
    }

    public void update() {
        if (cardsArea != null) {
            remove(cardsArea);
        }
        drawTable();
        updateUI();

    }
}
