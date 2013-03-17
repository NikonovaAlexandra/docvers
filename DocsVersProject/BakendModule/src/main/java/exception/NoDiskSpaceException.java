package exception;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 22.02.13
 * Time: 13:36
 * To change this template use File | Settings | File Templates.
 */
public class NoDiskSpaceException extends SystemException {

    public NoDiskSpaceException(Exception e) {
        super("No disk space available", e);
    }
}
