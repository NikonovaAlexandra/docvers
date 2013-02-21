package exception;

import java.lang.reflect.Method;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 19.02.13
 * Time: 10:07
 * To change this template use File | Settings | File Templates.
 */
public class IntegrityConstraintException extends SQLException {
    private SQLException e;

    public IntegrityConstraintException( SQLException e ) {

        this.e = e;
    }

    public String toString() {

        return "Integrity Constraint Exception: "+e.getMessage() ;

    }
}
