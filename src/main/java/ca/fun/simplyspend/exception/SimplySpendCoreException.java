package ca.fun.simplyspend.exception;

public class SimplySpendCoreException extends RuntimeException {

    private final int errorCode;
    private final String message;

    public SimplySpendCoreException(int errorCode, String message) {
        super(message);
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
