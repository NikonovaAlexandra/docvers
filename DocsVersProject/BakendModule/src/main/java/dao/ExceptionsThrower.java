package dao;

import exception.*;
import org.h2.constant.ErrorCode;
import org.h2.jdbc.JdbcBatchUpdateException;
import org.h2.jdbc.JdbcSQLException;
import org.hibernate.SessionException;
import org.hibernate.exception.ExceptionUtils;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 20.03.13
 * Time: 9:52
 * To change this template use File | Settings | File Templates.
 */
public class ExceptionsThrower {

    public static void throwException(Exception e) throws NullConnectionException, NotEnoughRightsException, NoDiskSpaceException, DAOException {

        if (ExceptionUtils.getCause(e) instanceof JdbcSQLException) {
            e = (JdbcSQLException) ExceptionUtils.getCause(e);
            if (((JdbcSQLException) e).getErrorCode() == ErrorCode.CONNECTION_BROKEN_1)
                throw new NullConnectionException(e);
            if (((JdbcSQLException) e).getErrorCode() == ErrorCode.NOT_ENOUGH_RIGHTS_FOR_1) {
                throw new NotEnoughRightsException(e);
            }
            if (((JdbcSQLException) e).getErrorCode() == ErrorCode.NO_DISK_SPACE_AVAILABLE) {
                throw new NoDiskSpaceException(e);
            } else throw new DAOException(e);
        }

        if (ExceptionUtils.getCause(e) instanceof JdbcBatchUpdateException) {
            e = (JdbcBatchUpdateException) ExceptionUtils.getCause(e);
            if (((JdbcBatchUpdateException) e).getErrorCode() == ErrorCode.DUPLICATE_KEY_1) {
                throw new ObjectAlreadyExistsException();
            }
            if (((JdbcBatchUpdateException) e).getErrorCode() == ErrorCode.NULL_NOT_ALLOWED) {
                throw new IntegrityConstraintException(e, "NULL is not allowed");
            }
            if (((JdbcBatchUpdateException) e).getErrorCode() == ErrorCode.REFERENTIAL_INTEGRITY_VIOLATED_PARENT_MISSING_1) {
                throw new ReferentialIntegrityViolatedException();
            } else throw new DAOException(e);

        }
        if (e instanceof SessionException) {
            throw new NullConnectionException(e);
        }
        if (e instanceof NoSuchObjectInDB)
            throw (NoSuchObjectInDB) e;

    }
}
