package dao.version;

import entities.Document;
import entities.Version;
import exception.*;
import org.h2.constant.ErrorCode;
import service.Queries;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 12.02.13
 * Time: 9:11
 * To change this template use File | Settings | File Templates.
 */
public class VersionDAOImpl implements VersionDAO {
    private Connection conn;

    public VersionDAOImpl(Connection conn) throws DAOException, SystemException {
        if (conn == null)
            throw new NullConnectionException();
        try {
            this.conn = conn;
            this.conn.setAutoCommit(false);
        } catch (SQLException e) {
            if (e.getErrorCode() == ErrorCode.CONNECTION_BROKEN_1)
                throw new NullConnectionException(e);
            if (e.getErrorCode() == ErrorCode.NOT_ENOUGH_RIGHTS_FOR_1) {
                throw new NotEnoughRightsException("", e);
            } else throw new DAOException(e);
        }
    }

    @Override
    public List<Version> getVersionsOfDocument(long id) throws DAOException, SystemException {
        List<Version> versions = new ArrayList<Version>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Version version;

        try {
            ps = conn.prepareStatement(Queries.SELECT_FROM_VERSION_WHERE_DOCUMENT_ID);
            ps.setLong(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                version = new Version();
                version.setId(rs.getLong("ID"));
                version.setAuthorID(rs.getLong("AUTHOR_ID"));
                version.setDate(rs.getDate("DATE"));
                version.setDocumentID(rs.getLong("DOCUMENT_ID"));
                version.setDocumentPath(rs.getString("DOCUMENT_PATH"));
                version.setVersionDescription(rs.getString("VERSION_DESCRIPTION"));
                versions.add(version);
            }
            conn.commit();
            if(versions.isEmpty()) throw new NoSuchObjectInDB("Versions of this document");
            return versions;
        } catch (SQLException e) {
            if (e.getErrorCode() == ErrorCode.CONNECTION_BROKEN_1)
                throw new NullConnectionException(e);
            if (e.getErrorCode() == ErrorCode.NOT_ENOUGH_RIGHTS_FOR_1) {
                throw new NotEnoughRightsException("", e);
            } else throw new DAOException(e);
        } finally {

            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
    }

    @Override
    public void addVersion(Version version) throws DAOException, SystemException {
        PreparedStatement ps = null;
        if (version == null) {
            throw new IllegalArgumentException();
        }
        try {
            ps = conn.prepareStatement(Queries.INSERT_INTO_VERSION);
            ps.setLong(1, version.getDocumentID());
            ps.setLong(2, version.getAuthorID());
            ps.setDate(3, version.getDate());
            ps.setString(4, version.getVersionDescription());
            ps.setString(5, version.getDocumentPath());
            ps.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                throw new DAOException(e);
            }
            if (e.getErrorCode() == ErrorCode.DUPLICATE_KEY_1) {
                throw new ObjectAlreadyExistsException();
            }
            if (e.getErrorCode() == ErrorCode.CONNECTION_BROKEN_1)
                throw new NullConnectionException(e);
            if (e.getErrorCode() == ErrorCode.REFERENTIAL_INTEGRITY_VIOLATED_PARENT_MISSING_1)
                throw new ReferentialIntegrityViolatedException();
            if (e.getErrorCode() == ErrorCode.NOT_ENOUGH_RIGHTS_FOR_1) {
                throw new NotEnoughRightsException("", e);
            }
            if (e.getErrorCode() == ErrorCode.NO_DISK_SPACE_AVAILABLE) {
                throw new NoDiskSpaceException("", e);
            }

        } finally {
            try {

                if (ps != null)
                    ps.close();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }

    }

    @Override
    public void deleteVersion(long id) throws DAOException, SystemException {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(Queries.DELETE_FROM_VERSION_WHERE_ID);
            ps.setLong(1, id);
            int i = ps.executeUpdate();
            if (i == 0) throw new NoSuchObjectInDB("Nothing to delete");
            conn.commit();
        } catch (SQLException e) {
            if (e.getErrorCode() == ErrorCode.CONNECTION_BROKEN_1)
                throw new NullConnectionException(e);
            if (e.getErrorCode() == ErrorCode.NOT_ENOUGH_RIGHTS_FOR_1) {
                throw new NotEnoughRightsException("", e);
            }
            try {
                conn.rollback();
            } catch (SQLException e1) {
                throw new DAOException(e);
            }

        } finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
    }


}
