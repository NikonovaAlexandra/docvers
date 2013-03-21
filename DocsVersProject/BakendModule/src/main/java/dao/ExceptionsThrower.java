package dao;

import exception.*;
import org.h2.constant.ErrorCode;
import org.h2.jdbc.JdbcBatchUpdateException;
import org.h2.jdbc.JdbcSQLException;
import org.hibernate.SessionException;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 20.03.13
 * Time: 9:52
 * To change this template use File | Settings | File Templates.
 */
public class ExceptionsThrower {
    public static MyException throwException(Exception e) {

        if (e instanceof JdbcSQLException) {

            if (((JdbcSQLException) e).getErrorCode() == ErrorCode.CONNECTION_BROKEN_1)
                return new NullConnectionException(e);
            if (((JdbcSQLException) e).getErrorCode() == ErrorCode.NOT_ENOUGH_RIGHTS_FOR_1) {
                return new NotEnoughRightsException(e);
            }
            if (((JdbcSQLException) e).getErrorCode() == ErrorCode.DUPLICATE_KEY_1) {
                return new ObjectAlreadyExistsException();
            }
            if (((JdbcSQLException) e).getErrorCode() == ErrorCode.NO_DISK_SPACE_AVAILABLE) {
                return new NoDiskSpaceException(e);
            } else return new DAOException(e);
        } else if (e instanceof JdbcBatchUpdateException) {

            if (((JdbcBatchUpdateException) e).getErrorCode() == ErrorCode.DUPLICATE_KEY_1) {
                return new ObjectAlreadyExistsException();
            }
            if (((JdbcBatchUpdateException) e).getErrorCode() == ErrorCode.NULL_NOT_ALLOWED) {
                return new IntegrityConstraintException(e, "NULL is not allowed");
            }
            if (((JdbcBatchUpdateException) e).getErrorCode() == ErrorCode.REFERENTIAL_INTEGRITY_VIOLATED_PARENT_MISSING_1) {
                return new ReferentialIntegrityViolatedException();
            } else return new DAOException(e);

        } else if (e instanceof SessionException) {
            return new NullConnectionException(e);
        } else if (e instanceof ConstraintViolationException) {
            return new ObjectAlreadyExistsException();
        } else if (e instanceof NoSuchObjectInDB) {
            return (NoSuchObjectInDB) e;
        } else if (e instanceof ObjectAlreadyExistsException) {
            return (ObjectAlreadyExistsException) e;
        } else return new MyException(e);

    }
}
