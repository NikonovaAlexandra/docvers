package exception;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 19.02.13
 * Time: 9:42
 * To change this template use File | Settings | File Templates.
 */
public class BusinessException extends Exception {
    private Exception e;

    public BusinessException(Exception e) {
        this.e = e;
    }

    public Exception getException() {
        return e;
    }
}
