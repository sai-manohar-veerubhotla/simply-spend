package ca.fun.simplyspend.exception;

public class InvalidUserEmailException extends SimplySpendCoreException {

    private final int errorCode;
    private final String message;
    public InvalidUserEmailException(int errorCode, String message) {
        super(errorCode, message);
        this.errorCode = errorCode;
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
