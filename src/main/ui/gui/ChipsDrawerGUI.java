package ui.gui;

import model.ChipsDrawer;
import ui.GameGUI;

import javax.swing.*;
import java.awt.*;

public class ChipsDrawerGUI extends JPanel {
    private static final Font FONT = new Font("Times New Roman", 1, 15);
    private ChipsDrawer chips1;
    private ChipsDrawer chips2;
    private BigTwoGameGUI game;

    public ChipsDrawerGUI(BigTwoGameGUI game) {
//        setBackground(Color.lightGray);
//        setMaximumSize(new Dimension((GameGUI.WIDTH / 10) * 2, (GameGUI.HEIGHT / 10) * 2));
//        setLayout(new GridLayout(2, 1));
        setLayout(new GridBagLayout());
        this.game = game;
        draw();
    }

//    public void draw2() {
//        chips1.draw(this);
//    }

    public void draw() {
        chips1 = game.getDrawer(1);
        chips2 = game.getDrawer(2);
//        drawPlayerChips(chips1, 1, DARK_BLUE, 0);
        drawPlayerChips(chips1, 1, Color.lightGray, 0);
        drawPlayerChips(chips2, 2, Color.orange, 1);
    }

    public void drawPlayerChips(ChipsDrawer drawer, int playerNumber, Color colour, int y) {
        JPanel playerChipsArea = new JPanel();
//        playerChipsArea.setLayout(new GridLayout(5, 0));
        playerChipsArea.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        playerChipsArea.setPreferredSize(new Dimension((int) (GameGUI.WIDTH / 10) * 2,
                (int) (GameGUI.HEIGHT / 10) * 2));
        playerChipsArea.setBackground(colour);

        JLabel userName = new JLabel("PLAYER " + playerNumber + " CHIPS");
        userName.setFont(FONT);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridy = 0;
        constraints.ipady = 4;
        constraints.weighty = 2.0;
        playerChipsArea.add(userName, constraints);

        addChipLabels(playerChipsArea, constraints, y, drawer);
    }

    public void addChipLabels(JPanel parent, GridBagConstraints constraints, int y, ChipsDrawer drawer) {
        JLabel whiteChips = new JLabel("White Chips: " + drawer.getNumWhiteChips());
        whiteChips.setFont(FONT);
        JLabel blueChips = new JLabel("Blue Chips: " + drawer.getNumBlueChips());
        blueChips.setFont(FONT);
        JLabel redChips = new JLabel("Red Chips: " + drawer.getNumRedChips());
        redChips.setFont(FONT);
        JLabel goldChips = new JLabel("Gold Chips: " + drawer.getNumGoldChips());
        goldChips.setFont(FONT);
        constraints.gridy = 1;
        parent.add(whiteChips, constraints);
        constraints.gridy = 2;
        parent.add(blueChips, constraints);
        constraints.gridy = 3;
        parent.add(redChips, constraints);
        constraints.gridy = 4;
        parent.add(goldChips, constraints);
        constraints.gridy = y;
        add(parent, constraints);
    }

    public void update() {
        removeAll();
        draw();
        updateUI();
    }
}
