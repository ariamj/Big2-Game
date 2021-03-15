package ui;

import exceptions.HandNotPlayableException;
import model.ChipsDrawer;
import ui.gui.BigTwoGameGUI;
import ui.gui.ChipsDrawerGUI;

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
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        game = new BigTwoGameGUI();
        add(game, BorderLayout.CENTER);
        JLabel statusLabel = new JLabel("Player one turn");
//        add(statusLabel, BorderLayout.NORTH);
        createTurnOptions();

        JPanel chipsArea = new JPanel();
//        chipsArea.setLayout(new GridLayout(0,1));
//        chipsArea.setSize(new Dimension(0,0));
        JPanel chipsGUI = new ChipsDrawerGUI(game);
        add(chipsArea, BorderLayout.CENTER);
        chipsArea.add(chipsGUI);

        pack();
        centreOnScreen();
        setVisible(true);
    }

    //EFFECTS: create buttons to pass, play, and quit
    public void createTurnOptions() {
        JPanel buttonsArea = new JPanel();
//        buttonsArea.setLayout(new GridLayout(0, 1));
        buttonsArea.setLayout(new FlowLayout());
//        buttonsArea.setSize(new Dimension(0, 0));
        add(buttonsArea, BorderLayout.SOUTH);

        buttonsArea.add(game.addPassButton());
        buttonsArea.add(game.addPlayButton());
        buttonsArea.add(game.addQuitButton());
    }
//
//    private JButton addPassButton() {
//        JButton passButton = new JButton("pass");
//        passButton.setActionCommand("pass");
//        passButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                game.pass();
//            }
//        });
//        return passButton;
//    }
//
//    private JButton addPlayButton() {
//        JButton playButton = new JButton("play");
//        playButton.setActionCommand("play");
//        playButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                try {
//                    game.play();
//                } catch (HandNotPlayableException he) {
//                    System.out.println(he.getMessage());
////                    throw new HandNotPlayableException();
//                }
//            }
//        });
//        return playButton;
//    }
//
//    private JButton addQuitButton() {
//        JButton quitButton = new JButton("quit");
//        quitButton.setActionCommand("quit");
//        quitButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                game.quit();
//            }
//        });
//        return quitButton;
//    }

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
