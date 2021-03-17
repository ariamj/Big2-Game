package ui;

import exceptions.HandNotPlayableException;
import model.ChipsDrawer;
import ui.gui.BigTwoGameGUI;
import ui.gui.ChipsDrawerGUI;
import ui.gui.TablePileGUI;
import ui.gui.UserInteractionArea;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GameGUI extends JFrame {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;

    private BigTwoGameGUI game;

    public GameGUI() {
        super("Big Two");
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setMaximumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        setLayout(new GridLayout(4,0));
        game = new BigTwoGameGUI();
        add(game);
        JLabel statusLabel = new JLabel("Player one turn");
//        add(statusLabel, BorderLayout.NORTH);
//        createTurnOptions();

//        JPanel chipsGUI = new ChipsDrawerGUI(game);
//        add(chipsGUI, BorderLayout.EAST);
//
//        JPanel tableGUI = new TablePileGUI(game);
//        add(tableGUI, BorderLayout.CENTER);
//
//        JPanel interaction = new UserInteractionArea(game);
//        add(interaction, BorderLayout.SOUTH);

        pack();
        centreOnScreen();
        setVisible(true);
    }

    public void update() {

        repaint();
    }

    //TODO: GUI CLEAN UP
    public void paintComponents(Graphics g) {
        super.paintComponents(g);
//        drawGame(g);
    }

    //TODO: GUI CLEAN UP
//    public void drawGame(Graphics g) {
    public void drawGame() {
//        game.draw(g);
        game.drawGame();
    }

    private void centreOnScreen() {
        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((scrn.width - getWidth()) / 2, (scrn.height - getHeight()) / 2);
    }
}
