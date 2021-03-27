package ui.gui;

import ui.GameGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Buttons {
    public static final Dimension BUTTON_SIZE_1 = new Dimension(80, 40);
    public static final Dimension BUTTON_SIZE_2 = new Dimension(150, 50);
    private static final Font BUTTON_FONT = new Font("SignPainter", 14, 20);

    public static final String YES = "yes";
    public static final String SAVE = "save";
    public static final String NO = "no";
    public static final String CANCEL = "cancel";
    public static final String GAME_OVER = "game over";
    public static final String CARDS_13 = "13 cards";
    public static final String HALF_DECK = "half deck";
    public static final String RESET = "New Game";

    private enum Actions {
        LOAD_GAME, SAVE, NO, GAME_OVER, CANCEL
    }

    public Buttons() {
        // stub
    }

    //EFFECTS: creates a button labeled msg that does action on child
    public static JButton createButton(String msg, String command, Dimension buttonSize,
                                       JFrame child, JFrame parent) {
        JButton button = new JButton(msg);
        button.setFont(GameGUI.BUTTON_FONT);
        button.setPreferredSize(buttonSize);
        button.setActionCommand(command);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executeActions(command, child, parent);
            }
        });
        return button;
    }

    //MODIFIES: this
    //EFFECTS: executes actions from buttons according to action
    public static void executeActions(String command, JFrame child, JFrame parent) {
        executeStartSettings(command, child, parent);
        if (command.equals(RESET)) {
            parent.removeAll();
            new GameGUI();
        }
//        else if (command.equals(SAVE)) {
////            saveGameStatus();
//            child.setVisible(false);
//            GameGUI.showMsg("Saved game status to file: " + BigTwoGameGUI.JSON_FILE);
////            displayEndGame("You have quit the game");
//        } else if (command.equals(NO) || command.equals(CANCEL)
//                || command.equals(GAME_OVER)) {
//            child.setVisible(false);
//            if (command.equals(GAME_OVER)) {
//                // show "has quit" text --> remove buttons and stuff
////                displayEndGame("Game Over");
//            }
//        }
    }

    private static void executeStartSettings(String command, JFrame child, JFrame parent) {
        BigTwoGameGUI game;
        if (command.equals(YES)) {
            game = new BigTwoGameGUI(BigTwoGameGUI.LOAD_SAVED);
            parent.add(game);
            parent.setVisible(true);
            child.setVisible(false);
            GameGUI.showMsg("Loaded game from file: " + BigTwoGameGUI.JSON_FILE);
        } else if (command.equals(NO)) {
            createPopUp("How many cards would you like to start with?",
                    CARDS_13, HALF_DECK, CARDS_13, HALF_DECK, parent);
            child.setVisible(false);
        } else if (command.equals(CARDS_13) || command.equals(HALF_DECK)) {
            if (command.equals(CARDS_13)) {
                game = new BigTwoGameGUI(BigTwoGameGUI.NEW_GAME_13);
            } else {
                game = new BigTwoGameGUI(BigTwoGameGUI.NEW_GAME_HALF_DECK);
            }
            parent.add(game);
            parent.setVisible(true);
            child.setVisible(false);
        }
    }

    //EFFECTS: creates a pop up window displaying a message with options
    public static void createPopUp(String msg, String button1Text, String button2Text,
                                   String command1, String command2, JFrame parent) {
        JFrame child = new JFrame();
        child.setPreferredSize(new Dimension(GameGUI.POP_UP_WIDTH, GameGUI.POP_UP_HEIGHT));
        JLabel question = new JLabel(msg);
        question.setFont(new Font("Times New Roman", 14, 18));
        question.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel buttonArea = new JPanel();

        child.add(question, BorderLayout.NORTH);
        buttonArea.add(Buttons.createButton(button1Text, command1, BUTTON_SIZE_1, child, parent));
        buttonArea.add(Buttons.createButton(button2Text, command2, BUTTON_SIZE_1, child, parent));
        child.add(buttonArea, BorderLayout.SOUTH);
        child.pack();
        child.setAlwaysOnTop(true);
        GameGUI.centreOnScreen(child);
        child.setVisible(true);
    }
}
