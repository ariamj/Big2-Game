package ui.gui;

import model.Card;
import model.ListOfCards;
import model.Player;
import ui.GameGUI;
import ui.exceptions.FirstTurnException;
import ui.exceptions.HandNotPlayableException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player tab for each individual player
 */
public class PlayerTab extends JPanel {
    private List<Card> cardList;
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
        cardList = new ArrayList<>();
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
        if (player.getNumCards() > 13) {
            createCardsRow(0, 2, 13, player.getNumCards(), cardsArea2);
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
            for (Card card : cards.getListOfCards()) {
                area.add(createCardButton(card));
            }
        } else {
            for (int i = firstIndex; i < lastIndex; i++) {
                area.add(createCardButton(cards.getCard(i)));
            }
        }
    }

    //EFFECTS: create buttons to pass, play, and quit
    private void createTurnOptions() {
        JPanel buttonsArea = new JPanel();
        buttonsArea.setBackground(GameGUI.BACKGROUND);
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

    //EFFECTS: creates and returns a card as a button;
    //          - if isSelected(), add card to cardList
    //          - else: remove from cardList
    private JButton createCardButton(Card card) {
        Icon cardImg = new ImageIcon("./data/images/cards/" + card.toString() + ".jpg");
        JButton button = new JButton();
        button.setIcon(cardImg);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (button.getIcon().equals(cardImg)) {
                    button.setIcon(new ImageIcon("./data/images/selectedCards/" + card.toString()
                            + "_S.jpg"));
                    cardList.add(card);
                } else {
                    button.setIcon(cardImg);
                    cardList.remove(card);
                }
            }
        });
        return button;
    }

    //==========================================================================================

    //EFFECTS: creates and returns a button to pass
    private JButton addPassButton() {
        JButton passButton = new JButton("pass");
        passButton.setFont(Helper.BUTTON_FONT);
        passButton.setPreferredSize(Helper.BUTTON_SIZE_1);
        passButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    game.pass();
                } catch (FirstTurnException fe) {
                    Helper.showMsg(fe.getMessage());
                }
            }
        });
        return passButton;
    }

    //EFFECTS: creates and returns a button to play
    //          - when clicked, update tab
    //          - if picked hand is not playable, display message
    private JButton addPlayButton() {
        JButton playButton = new JButton("play");
        playButton.setFont(Helper.BUTTON_FONT);
        playButton.setPreferredSize(Helper.BUTTON_SIZE_1);
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    game.play(cardList, player);
                } catch (HandNotPlayableException he) {
                    Helper.showMsg(he.getMessage());
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
        quitButton.setFont(Helper.BUTTON_FONT);
        quitButton.setPreferredSize(Helper.BUTTON_SIZE_1);
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
        cardList = new ArrayList<>();
    }
}
