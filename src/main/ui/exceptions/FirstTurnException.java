package ui.exceptions;

public class FirstTurnException extends Throwable {

    //thrown when passing as a start player
    public FirstTurnException(String msg) {
        super(msg);
    }
}
