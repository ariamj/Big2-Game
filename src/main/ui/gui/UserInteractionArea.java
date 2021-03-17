package ui.gui;

import ui.GameGUI;

import javax.swing.*;
import java.awt.*;

public class UserInteractionArea extends JPanel {
    private static final int PLAYER1_TAB_INDEX = 0;
    private static final int PLAYER2_TAB_INDEX = 1;

    public static final int WIDTH = GameGUI.WIDTH;
    public static final int HEIGHT = GameGUI.HEIGHT / 3;
    private BigTwoGameGUI game;
    private JTabbedPane sideBar;

    public UserInteractionArea(BigTwoGameGUI game) {
//        super("Player Cards");
//        setSize(new Dimension(WIDTH, HEIGHT));
//        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        sideBar = new JTabbedPane();
        sideBar.setTabPlacement(JTabbedPane.LEFT);

//        game = new BigTwoGameGUI();
        this.game = game;

        loadTabs();
        add(sideBar);
        setVisible(true);
    }

    public void loadTabs() {
//        JPanel player1Tab = new PlayerTab();
//        JPanel player2Tab = new PlayerTab();
        JPanel player1Tab = new PlayerTab(game, game.getPlayer(1));
        JPanel player2Tab = new PlayerTab(game, game.getPlayer(2));

        sideBar.add(player1Tab, PLAYER1_TAB_INDEX);
        sideBar.setTitleAt(PLAYER1_TAB_INDEX, "Player 1");
        sideBar.add(player2Tab, PLAYER2_TAB_INDEX);
        sideBar.setTitleAt(PLAYER2_TAB_INDEX, "Player 2");
    }

    public static void main(String[] args) {
        new UserInteractionArea(new BigTwoGameGUI());
    }
}
