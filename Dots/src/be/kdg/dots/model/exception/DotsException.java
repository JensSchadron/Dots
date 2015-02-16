package be.kdg.dots.model.exception;

/**
 * Created by Jens on 5-2-2015.
 */
public class DotsException extends Exception {
    public DotsException() {
    }

    public DotsException(Throwable cause) {
        super(cause);
    }

    public DotsException(String message) {
        super(message);
    }

    public DotsException(String message, Throwable cause) {
        super(message, cause);
    }

    public DotsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
