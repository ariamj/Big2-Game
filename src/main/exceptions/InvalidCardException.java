package exceptions;

public class InvalidCardException extends Exception {

    public InvalidCardException() {

    }

    public InvalidCardException(String msg) {
        super(msg);
    }
}
