package ui;

import model.Card;
import model.ListOfCards;
import model.Player;
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
    private static final String PASS = "pass";
    private static final String PLAY = "play";
    private static final String QUIT = "quit";
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
        buttonsArea.add(createOptionButton(PASS));
        buttonsArea.add(createOptionButton(PLAY));
        buttonsArea.add(createOptionButton(QUIT));
    }

    //EFFECTS: creates and returns one of the option buttons
    private JButton createOptionButton(String command) {
        JButton button = new JButton(command);
        button.setFont(Helper.BUTTON_FONT);
        button.setPreferredSize(Helper.BUTTON_SIZE_1);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executeActions(command);
            }
        });
        return button;
    }

    //EFFECTS: executes action of option buttons
    private void executeActions(String command) {
        if (command.equals(PASS)) {
            try {
                game.pass();
            } catch (FirstTurnException fe) {
                Helper.showMsg(fe.getMessage());
            }
        } else if (command.equals(PLAY)) {
            try {
                game.play(cardList, player);
            } catch (HandNotPlayableException he) {
                Helper.showMsg(he.getMessage());
            } finally {
                update();
            }
        } else if (command.equals(QUIT)) {
            game.quit();
        }
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
