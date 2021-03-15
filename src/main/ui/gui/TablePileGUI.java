package ui.gui;

import model.TablePile;

import javax.swing.*;

public class TablePileGUI extends JPanel {
    private TablePile table;

    public TablePileGUI(BigTwoGameGUI game) {
        table = game.getTable();
        drawTable();
        setVisible(true);
    }

    public void drawTable() {
        table.draw(this);
    }

    public void update() {
        // stubb
        // updates after player plays a hand
    }

}
