package ui.gui;

import exceptions.HandNotPlayableException;
import model.Card;
import model.ListOfCards;
import model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.SwingConstants.CENTER;
import static javax.swing.SwingConstants.TOP;

public class PlayerTab extends JPanel {
    private List<Integer> cardsIndex;
    private BigTwoGameGUI game;
    private Player player;

    private GridBagConstraints constraints;
    private JPanel cardsArea;
    private JPanel selectArea;

//    public PlayerTab() {
    public PlayerTab(BigTwoGameGUI game, Player player) {
        setMaximumSize(new Dimension(UserInteractionArea.WIDTH, UserInteractionArea.HEIGHT));

        setLayout(new GridBagLayout());
        constraints = new GridBagConstraints();

        this.game = game;
        this.player = player;
//        this.player = game.getCurrPlayer();

        cardsIndex = new ArrayList<>();

        placeCards();
        createTurnOptions();
    }

    public void placeCards() {
        //TESTING PURPOSE
        createCardsRow(0, 0, 0, 13);
        createSelectCardsRow(0, 1);
        //if playing with half deck
        if (player.getNumCards() > 13) {
            createCardsRow(0, 2, 14, player.getNumCards());
            createSelectCardsRow(0, 3);
        }
    }

    public void createCardsRow(int x, int y, int firstIndex, int lastIndex) {
        ListOfCards cards = player.getCards();
        cardsArea = new JPanel();
//        JPanel cardsArea = new JPanel();
        cardsArea.setLayout(new GridLayout(0, 13));
        constraints.gridx = x;
        constraints.gridy = y;
        add(cardsArea, constraints);
//        for (int i = 0; i < 13; i++) {
//            player.getCards().getCardsList().get(0).draw(cardsArea);
//        }
        if (cards.getSize() <= 13) {
            cards.draw(cardsArea);
        } else {
            for (int i = firstIndex; i < lastIndex; i++) {
                cards.getCard(i).draw(cardsArea);
            }
        }
    }

    public void createSelectCardsRow(int x, int y) {
        selectArea = new JPanel();
        selectArea.setLayout(new GridLayout(0,13));
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = x;
        constraints.gridy = y;
        add(selectArea, constraints);
        for (int i = 0; i < player.getNumCards(); i++) {
            createCheckBox(selectArea, i);
        }
    }

    public void createCheckBox(JPanel parent, int i) {
        JCheckBox selectCard = new JCheckBox();
        selectCard.setHorizontalAlignment(CENTER);
        selectCard.setVerticalAlignment(TOP);
        parent.add(selectCard);

        selectCard.setSelected(false);
        selectCard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectCard.isSelected()) {
                    cardsIndex.add(i);
                } else {
                    cardsIndex.remove((Integer) i);
                }
                //TESTING....
                //TODO: CLEAN UP
                System.out.println(cardsIndex.toString());
            }
        });
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
                    System.out.println(game.getCurrPlayer().equals(player));
                    if (game.getCurrPlayer().equals(player)) {
                        game.play(cardsIndex, player);
                        update();
                    } else {
                        JFrame popUp = new JFrame();
                        JOptionPane.showMessageDialog(popUp, "Not your turn!");
                    }
//                    game.play(cardsIndex);
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

    public void update() {
        if (cardsArea != null) {
            remove(cardsArea);
            remove(selectArea);
            createCardsRow(0, 0, 0, 13);
            createSelectCardsRow(0, 1);
        }
//        placeCards();
        updateUI();
    }
}
