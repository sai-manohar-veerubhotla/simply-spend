package ca.fun.simplyspend.exception;

public class InvalidUserPasswordException extends SimplySpendCoreException {

    private final int errorCode;
    private final String message;
    public InvalidUserPasswordException(int errorCode, String message) {
        super(errorCode, message);
        this.errorCode = errorCode;
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}
