package exception;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 21.02.13
 * Time: 13:40
 * To change this template use File | Settings | File Templates.
 */
public class ObjectAlreadyExistsException extends DAOException {


    public ObjectAlreadyExistsException(Exception e) {
        super(e);
    }

    public ObjectAlreadyExistsException() {
       super(null);
    }

    public String toString() {
        return "Such object already exist in database: " + (getException() != null ? getException().getMessage() : "");
    }
}
