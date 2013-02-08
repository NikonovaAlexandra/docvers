package exception;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 08.02.13
 * Time: 13:01
 * To change this template use File | Settings | File Templates.
 */
public class NullConnectionException extends Exception {

    private Class exceptionClass;

    public NullConnectionException(Class exceptionClass) {

        this.exceptionClass = exceptionClass;

    }

    public String toString() {

        return "NullConnectionException in class " + exceptionClass ;

    }

}
