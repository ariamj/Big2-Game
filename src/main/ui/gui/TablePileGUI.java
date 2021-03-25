package ui.gui;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import com.sun.xml.internal.fastinfoset.algorithm.HexadecimalEncodingAlgorithm;
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
    private BigTwoGameGUI game;
    private JPanel cardsArea;
    private CardsGUI cardsGUI;

//    public TablePileGUI() {
    public TablePileGUI(BigTwoGameGUI game) {
        setSize(new Dimension(WIDTH, HEIGHT));
        setBackground(GameGUI.BACKGROUND);
//        table = game.getTable();
//        Hand list = new Hand(new ArrayList<>(Arrays.asList(new Card(1, "diamond"),
//                new Card(4, "spade"))));
//        table = list;

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
//        table.draw(cardsArea);
        add(cardsArea, BorderLayout.CENTER);
    }

    public void update() {
        // stub
//        removeAll();
        if (cardsArea != null) {
            remove(cardsArea);
        }
        drawTable();
        updateUI();

    }

    public static void main(String[] args) {
//        new TablePileGUI();
    }
}
