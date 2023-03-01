package task.exception;

public class TransactionException extends RuntimeException{
    private final String msg;

    public TransactionException(String msg) {
        this.msg = msg;
    }

    public String getMessage() {
        return msg;
    }
}
