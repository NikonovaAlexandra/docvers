package dao.version;

import entities.Version;
import exception.*;
import org.h2.constant.ErrorCode;
import service.Queries;

import java.lang.IllegalArgumentException;
import java.sql.*;
import java.util.ArrayList;
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
                version.setReleased(rs.getBoolean("is_released"));
                version.setVersionName(rs.getLong("version_name"));
                version.setVersionType(rs.getString("version_type"));
                versions.add(version);
            }
            conn.commit();
            if (versions.isEmpty()) throw new NoSuchObjectInDB("Versions of this document");
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
        ResultSet rs = null;
        if (version == null) {
            throw new IllegalArgumentException();
        }
        try {
            long name = getLastVersionNameInfo(version.getDocumentID()) + 1;
            conn.commit();
            ps = conn.prepareStatement(Queries.UPDATE_VERSION_SET_IS_RELEASED);
            ps.setBoolean(1, true);
            ps.setLong(2, version.getDocumentID());
            ps.setBoolean(3, true);
            ps.executeUpdate();
            ps = conn.prepareStatement(Queries.INSERT_INTO_VERSION);
            ps.setLong(1, version.getDocumentID());
            ps.setLong(2, version.getAuthorID());
            ps.setDate(3, version.getDate());
            ps.setString(4, version.getVersionDescription());
            ps.setString(5, version.getDocumentPath());
            ps.setBoolean(6, false);
            ps.setString(7, version.getVersionType());
            ps.setLong(8, name);
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
            }   throw new DAOException(e);

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
    public void deleteVersion(long versName, long docCode, String login) throws DAOException, SystemException {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(Queries.DELETE_FROM_VERSION_WHERE_VERSION_NAME_AND_DOC_AND_LOGIN);
            ps.setLong(1, versName);
            ps.setLong(2, docCode);
            ps.setString(3, login);
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

    @Override
    public String getVersionType(long versionName, long documentName, String login) throws DAOException, SystemException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(Queries.SELECT_VERSION_TYPE_FROM_VERSION);
            ps.setLong(1, versionName);
            ps.setLong(2, documentName);
            ps.setString(3, login);
            rs = ps.executeQuery();
            String type = null;
            if(rs.next()){
                type = rs.getString("version_type");
            }

            conn.commit();
            return type;
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                throw new DAOException(e);
            }
            if (e.getErrorCode() == ErrorCode.CONNECTION_BROKEN_1)
                throw new NullConnectionException(e);
            if (e.getErrorCode() == ErrorCode.NOT_ENOUGH_RIGHTS_FOR_1) {
                throw new NotEnoughRightsException("", e);
            } throw new DAOException(e);


        } finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
    }

    @Override
    public Version getVersion(long id, long versName) throws DAOException, SystemException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Version version = null;

        try {
            ps = conn.prepareStatement(Queries.SELECT_FROM_VERSION_WHERE_DOCUMENT_ID_AND_VERSION_NAME);
            ps.setLong(1, id);
            ps.setLong(2, versName);
            rs = ps.executeQuery();
            while (rs.next()) {
                version = new Version();
                version.setId(rs.getLong("ID"));
                version.setAuthorID(rs.getLong("AUTHOR_ID"));
                version.setDate(rs.getDate("DATE"));
                version.setDocumentID(rs.getLong("DOCUMENT_ID"));
                version.setDocumentPath(rs.getString("DOCUMENT_PATH"));
                version.setVersionDescription(rs.getString("VERSION_DESCRIPTION"));
                version.setReleased(rs.getBoolean("is_released"));
                version.setVersionName(rs.getLong("version_name"));
                version.setVersionType(rs.getString("version_type"));
            }
            conn.commit();
            if (version == null) throw new NoSuchObjectInDB("Version of this document with same name = " + versName);
            return version;
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
    public long getLastVersionNameInfo(long docID) throws DAOException, SystemException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(Queries.SELECT_VERSION_NAME_FROM_VERSION);
            ps.setLong(1, docID);
            rs = ps.executeQuery();
            long name = 0;
            if(rs.next()){
                name = rs.getLong("version_max_name");
            }

            conn.commit();
            return name;
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                throw new DAOException(e);
            }
            if (e.getErrorCode() == ErrorCode.CONNECTION_BROKEN_1)
                throw new NullConnectionException(e);
            if (e.getErrorCode() == ErrorCode.NOT_ENOUGH_RIGHTS_FOR_1) {
                throw new NotEnoughRightsException("", e);
            } throw new DAOException(e);


        } finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }

    }


}
