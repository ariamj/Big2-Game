package ui.gui;

import exceptions.HandNotPlayableException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.SwingConstants.TOP;

public class PlayerTab extends JPanel {
    private List<Integer> cardsIndex;
    private BigTwoGameGUI game;

    private GridBagConstraints constraints;

//    public PlayerTab() {
    public PlayerTab(BigTwoGameGUI game) {
        setMaximumSize(new Dimension(UserInteractionArea.WIDTH, UserInteractionArea.HEIGHT));

        setLayout(new GridBagLayout());
        constraints = new GridBagConstraints();

        cardsIndex = new ArrayList<>();
        for (int i = 0; i < game.getPlayerCards().getCardsList().size(); i++) {
            cardsIndex.add(i);
        }

        this.game = game;
        placeCards();
        createTurnOptions();
    }

    public void placeCards() {
        //TESTING PURPOSE
        createCardsRow(0, 0);
        createSelectCardsRow(0, 1);
        //TODO: IMPLEMENT FOR IF PLAYING WITH HALF DECK
        createCardsRow(0, 2);
        createSelectCardsRow(0, 3);
    }

    public void createCardsRow(int x, int y) {
        JPanel cardsArea = new JPanel();
        cardsArea.setLayout(new GridLayout(0, 13));
        constraints.gridx = x;
        constraints.gridy = y;
        add(cardsArea, constraints);
        for (int i = 0; i < 13; i++) {
            game.getPlayerCards().getCardsList().get(0).draw(cardsArea);
        }
    }

    public void createSelectCardsRow(int x, int y) {
        JCheckBox selectCard;
        JPanel selectArea = new JPanel();
        selectArea.setLayout(new GridLayout(0,13));
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = x;
        constraints.gridy = y;
        add(selectArea, constraints);
        for (int i = 0; i < 13; i++) {
            selectCard = new JCheckBox();
            selectCard.setVerticalAlignment(TOP);
            selectArea.add(selectCard);
        }
    }

    //EFFECTS: create buttons to pass, play, and quit
    public void createTurnOptions() {
        JPanel buttonsArea = new JPanel();
        buttonsArea.setLayout(new FlowLayout(FlowLayout.CENTER));
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.ipady = 1;
        add(buttonsArea, constraints);

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
