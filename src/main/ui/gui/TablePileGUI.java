package ui.gui;

import model.Card;
import model.Hand;
import model.ListOfCards;
import model.TablePile;
import ui.GameGUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class TablePileGUI extends JPanel {
    public static final int WIDTH = GameGUI.WIDTH / 2;
    public static final int HEIGHT = GameGUI.HEIGHT / 3;

    private Hand table;

//    public TablePileGUI() {
    public TablePileGUI(BigTwoGameGUI game) {
        setSize(new Dimension(WIDTH, HEIGHT));
//        table = game.getTable();
        Hand list = new Hand(new ArrayList<>(Arrays.asList(new Card(1, "diamond"),
                new Card(4, "spade"))));
        table = list;
        drawTable();

        setVisible(true);
    }

    public void drawTable() {
//        JPanel parent = new JPanel();
        table.draw(this);
//        add(parent);
    }

    public void update() {
        // stubb
        // updates after player plays a hand
    }

    public static void main(String[] args) {
//        new TablePileGUI();
    }
}
