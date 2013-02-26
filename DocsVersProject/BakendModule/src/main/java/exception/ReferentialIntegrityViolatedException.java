package exception;

import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 19.02.13
 * Time: 10:07
 * To change this template use File | Settings | File Templates.
 */
public class ReferentialIntegrityViolatedException extends DAOException {
    private SQLException e;

    public ReferentialIntegrityViolatedException(SQLException e) {
        super(e);
        this.e = e;
    }

    public ReferentialIntegrityViolatedException() {
        this.e = null;
    }

    public String toString() {

        return "Referential Integrity Constraint Violated: trying to insert or update a row " +
                "that would violate a referential constraint, because the referenced row does " +
                "not exist: " + (e != null ? e.getMessage() : "");

    }
}
