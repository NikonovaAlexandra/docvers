package exception;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 21.02.13
 * Time: 13:30
 * To change this template use File | Settings | File Templates.
 */
public class DAOException extends BusinessException {
    private Exception e;

    public DAOException() {
    }

    public DAOException(Exception e) {
        this.e = e;
    }

    public String toString() {
        return "Exception while access database " + (e != null ? e.getMessage() : "");
    }
}
