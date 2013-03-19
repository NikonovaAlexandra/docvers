package exception;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 21.02.13
 * Time: 13:30
 * To change this template use File | Settings | File Templates.
 */
public class DAOException extends BusinessException {

    public DAOException() {
        super(null);
    }

    public DAOException(Exception e) {
       super(e);
    }

    public String toString() {
        return "Exception while access database: " + (getException() != null ? getException().getMessage() : "");
    }

    public String getMessage() {
        return "Exception while access database";
    }
}
