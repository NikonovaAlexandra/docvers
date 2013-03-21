package exception;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 21.03.13
 * Time: 13:08
 * To change this template use File | Settings | File Templates.
 */
public class MyException extends Exception {
    private Exception e;

    public MyException(Exception e) {
        this.e = e;
    }

    public Exception getException() {
        return e;
    }

    public String toString() {
        return e.getMessage();
    }

}
