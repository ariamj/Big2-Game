package ui.gui;

import model.Player;
import ui.GameGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Represents the area where player can see their cards and play their turn
 *  ie. say play, pass, quit
 */
public class UserInteractionArea extends JPanel {
    private static final int PLAYER1_TAB_INDEX = 0;
    private static final int PLAYER2_TAB_INDEX = 1;

    public static final int WIDTH = GameGUI.WIDTH;
    public static final int HEIGHT = GameGUI.HEIGHT / 3;
    private BigTwoGameGUI game;
    private JTabbedPane sideBar;
    private PlayerTab player1Tab;
    private PlayerTab player2Tab;

    //EFFECTS: creates an area for players to interact in (display their cards, play)
    public UserInteractionArea(BigTwoGameGUI game) {
        setBackground(GameGUI.BACKGROUND);
        sideBar = new JTabbedPane();
        sideBar.setBackground(GameGUI.BACKGROUND);
        sideBar.setTabPlacement(JTabbedPane.LEFT);
        this.game = game;
        loadTabs();
        add(sideBar);
        setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: load player tabs
    private void loadTabs() {
        player1Tab = new PlayerTab(game, game.getPlayer(1));
        player2Tab = new PlayerTab(game, game.getPlayer(2));

        sideBar.add(player1Tab, PLAYER1_TAB_INDEX);
        sideBar.setTitleAt(PLAYER1_TAB_INDEX, "Player 1");
        sideBar.add(player2Tab, PLAYER2_TAB_INDEX);
        sideBar.setTitleAt(PLAYER2_TAB_INDEX, "Player 2");
    }

    //MODIFIES: this
    //EFFECTS: updates the tabs accordingly, set tab to be for current player
    public void update() {
        player1Tab.update();
        player2Tab.update();
        Player currPlayer = game.getCurrPlayer();
        if (currPlayer.equals(game.getPlayer(1))) {
            sideBar.setSelectedIndex(PLAYER1_TAB_INDEX);
            sideBar.setEnabledAt(PLAYER1_TAB_INDEX, true);
            sideBar.setEnabledAt(PLAYER2_TAB_INDEX, false);
        } else {
            sideBar.setSelectedIndex(PLAYER2_TAB_INDEX);
            sideBar.setEnabledAt(PLAYER1_TAB_INDEX, false);
            sideBar.setEnabledAt(PLAYER2_TAB_INDEX, true);
        }
        confirm(currPlayer.getName());
    }

    //EFFECTS: confirms player is current player before proceeding
    private void confirm(String name) {
        JFrame halt = new JFrame();
        halt.getContentPane().setBackground(GameGUI.POP_UP_COLOUR);
        halt.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        halt.setResizable(false);
        halt.setAlwaysOnTop(true);
        halt.setLocationRelativeTo(sideBar);
        JLabel msg = new JLabel("Confirm you are " + name);
        msg.setHorizontalAlignment(SwingConstants.CENTER);
        msg.setFont(new Font("Phosphate", 1, 32));
        halt.add(msg, BorderLayout.CENTER);
        JButton confirmB = confirmButton(halt);
        halt.add(confirmB, BorderLayout.SOUTH);
        halt.getRootPane().setDefaultButton(confirmB);
        halt.setVisible(true);
    }

    //EFFECTS: creates a button for confirmation
    private JButton confirmButton(JFrame parent) {
        JButton confirm = new JButton("Confirm");
        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.setVisible(false);
            }
        });
        return confirm;
    }
}
