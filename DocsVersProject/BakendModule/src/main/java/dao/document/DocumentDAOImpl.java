package dao.document;

import entities.Document;
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
 * Date: 07.02.13
 * Time: 11:03
 * To change this template use File | Settings | File Templates.
 */
public class DocumentDAOImpl implements DocumentDAO {
    private Connection conn;

    public DocumentDAOImpl(Connection conn) throws DAOException {
        if (conn == null)
            throw new NullConnectionException();
        try {
            this.conn = conn;
            this.conn.setAutoCommit(false);
        } catch (SQLException e) {
            if (e.getErrorCode() == ErrorCode.CONNECTION_BROKEN_1)
                throw new NullConnectionException(e);
            else throw new DAOException(e);
        }
    }

    @Override
    public List<Document> getAllDocuments() throws DAOException, SystemException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(Queries.SELECT_FROM_DOCUMENT);
            rs = ps.executeQuery();
            conn.commit();
            return createDocumentsListFromResultSet(rs);
        } catch (SQLException e) {
            if (e.getErrorCode() == ErrorCode.CONNECTION_BROKEN_1)
                throw new NullConnectionException(e);
            if (e.getErrorCode() == ErrorCode.NOT_ENOUGH_RIGHTS_FOR_1) {
                throw new NotEnoughRightsException("", e);
            } else throw new DAOException(e);

        } finally {
            try {
                if (ps != null) ps.close();
                if (rs != null) rs.close();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
    }

    @Override
    public void addDocument(Document document) throws DAOException, SystemException {
        PreparedStatement ps = null;
        if (document == null) {
            throw new IllegalArgumentException();
        }
        try {
            ps = conn.prepareStatement(Queries.INSERT_INTO_DOCUMENT_AUTHOR_NAME_DESCRIPTION_VALUES);
            ps.setLong(1, document.getAuthorID());
            ps.setString(2, document.getName());
            ps.setString(3, document.getDescription());
            ps.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
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
            } else throw new DAOException(e);

        } finally {
            if (ps != null) try {
                ps.close();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }

    }

    @Override
    public void deleteDocument(long id) throws DAOException, SystemException {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(Queries.DELETE_FROM_DOCUMENT_WHERE_ID);
            ps.setLong(1, id);
            ps.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            if (e.getErrorCode() == ErrorCode.CONNECTION_BROKEN_1)
                throw new NullConnectionException(e);
            if (e.getErrorCode() == ErrorCode.NOT_ENOUGH_RIGHTS_FOR_1) {
                throw new NotEnoughRightsException("", e);
            } else throw new DAOException(e);

        } finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
    }

    @Override
    public List<Document> getDocumentsByAuthorID(long id) throws DAOException, SystemException {

        PreparedStatement ps = null;
        ResultSet rsDocs = null;
        try {
            ps = conn.prepareStatement(Queries.SELECT_FROM_DOCUMENT_WHERE_AUTHOR_ID);
            ps.setLong(1, id);
            rsDocs = ps.executeQuery();
            conn.commit();
            return createDocumentsListFromResultSet(rsDocs);
        } catch (SQLException e) {
            if (e.getErrorCode() == ErrorCode.CONNECTION_BROKEN_1)
                throw new NullConnectionException(e);
            if (e.getErrorCode() == ErrorCode.NOT_ENOUGH_RIGHTS_FOR_1) {
                throw new NotEnoughRightsException("", e);
            } else throw new DAOException(e);

        } finally {
            try {
                if (ps != null) ps.close();
                if (rsDocs != null) rsDocs.close();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
    }

    private List<Document> createDocumentsListFromResultSet(ResultSet rs) throws DAOException {
        List<Document> documents = new ArrayList<Document>();
        Document document = null;
        try {
            while (rs.next()) {
                document = new Document();
                document.setId(rs.getLong("ID"));
                document.setAuthorID(rs.getLong("AUTHOR_ID"));
                document.setName(rs.getString("DOCUMENT_NAME"));
                document.setDescription(rs.getString("DESCRIPTION"));
                documents.add(document);
            }
            return documents;
        } catch (SQLException e) {
            if (e.getErrorCode() == ErrorCode.CONNECTION_BROKEN_1)
                throw new NullConnectionException(e);
            else throw new DAOException(e);
        }

    }


}
