package ui;

import exceptions.HandNotPlayableException;
import ui.gui.BigTwoGameGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GameGUI extends JFrame {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private BigTwoGameGUI game;

    public GameGUI() {
        super("Big Two");
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        game = new BigTwoGameGUI();
        JLabel statusLabel = new JLabel("Player one turn");
        add(statusLabel, BorderLayout.NORTH);
        createTurnOptions();
        pack();
        centreOnScreen();
        setVisible(true);
    }

    //EFFECTS: create buttons to pass, play, and quit
    public void createTurnOptions() {
        JPanel buttonsArea = new JPanel();
        buttonsArea.setLayout(new GridLayout(0, 1));
        buttonsArea.setSize(new Dimension(0, 0));
        add(buttonsArea, BorderLayout.SOUTH);

        buttonsArea.add(addPassButton());
        buttonsArea.add(addPlayButton());
        buttonsArea.add(addQuitButton());
    }

    private JButton addPassButton() {
        JButton passButton = new JButton("pass");
        passButton.setActionCommand("pass");
        passButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.pass();
            }
        });
        return passButton;
    }

    private JButton addPlayButton() {
        JButton playButton = new JButton("play");
        playButton.setActionCommand("play");
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    game.play();
                } catch (HandNotPlayableException he) {
                    System.out.println(he.getMessage());
                }
            }
        });
        return playButton;
    }

    private JButton addQuitButton() {
        JButton quitButton = new JButton("quit");
        quitButton.setActionCommand("quit");
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.quit();
            }
        });
        return quitButton;
    }

    //TODO: GUI CLEAN UP
    public void paintComponents(Graphics g) {
        super.paintComponents(g);
        drawGame(g);
    }

    //TODO: GUI CLEAN UP
    public void drawGame(Graphics g) {
//        game.draw(g);
    }

    private void centreOnScreen() {
        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((scrn.width - getWidth()) / 2, (scrn.height - getHeight()) / 2);
    }
}
