package model;

import org.json.JSONObject;
import persistence.Writable;

import javax.swing.*;
import java.awt.*;

/**
 * Represents a card with a rank number and suit
 */
public class Card implements Writable {

    private int rank;
    private String suit;

    //EFFECTS: makes a new card with a rank and suit
    //          - rank ranges in [1,13]
    //          - suit is one of: diamond, clubs, heart, spade
    public Card(int rank, String suit) {
        this.rank = rank;
        this.suit = suit.toLowerCase();
    }

    //EFFECTS: returns true if this and card are equal (same rank & same suit)
    public boolean equalCards(Card card) {
        return (this.rank == card.rank) && (this.suit.equals(card.suit));
    }

    //getters
    public int getRank() {
        return this.rank;
    }

    //getters
    public String getSuit() {
        return this.suit;
    }

    //EFFECTS: Returns a string representation of a card in form (rank)(suit letter/number)
    public String toString() {
        String cardString = "";
        if (rank >= 2 && rank <= 10) {
            cardString += String.valueOf(rank);
        } else if (rank == 11) {
            cardString += "J";
        } else if (rank == 12) {
            cardString += "Q";
        } else if (rank == 13) {
            cardString += "K";
        } else {
            cardString += "A";
        }
        if (suit.equalsIgnoreCase("diamond")) {
            cardString += "D";
        } else if (suit.equalsIgnoreCase("clubs")) {
            cardString += "C";
        } else if (suit.equalsIgnoreCase("heart")) {
            cardString += "H";
        } else {
            cardString += "S";
        }
        return cardString;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("rank", rank);
        json.put("suit", suit);
        return json;
    }

//    //TODO: GUI CLEAN UP
//    public void draw(Graphics g) {
//        g.setColor(new Color(9, 98, 125));
//        g.drawRect(BigTwoGame.WIDTH / 2, BigTwoGame.HEIGHT / 2, 10, 100);
////        g.setColor(new Color(9, 98, 125));
//    }

    //TODO: GUI CLEAN UP
//    public void draw() {
    public void draw(JPanel parent) {
        // stub
        // load images
        String sep = System.getProperty("file.separator");
        ImageIcon trial = new ImageIcon(System.getProperty("user.dir") + sep
                + "images" + sep + "AD.jpg");
        JLabel imageAsLabel75 = new JLabel(trial);
//        for (int i = 0; i < 13; i++) {
//            parent.add(imageAsLabel75);
//        }
        parent.add(imageAsLabel75);
//        trial = new ImageIcon(System.getProperty("user.dir") + sep
//                + "images" + sep + "50x.jpg");
//        JLabel imageAsLabel50 = new JLabel(trial);
//        parent.add(imageAsLabel50);
    }

}
