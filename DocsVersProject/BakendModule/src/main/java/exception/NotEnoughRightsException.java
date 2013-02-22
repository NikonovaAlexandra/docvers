package exception;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 22.02.13
 * Time: 13:35
 * To change this template use File | Settings | File Templates.
 */
public class NotEnoughRightsException extends SystemException {
    public NotEnoughRightsException(String s, Exception e) {
        super(s, e);
    }
}
