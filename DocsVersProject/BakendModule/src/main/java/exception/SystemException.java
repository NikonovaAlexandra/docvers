package exception;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 19.02.13
 * Time: 9:42
 * To change this template use File | Settings | File Templates.
 */
public class SystemException extends Exception {
    private Exception e;
    private String message;

    public SystemException(String message, Exception e) {
        this.e = e;
        this.message = message;
    }

    public Exception getException() {
        return e;
    }

    public String toString() {
        return message + (e != null ? e.getMessage() : "");
    }
}
