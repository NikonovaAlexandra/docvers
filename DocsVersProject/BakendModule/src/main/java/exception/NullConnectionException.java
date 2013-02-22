package exception;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 21.02.13
 * Time: 13:21
 * To change this template use File | Settings | File Templates.
 */
public class NullConnectionException extends DAOException {
    private Exception e;

    public NullConnectionException(Exception e) {
        this.e = e;
    }

    public NullConnectionException() {
        this.e = null;
    }

    public String toString() {
        return "Could not connect to the database, or connection was lost." +
                " Possible reasons are: the database server is not running at the given port, " +
                "the connection was closed due to a shutdown, or the server was stopped. " +
                "Other possible causes are: the server is not an H2 server, or the network connection is broken." +
                (e != null ? e.getMessage() : "");
    }
}
