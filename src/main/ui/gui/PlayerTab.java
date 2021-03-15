package ui.gui;

import exceptions.HandNotPlayableException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerTab extends JPanel {
    private BigTwoGameGUI game;

//    public PlayerTab() {
    public PlayerTab(BigTwoGameGUI game) {
//        setSize(new Dimension(UserInteractionArea.WIDTH, UserInteractionArea.HEIGHT));
        this.game = game;
        placeCards();
        createTurnOptions();
    }

    public void placeCards() {
        //TESTING PURPOSE
        game.getPlayerCards().getCardsList().get(0).draw(this);
        validate();
    }

    //EFFECTS: create buttons to pass, play, and quit
    public void createTurnOptions() {
        JPanel buttonsArea = new JPanel();
//        buttonsArea.setLayout(new GridLayout(0, 1));
        buttonsArea.setLayout(new FlowLayout());
//        buttonsArea.setSize(new Dimension(0, 0));
        add(buttonsArea, BorderLayout.SOUTH);

        buttonsArea.add(addPassButton());
        buttonsArea.add(addPlayButton());
        buttonsArea.add(addQuitButton());
    }

    public JButton addPassButton() {
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

    public JButton addPlayButton() {
        JButton playButton = new JButton("play");
        playButton.setActionCommand("play");
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    game.play();
                } catch (HandNotPlayableException he) {
                    System.out.println(he.getMessage());
//                    throw new HandNotPlayableException();
                }
            }
        });
        return playButton;
    }

    public JButton addQuitButton() {
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
}
