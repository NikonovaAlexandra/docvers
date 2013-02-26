package exception;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 21.02.13
 * Time: 13:40
 * To change this template use File | Settings | File Templates.
 */
public class ObjectAlreadyExistsException extends DAOException {
    private Exception e;

    public ObjectAlreadyExistsException(Exception e) {
        super(e);
        this.e = e;
    }

    public ObjectAlreadyExistsException() {
        this.e = null;
    }

    public String toString() {
        return "Such object already exist in database: " + (e != null ? e.getMessage() : "");
    }
}
