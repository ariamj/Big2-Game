package ui.gui;

import model.Card;
import model.ListOfCards;

import javax.swing.*;

/**
 * Represents the graphic visual of cards
 */
public class CardsGUI {

    //MODIFIES: parent
    //EFFECTS: draws the list of cards onto parent
    public static void drawCardList(JPanel parent, ListOfCards listOfCards) {
        for (Card card : listOfCards.getListOfCards()) {
            drawCard(parent, card);
        }
    }

    //MODIFIES: parent
    //EFFECTS: draws the card as an image onto parent
    public static void drawCard(JPanel parent, Card card) {
        ImageIcon trial = new ImageIcon("./data/images/" + card.toString() + ".jpg");
        JLabel imageAsLabel75 = new JLabel(trial);
        parent.add(imageAsLabel75);
    }
}
