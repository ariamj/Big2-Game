package ui;

import ui.gui.BigTwoGameGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameGUI extends JFrame {
    private static final String YES = "Yes";
    private static final String NO = "No";
    private static final String CARDS_13 = "13 cards";
    private static final String HALF_DECK = "Half Deck";
    public static final int WIDTH = 1100;
    public static final int HEIGHT = 700;
    public static final Color BACKGROUND = new Color(130, 62, 56);

    private BigTwoGameGUI game;

    //EFFECTS: set up which game window to operate; saved or new game
    public GameGUI() {
        super("Big Two");
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        askToLoadGame();
        pack();
        centreOnScreen(this);
    }

    //EFFECTS: ask to play a new game or loaded game form file
    public void askToLoadGame() {
        createPopUp("Do you want to load a saved game from file?", YES, NO);
    }

    public void createPopUp(String text, String button1Text, String button2Text) {
        JFrame parent = new JFrame();
        parent.setPreferredSize(new Dimension(500, 150));
        JLabel question = new JLabel(text);
        question.setFont(new Font("Times New Roman", 14, 18));

        JPanel buttonArea = new JPanel();

        parent.add(question, BorderLayout.NORTH);
        buttonArea.add(createButton(button1Text, parent));
        buttonArea.add(createButton(button2Text, parent));
        parent.add(buttonArea, BorderLayout.SOUTH);
        parent.pack();
        parent.setAlwaysOnTop(true);
        centreOnScreen(parent);
        parent.setVisible(true);
    }

    private JButton createButton(String text, JFrame parent) {
        JButton button = new JButton(text);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executeAction(text, parent);
            }
        });
        return button;
    }

    private void executeAction(String text, JFrame parent) {
        if (text.equals(YES)) {
            game = new BigTwoGameGUI(BigTwoGameGUI.LOAD_SAVED);
            add(game);
            setVisible(true);
            parent.setVisible(false);
            JFrame popUp = new JFrame();
            JOptionPane.showMessageDialog(popUp, "Loaded game from file: " + BigTwoGameGUI.JSON_FILE);
        } else if (text.equals(NO)) {
            createPopUp("How many cards would you like to start with?", CARDS_13, HALF_DECK);
//            game = new BigTwoGameGUI(BigTwoGameGUI.NEW_GAME_13);
//            add(game);
//            setVisible(true);
            parent.setVisible(false);
        } else {
            if (text.equals(CARDS_13)) {
                game = new BigTwoGameGUI(BigTwoGameGUI.NEW_GAME_13);
            } else {
                game = new BigTwoGameGUI(BigTwoGameGUI.NEW_GAME_HALF_DECK);
            }
            add(game);
            setVisible(true);
            parent.setVisible(false);
        }
    }

    private void centreOnScreen(JFrame parent) {
        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
        parent.setLocation((scrn.width - parent.getWidth()) / 2, (scrn.height - parent.getHeight()) / 2);
    }
}
