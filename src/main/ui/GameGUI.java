package ui;

import ui.gui.BigTwoGameGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameGUI extends JFrame {
    private static final String YES = "Yes";
    private static final String NO = "No";
    public static final int WIDTH = 1100;
    public static final int HEIGHT = 700;

    private BigTwoGameGUI game;

    public GameGUI() {
        super("Big Two");
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        askToLoadGame();

//        JPanel chipsGUI = new ChipsDrawerGUI(game);
//        add(chipsGUI, BorderLayout.EAST);
//
//        JPanel tableGUI = new TablePileGUI(game);
//        add(tableGUI, BorderLayout.CENTER);
//
//        JPanel interaction = new UserInteractionArea(game);
//        add(interaction, BorderLayout.SOUTH);
        pack();
        centreOnScreen(this);
//        setVisible(true);
    }

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
        } else {
            game = new BigTwoGameGUI(BigTwoGameGUI.NEW_GAME);
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
