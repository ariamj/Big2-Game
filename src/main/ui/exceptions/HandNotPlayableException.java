package ui.exceptions;

public class HandNotPlayableException extends Exception {

    //thrown when hand chosen is not playable
    public HandNotPlayableException(String msg) {
        super(msg);
    }
}
