package exception;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 19.02.13
 * Time: 9:42
 * To change this template use File | Settings | File Templates.
 */
public class SystemException extends MyException {
    private String message;

    public SystemException(String message, Exception e) {
        super(e);
        this.message = message;
    }

    public String toString() {
        return message + (getException() != null ? getException().getMessage() : "");
    }
}
