package model.exceptions;

import java.awt.*;

public class TooFewCardsException extends Exception {

    public TooFewCardsException(String msg) {
        super(msg);
    }
}
