package dao.version;

import entities.Version;
import exception.*;
import org.h2.constant.ErrorCode;
import util.Queries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        ResultSet rsVersions = null;
        Version version;

        try {
            ps = conn.prepareStatement(Queries.SELECT_FROM_VERSION_WHERE_DOCUMENT_ID);
            ps.setLong(1, id);
            rsVersions = ps.executeQuery();
            conn.commit();
            if(!rsVersions.next()) throw new NoSuchObjectInDB("Versions of this document");
            while (rsVersions.next()) {
                version = new Version();
                version.setId(rsVersions.getLong("ID"));
                version.setAuthorID(rsVersions.getLong("AUTHOR_ID"));
                version.setDate(rsVersions.getDate("DATE"));
                version.setDocumentID(rsVersions.getLong("DOCUMENT_ID"));
                version.setDocumentPath(rsVersions.getString("DOCUMENT_PATH"));
                version.setVersionDescription(rsVersions.getString("VERSION_DESCRIPTION"));
                versions.add(version);
            }
            return versions;
        } catch (SQLException e) {
            if (e.getErrorCode() == ErrorCode.CONNECTION_BROKEN_1)
                throw new NullConnectionException(e);
            if (e.getErrorCode() == ErrorCode.NOT_ENOUGH_RIGHTS_FOR_1) {
                throw new NotEnoughRightsException("", e);
            } else throw new DAOException(e);
        } finally {

            try {
                if (ps != null) ps.close();
                if (rsVersions != null) rsVersions.close();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
    }

}
