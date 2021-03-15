package ui.gui;

import model.ChipsDrawer;
import ui.GameGUI;

import javax.swing.*;
import java.awt.*;

public class ChipsDrawerGUI extends JPanel {
    private ChipsDrawer chips;

    public ChipsDrawerGUI(BigTwoGameGUI game) {
//        setPreferredSize(new Dimension(100, 100));
        setPreferredSize(new Dimension((int) (GameGUI.WIDTH / 10) * 2, (int) (GameGUI.HEIGHT / 10) * 2));
        setBackground(Color.lightGray);
        setLayout(new GridLayout(0, 1));
        chips = game.getPlayerChips();
        draw();
    }

    public void draw2() {
        chips.draw(this);
    }

    public void draw() {
        JLabel whiteChips = new JLabel("White Chips: " + chips.getNumWhiteChips());
        whiteChips.setFont(new Font("Times New Roman", 1, 15));
        JLabel blueChips = new JLabel("Blue Chips: " + chips.getNumBlueChips());
        blueChips.setFont(new Font("Times New Roman", 1, 15));
        JLabel redChips = new JLabel("Red Chips: " + chips.getNumRedChips());
        redChips.setFont(new Font("Times New Roman", 1, 15));
        JLabel goldChips = new JLabel("Gold Chips: " + chips.getNumGoldChips());
        goldChips.setFont(new Font("Times New Roman", 1, 15));
        add(whiteChips);
        add(blueChips);
        add(redChips);
        add(goldChips);
    }
}
