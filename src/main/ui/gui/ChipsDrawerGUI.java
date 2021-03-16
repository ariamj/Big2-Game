package ui.gui;

import model.ChipsDrawer;
import ui.GameGUI;

import javax.swing.*;
import java.awt.*;

public class ChipsDrawerGUI extends JPanel {
    private static final Font FONT =  new Font("Times New Roman", 1, 15);
    private ChipsDrawer chips;

    public ChipsDrawerGUI(BigTwoGameGUI game) {
//        setPreferredSize(new Dimension(100, 100));
//        setPreferredSize(new Dimension((int) (GameGUI.WIDTH / 10) * 2, (int) (GameGUI.HEIGHT / 10) * 2));
//        setBackground(Color.lightGray);
        setLayout(new GridLayout(2, 1));
        chips = game.getPlayerChips();
        draw();
    }

    public void draw2() {
        chips.draw(this);
    }

    public void draw() {
        drawPlayerChips(Color.lightGray);
        drawPlayerChips(Color.orange);
    }

    public void drawPlayerChips(Color colour) {
        JPanel playerChipsArea = new JPanel();
//        playerChipsArea.setLayout(new GridLayout(5, 0));
        playerChipsArea.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        playerChipsArea.setPreferredSize(new Dimension((int) (GameGUI.WIDTH / 10) * 2,
                (int) (GameGUI.HEIGHT / 10) * 2));
        playerChipsArea.setBackground(colour);
        JLabel userName = new JLabel("PLAYER 1 CHIPS");
        userName.setFont(FONT);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridy = 0;
        constraints.ipady = 4;
        playerChipsArea.add(userName, constraints);

        addChipLabels(playerChipsArea, constraints);
    }

    public void addChipLabels(JPanel parent, GridBagConstraints constraints) {
        JLabel whiteChips = new JLabel("White Chips: " + chips.getNumWhiteChips());
        whiteChips.setFont(FONT);
        JLabel blueChips = new JLabel("Blue Chips: " + chips.getNumBlueChips());
        blueChips.setFont(FONT);
        JLabel redChips = new JLabel("Red Chips: " + chips.getNumRedChips());
        redChips.setFont(FONT);
        JLabel goldChips = new JLabel("Gold Chips: " + chips.getNumGoldChips());
        goldChips.setFont(FONT);
        constraints.gridy = 1;
        parent.add(whiteChips, constraints);
        constraints.gridy = 2;
        parent.add(blueChips, constraints);
        constraints.gridy = 3;
        parent.add(redChips, constraints);
        constraints.gridy = 4;
        parent.add(goldChips, constraints);
        add(parent);
    }
}
