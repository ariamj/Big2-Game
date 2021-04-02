package ui;

import javax.swing.*;
import java.awt.*;

/**
 * Represents the framework of the Big 2 game
 */
public class GameGUI extends JFrame {
    private static final String YES = "Yes";
    private static final String NO = "No";
    public static final int WIDTH = 1300;
    public static final int HEIGHT = 700;
    public static final int POP_UP_WIDTH = 700;
    public static final int POP_UP_HEIGHT = 150;
    public static final Color BACKGROUND = new Color(130, 62, 56);
    public static final Color POP_UP_COLOUR = new Color(172, 127, 127);

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
        globalArea.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        globalArea.setBackground(BACKGROUND);
        JButton newGame = Helper.createButton("New Game", Helper.NEW_GAME, Helper.BUTTON_SIZE_2,
                null, this);
        globalArea.add(newGame);
        JButton newRound = Helper.createButton("New Round", Helper.NEW_ROUND,
                Helper.BUTTON_SIZE_2, null, this);
        globalArea.add(newRound);
        return globalArea;
    }

    //EFFECTS: ask to play a new game or loaded game form file
    public void askToLoadGame() {
        Helper.createPopUp("Do you want to load a saved game from file?", YES, NO,
                Helper.YES, Helper.NO, this);
    }

    //EFFECTS: centers parent onto screen
    public static void centreOnScreen(JFrame parent) {
        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
        parent.setLocation((scrn.width - parent.getWidth()) / 2, (scrn.height - parent.getHeight()) / 2);
    }
}
