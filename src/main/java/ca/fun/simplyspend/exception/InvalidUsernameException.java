package ca.fun.simplyspend.exception;

public class InvalidUsernameException extends SimplySpendCoreException{

    private final int errorCode;
    private final String message;

    public InvalidUsernameException(int errorCode, String message) {
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
