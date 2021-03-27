package ui.gui;

import model.ListOfCards;
import model.Player;
import ui.GameGUI;
import ui.exceptions.HandNotPlayableException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.SwingConstants.CENTER;
import static javax.swing.SwingConstants.TOP;

/**
 * Represents a player tab for each individual player
 */
public class PlayerTab extends JPanel {
    public static final Dimension BUTTON_SIZE = new Dimension(80, 40);
    private List<Integer> cardsIndex;
    private BigTwoGameGUI game;
    private Player player;

    private GridBagConstraints constraints;
    private JPanel cardsArea1;
    private JPanel cardsArea2;
    private JPanel selectArea1;
    private JPanel selectArea2;

    //EFFECTS: creates a panel for player
    public PlayerTab(BigTwoGameGUI game, Player player) {
        setMaximumSize(new Dimension(UserInteractionArea.WIDTH, UserInteractionArea.HEIGHT));
        setBackground(GameGUI.BACKGROUND);
        setLayout(new GridBagLayout());
        constraints = new GridBagConstraints();
        this.game = game;
        this.player = player;
        cardsIndex = new ArrayList<>();
        placeCards();
        createTurnOptions();
    }

    //MODIFIES: this
    //EFFECTS: place player's cards on display with a checkbos associating with each card
    private void placeCards() {
        cardsArea1 = new JPanel();
        cardsArea2 = new JPanel();
        selectArea1 = new JPanel();
        selectArea2 = new JPanel();
        createCardsRow(0, 0, 0, 13, cardsArea1);
        createSelectCardsRow(0, 1, 0, 13, selectArea1);
        if (player.getNumCards() > 13) {
            createCardsRow(0, 2, 13, player.getNumCards(), cardsArea2);
            createSelectCardsRow(0, 3, 13, player.getNumCards(), selectArea2);
        }
    }

    //EFFECTS: creates a row of cards on display
    private void createCardsRow(int x, int y, int firstIndex, int lastIndex, JPanel area) {
        area.setBackground(GameGUI.BACKGROUND);
        ListOfCards cards = player.getCards();
        area.setLayout(new GridLayout(0, 13));
        constraints.gridx = x;
        constraints.gridy = y;
        add(area, constraints);
        if (cards.getSize() <= 13) {
            CardsGUI.drawCardList(area, cards);
        } else {
            for (int i = firstIndex; i < lastIndex; i++) {
                CardsGUI.drawCard(area, cards.getCard(i));
            }
        }
    }

    //EFFECTS: creates a row of checkboxes for the cards
    private void createSelectCardsRow(int x, int y, int firstIndex, int lastIndex, JPanel area) {
        area.setBackground(GameGUI.BACKGROUND);
        area.setLayout(new GridLayout(0, 13));
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = x;
        constraints.gridy = y;
        add(area, constraints);
        if (player.getNumCards() <= 13) {
            for (int i = 0; i < player.getNumCards(); i++) {
                createCheckBox(area, i);
            }
        } else {
            for (int i = firstIndex; i < lastIndex; i++) {
                createCheckBox(area, i);
            }
        }
    }

    //EFFECTS: creates a single checkbox
    private void createCheckBox(JPanel parent, int i) {
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
                    cardsIndex.remove(i);
                }
            }
        });
    }

    //EFFECTS: create buttons to pass, play, and quit
    private void createTurnOptions() {
        JPanel buttonsArea = new JPanel();
        buttonsArea.setBackground(GameGUI.BACKGROUND);

//        FlowLayout layout = new FlowLayout(FlowLayout.CENTER);
//        layout.setHgap(10);
//        buttonsArea.setLayout(layout);

        buttonsArea.setLayout(new FlowLayout(FlowLayout.CENTER));
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.ipady = 1;
        add(buttonsArea, constraints);

        buttonsArea.add(addPassButton());
        buttonsArea.add(addPlayButton());
        buttonsArea.add(addQuitButton());

        buttonsArea.add(addIconButtonTest());
        buttonsArea.add(addIconButtonTest());
    }

    //MAKING BUTTON AS ICON!!!
    //TODO: TESTING
    private JButton addIconButtonTest() {
        String sep = System.getProperty("file.separator");
        Icon image1 = new ImageIcon("./data/images/KS.jpg");

        JButton button = new JButton();
        button.setIcon(image1);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (button.getIcon().equals(image1)) {
                    button.setIcon(new ImageIcon("./data/images/KS_Selected.jpg"));
                } else {
                    button.setIcon(image1);
                }
            }
        });
        return button;
    }

    //EFFECTS: creates and returns a button to pass
    private JButton addPassButton() {
        JButton passButton = new JButton("pass");
        passButton.setFont(GameGUI.BUTTON_FONT);
        passButton.setPreferredSize(BUTTON_SIZE);
        passButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.pass();
            }
        });
        return passButton;
    }

    //EFFECTS: creates and returns a button to play
    //          - when clicked, update tab
    //          - if picked hand is not playable, display message
    private JButton addPlayButton() {
        JButton playButton = new JButton("play");
        playButton.setFont(GameGUI.BUTTON_FONT);
        playButton.setPreferredSize(BUTTON_SIZE);
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    game.play(cardsIndex, player);
                } catch (HandNotPlayableException he) {
                    GameGUI.showMsg(he.getMessage());
                } finally {
                    update();
                }
            }
        });
        return playButton;
    }

    //EFFECTS: creates and returns a button to quit
    private JButton addQuitButton() {
        JButton quitButton = new JButton("quit");
        quitButton.setFont(GameGUI.BUTTON_FONT);
        quitButton.setPreferredSize(BUTTON_SIZE);
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.quit();
            }
        });
        return quitButton;
    }

    //MODIFIES: this
    //EFFECTS: updates the tab
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
    }
}
