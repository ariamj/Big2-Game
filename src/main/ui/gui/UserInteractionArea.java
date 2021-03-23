package ui.gui;

import com.sun.xml.internal.fastinfoset.algorithm.HexadecimalEncodingAlgorithm;
import model.Player;
import ui.GameGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static ui.gui.BigTwoGameGUI.MSG_FONT;

public class UserInteractionArea extends JPanel {
    private static final int PLAYER1_TAB_INDEX = 0;
    private static final int PLAYER2_TAB_INDEX = 1;

    public static final int WIDTH = GameGUI.WIDTH;
    public static final int HEIGHT = GameGUI.HEIGHT / 3;
    private BigTwoGameGUI game;
    private JTabbedPane sideBar;

    private PlayerTab player1Tab;
    private PlayerTab player2Tab;

    public UserInteractionArea(BigTwoGameGUI game) {
//        super("Player Cards");
//        setSize(new Dimension(WIDTH, HEIGHT));
//        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBackground(GameGUI.BACKGROUND);
        sideBar = new JTabbedPane();
        sideBar.setBackground(GameGUI.BACKGROUND);
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
        player1Tab = new PlayerTab(game, game.getPlayer(1));
        player2Tab = new PlayerTab(game, game.getPlayer(2));

        sideBar.add(player1Tab, PLAYER1_TAB_INDEX);
        sideBar.setTitleAt(PLAYER1_TAB_INDEX, "Player 1");
        sideBar.add(player2Tab, PLAYER2_TAB_INDEX);
        sideBar.setTitleAt(PLAYER2_TAB_INDEX, "Player 2");
    }

    public void update() {
        player1Tab.update();
        player2Tab.update();

        Player currPlayer = game.getCurrPlayer();
//        confirm(currPlayer.getName());

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

    public void confirm(String name) {
        JFrame halt = new JFrame();
        halt.getContentPane().setBackground(GameGUI.BACKGROUND);
        halt.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        halt.setResizable(false);
        halt.setAlwaysOnTop(true);
        halt.setLocationRelativeTo(sideBar);
        JLabel msg = new JLabel("Confirm you are " + name);
        msg.setHorizontalAlignment(SwingConstants.CENTER);
        msg.setFont(new Font("Phosphate", 1, 32));
        halt.add(msg, BorderLayout.CENTER);
//        msg.setBackground(GameGUI.BACKGROUND);
        halt.add(confirmButton(halt), BorderLayout.SOUTH);
        halt.setVisible(true);
    }

    public JButton confirmButton(JFrame parent) {
        JButton confirm = new JButton("Confirm");
        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.setVisible(false);
            }
        });
        return confirm;
    }

//    public static void main(String[] args) {
//        new UserInteractionArea(new BigTwoGameGUI());
//    }
}
