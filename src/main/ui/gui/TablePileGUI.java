package ui.gui;

import model.TablePile;

import javax.swing.*;

public class TablePileGUI extends JPanel {
    private TablePile table;

    public TablePileGUI(BigTwoGameGUI game) {
        table = game.getTable();
    }

    public void drawTable() {
        table.draw();
    }

    public void update() {
        // stubb
        // updates after player plays a hand
    }

}
