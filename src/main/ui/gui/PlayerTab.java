package ui.gui;

import exceptions.HandNotPlayableException;
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
    private JPanel cardsArea1;
    private JPanel cardsArea2;
    private JPanel selectArea1;
    private JPanel selectArea2;

    //    public PlayerTab() {
    public PlayerTab(BigTwoGameGUI game, Player player) {
        setMaximumSize(new Dimension(UserInteractionArea.WIDTH, UserInteractionArea.HEIGHT));

        setLayout(new GridBagLayout());
        constraints = new GridBagConstraints();

        this.game = game;
        this.player = player;

        cardsIndex = new ArrayList<>();

        placeCards();
        createTurnOptions();
    }

    public void placeCards() {
        //TESTING PURPOSE
        cardsArea1 = new JPanel();
        cardsArea2 = new JPanel();
        selectArea1 = new JPanel();
        selectArea2 = new JPanel();
        createCardsRow(0, 0, 0, 13, cardsArea1);
        createSelectCardsRow(0, 1, selectArea1);
        //if playing with half deck
        if (player.getNumCards() > 13) {
            createCardsRow(0, 2, 14, player.getNumCards(), cardsArea2);
//            createCardsRow(0, 2, 0, 13, cardsArea2);
            createSelectCardsRow(0, 3, selectArea2);
        }
    }

    public void createCardsRow(int x, int y, int firstIndex, int lastIndex, JPanel area) {
        ListOfCards cards = player.getCards();
//        area = new JPanel();
//        JPanel cardsArea = new JPanel();
        area.setLayout(new GridLayout(0, 13));
        constraints.gridx = x;
        constraints.gridy = y;
        add(area, constraints);
        if (cards.getSize() <= 13) {
            cards.draw(area);
        } else {
            for (int i = firstIndex; i < lastIndex; i++) {
                cards.getCard(i).draw(area);
            }
        }
    }

    public void createSelectCardsRow(int x, int y, JPanel area) {
//        selectArea1 = new JPanel();
        area.setLayout(new GridLayout(0, 13));
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = x;
        constraints.gridy = y;
        add(area, constraints);
        for (int i = 0; i < player.getNumCards(); i++) {
            createCheckBox(area, i);
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
                    } else {
                        JFrame popUp = new JFrame();
                        JOptionPane.showMessageDialog(popUp, "Not your turn!");
                    }
//                    game.play(cardsIndex);
                } catch (HandNotPlayableException he) {
                    JFrame popUp = new JFrame();
                    JOptionPane.showMessageDialog(popUp, he.getMessage());
//                    System.out.println(he.getMessage());
//                    throw new HandNotPlayableException();
                } finally {
                    update();
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
        if (cardsArea1 != null) {
            remove(cardsArea1);
        }
        if (selectArea1 != null) {
            remove(selectArea1);
        }
        if (cardsArea2 != null) {
            remove(cardsArea2);
        }
        if (selectArea2 != null) {
            remove(selectArea2);
        }
        placeCards();
        cardsIndex = new ArrayList<>();
        updateUI();
    }
}
