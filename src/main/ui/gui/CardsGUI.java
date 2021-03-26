package ui.gui;

import model.Card;
import model.ListOfCards;

import javax.swing.*;

public class CardsGUI {

    //TODO: GUI STUFF TO CLEAN UP
    public void drawCardList(JPanel parent, ListOfCards listOfCards) {
        for (Card card : listOfCards.getListOfCards()) {
            drawCard(parent, card);
        }
    }

    //TODO: GUI CLEAN UP
    public void drawCard(JPanel parent, Card card) {
        ImageIcon trial = new ImageIcon("./data/images/" + card.toString() + ".jpg");
        JLabel imageAsLabel75 = new JLabel(trial);
        parent.add(imageAsLabel75);
    }
}
