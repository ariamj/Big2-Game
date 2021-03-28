package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Abstract class for any fold helper methods use throughout project
 */
public abstract class Helper {
    public static final Dimension BUTTON_SIZE_1 = new Dimension(80, 40);
    public static final Dimension BUTTON_SIZE_2 = new Dimension(150, 50);
    public static final Font BUTTON_FONT = new Font("SignPainter", 14, 20);
    private static final Font MSG_FONT = new Font("SignPainter", 15, 30);

    public static final String YES = "Yes";
    public static final String SAVE = "Save";
    public static final String DONT_SAVE = "Don't save";
    public static final String CONFIRM = "Confirm";
    public static final String NO = "No";
    public static final String GAME_OVER = "Game Over";
    public static final String CARDS_13 = "13 cards";
    public static final String HALF_DECK = "Half Deck";
    public static final String NEW_GAME = "New Game";

    //EFFECTS: creates a button labeled text that does command on child or parent
    public static JButton createButton(String text, String command, Dimension buttonSize,
                                       JFrame child, Container parent) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
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
    //EFFECTS: executes actions from buttons according to command
    //          - command: action command of button
    //          - child: mini windows for messages
    //          - parent: the frame with all the components
    private static void executeActions(String command, JFrame child, Container parent) {
        executeStartSettings(command, child, parent);
        if (command.equals(NEW_GAME)) {
            parent.removeAll();
            new GameGUI();
            parent.setVisible(false);
        } else if (command.equals(SAVE) || command.equals(DONT_SAVE)
                || command.equals(CONFIRM) || command.equals(GAME_OVER))  {
            child.setVisible(false);
            if (command.equals(SAVE) || command.equals(DONT_SAVE)) {
                if (command.equals(SAVE)) {
                    BigTwoGameGUI.saveGameStatus();
                    showMsg("Saved game status to file: " + BigTwoGameGUI.JSON_FILE);
                }
                displayEndGame("You have quit the game", parent);
            } else if (command.equals(GAME_OVER)) {
                displayEndGame("Game Over", parent);
            }
        }
    }

    //EFFECTS: executes actions for all the buttons in GameGUI
    private static void executeStartSettings(String command, JFrame child, Container parent) {
        BigTwoGameGUI game;
        if (command.equals(YES)) {
            game = new BigTwoGameGUI(BigTwoGameGUI.LOAD_SAVED);
            parent.add(game);
            parent.setVisible(true);
            child.setVisible(false);
            showMsg("Loaded game from file: " + BigTwoGameGUI.JSON_FILE);
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
    public static void createPopUp(String msg, String buttonText1, String buttonText2,
                                   String command1, String command2, Container parent) {
        JFrame child = new JFrame();
        child.setPreferredSize(new Dimension(GameGUI.POP_UP_WIDTH, GameGUI.POP_UP_HEIGHT));
        child.getContentPane().setBackground(GameGUI.POP_UP_COLOUR);
        JLabel question = new JLabel(msg);
        question.setFont(MSG_FONT);
        question.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel buttonArea = new JPanel();
        buttonArea.setBackground(GameGUI.POP_UP_COLOUR);

        child.add(question, BorderLayout.NORTH);
        buttonArea.add(createButton(buttonText1, command1, BUTTON_SIZE_1, child, parent));
        buttonArea.add(createButton(buttonText2, command2, BUTTON_SIZE_1, child, parent));
        child.add(buttonArea, BorderLayout.SOUTH);
        child.pack();
        child.setAlwaysOnTop(true);
        GameGUI.centreOnScreen(child);
        child.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: repaint entire window of parent to display message about end of game
    private static void displayEndGame(String text, Container parent) {
        parent.removeAll();
        parent.setLayout(new FlowLayout());
        JLabel end = new JLabel(text);
        end.setFont(BigTwoGameGUI.ANNOUNCE_FONT);
        parent.add(end, BorderLayout.CENTER);
        ((JPanel) parent).updateUI();
    }

    //EFFECTS: displays a pop up message
    public static void showMsg(String msg) {
        Icon image1 = new ImageIcon("./data/images/Msg_Icon.jpg");
        JOptionPane.showMessageDialog(null, msg, null,
                JOptionPane.INFORMATION_MESSAGE, image1);
    }
}
