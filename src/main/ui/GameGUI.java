package ui;

import ui.gui.BigTwoGameGUI;
import ui.gui.Buttons;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Represents the framework of the Big 2 game
 */
public class GameGUI extends JFrame {
    private static final String YES = "Yes";
    private static final String NO = "No";
    public static final String CARDS_13 = "13 cards";
    public static final String HALF_DECK = "Half Deck";
    public static final int WIDTH = 1100;
    public static final int HEIGHT = 700;
    public static final int POP_UP_WIDTH = 700;
    public static final int POP_UP_HEIGHT = 150;
    public static final Color BACKGROUND = new Color(130, 62, 56);
    public static final Color POP_UP_COLOUR = new Color(172, 127, 127);
    public static final Font BUTTON_FONT = new Font("SignPainter", 14, 20);

    private BigTwoGameGUI game;

    //EFFECTS: set up which game window to operate; saved or new game
    public GameGUI() {
        super("Big Two");
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        askToLoadGame();
        add(newGame(), BorderLayout.PAGE_END);
        pack();
        centreOnScreen(this);
    }

    //MODIFIES: this
    //EFFECTS: creates a button to make a new game
    private JPanel newGame() {
        JPanel globalArea = new JPanel();
        FlowLayout layout = new FlowLayout();
        layout.setAlignment(FlowLayout.RIGHT);
        globalArea.setLayout(layout);
        globalArea.setBackground(BACKGROUND);
//        JButton newGame = new JButton("New Game");
//        newGame.setPreferredSize(new Dimension(150, 50));
//        newGame.setFont(BUTTON_FONT);
//        newGame.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                removeAll();
//                new GameGUI();
//            }
//        });

        JButton newGame = Buttons.createButton("New Game", Buttons.RESET, Buttons.BUTTON_SIZE_2,
                null, this);

        globalArea.add(newGame);
        return globalArea;
    }

    //EFFECTS: ask to play a new game or loaded game form file
    public void askToLoadGame() {
        Buttons.createPopUp("Do you want to load a saved game from file?", YES, NO,
                Buttons.YES, Buttons.NO, this);
//        createPopUp("Do you want to load a saved game from file?", YES, NO);
    }

//    //EFFECTS: creates a pop up window displaying a message with options
//    public void createPopUp(String msg, String button1Text, String button2Text) {
//        JFrame parent = new JFrame();
//        parent.setPreferredSize(new Dimension(POP_UP_WIDTH, POP_UP_HEIGHT));
//        JLabel question = new JLabel(msg);
//        question.setFont(new Font("Times New Roman", 14, 18));
//        question.setHorizontalAlignment(SwingConstants.CENTER);
//
//        JPanel buttonArea = new JPanel();
//
//        parent.add(question, BorderLayout.NORTH);
//        buttonArea.add(createButton(button1Text, parent));
//        buttonArea.add(createButton(button2Text, parent));
//
////        buttonArea.add(Buttons.createButton(button1Text, command1, parent, thisClass()));
////        buttonArea.add(Buttons.createButton(button2Text, command2, parent, this));
//
//        parent.add(buttonArea, BorderLayout.SOUTH);
//        parent.pack();
//        parent.setAlwaysOnTop(true);
//        centreOnScreen(parent);
//        parent.setVisible(true);
//    }
//
//    //EFFECTS: creates a JButton on parent
//    private JButton createButton(String text, JFrame parent) {
//        JButton button = new JButton(text);
//        button.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                //TODO: FIX
//                Buttons.executeActions(Buttons.YES, parent, thisClass());
//                executeAction(text, parent);
//            }
//        });
//        return button;
//    }
//
//    private GameGUI thisClass() {
//        return this;
//    }
//
//    //EFFECTS: executes the action specified by button name
//    private void executeAction(String text, JFrame parent) {
//        if (text.equals(YES)) {
//            game = new BigTwoGameGUI(BigTwoGameGUI.LOAD_SAVED);
//            add(game);
//            setVisible(true);
//            parent.setVisible(false);
//            showMsg("Loaded game from file: " + BigTwoGameGUI.JSON_FILE);
//        } else if (text.equals(NO)) {
//            createPopUp("How many cards would you like to start with?", CARDS_13, HALF_DECK);
//            parent.setVisible(false);
//        } else {
//            if (text.equals(CARDS_13)) {
//                game = new BigTwoGameGUI(BigTwoGameGUI.NEW_GAME_13);
//            } else {
//                game = new BigTwoGameGUI(BigTwoGameGUI.NEW_GAME_HALF_DECK);
//            }
//            add(game);
//            setVisible(true);
//            parent.setVisible(false);
//        }
//    }

    //EFFECTS: centers parent onto screen
    public static void centreOnScreen(JFrame parent) {
        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
        parent.setLocation((scrn.width - parent.getWidth()) / 2, (scrn.height - parent.getHeight()) / 2);
    }

    //EFFECTS: displays a pop up message
    public static void showMsg(String msg) {
        Icon image1 = new ImageIcon("./data/images/Msg_Icon.jpg");
        JOptionPane.showMessageDialog(null, msg, null,
                JOptionPane.INFORMATION_MESSAGE, image1);
    }
}
