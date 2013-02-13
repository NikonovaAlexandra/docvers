package dao.document;


import entities.Document;
import exception.NullConnectionException;
import util.Queries;

import java.sql.*;
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

    public DocumentDAOImpl(Connection conn) throws NullConnectionException, SQLException {
        if(conn == null) throw new NullConnectionException(DocumentDAOImpl.class);
        this.conn = conn;
        this.conn.setAutoCommit(false);
    }

    @Override
    public List<Document> getAllDocuments() throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
       try{
            ps = conn.prepareStatement(Queries.SELECT_FROM_DOCUMENT);
            rs = ps.executeQuery();
            conn.commit();
            return createDocumentsListFromResultSet(rs);
       }finally {

           if(ps!= null) ps.close();
           if(rs!= null) rs.close();
        }
    }

    @Override
    public void addDocument(Document document) throws SQLException {
        PreparedStatement ps = null;
        if(document== null) {
            throw new IllegalArgumentException();
        }
        try{
        ps = conn.prepareStatement(Queries.INSERT_INTO_DOCUMENT_AUTHOR_NAME_DESCRIPTION_VALUES);
        ps.setLong(1, document.getAuthorID());
        ps.setString(2, document.getName());
        ps.setString(3, document.getDescription());
        ps.executeUpdate();
        conn.commit();}
        finally {
            if(ps!=null) ps.close();
        }
    }

    @Override
    public void deleteDocument(long id) throws SQLException {
        PreparedStatement ps = null;
        try{
            ps = conn.prepareStatement(Queries.DELETE_FROM_DOCUMENT_WHERE_ID);
            ps.setLong(1,id);
            ps.executeUpdate();
            conn.commit();}
        finally {
            if(ps!=null) ps.close();
        }
    }

    @Override
    public List<Document> getDocumentsByAuthorID(long id) throws SQLException {

        PreparedStatement ps = null;
        ResultSet rsDocs = null;
        try{
            ps = conn.prepareStatement(Queries.SELECT_FROM_DOCUMENT_WHERE_AUTHOR_ID);
            ps.setLong(1,id);
            rsDocs = ps.executeQuery();
            conn.commit();
            return createDocumentsListFromResultSet(rsDocs);
        } finally {
            if(ps!=null) ps.close();
            if(rsDocs!=null) rsDocs.close();
        }
    }

    private List<Document> createDocumentsListFromResultSet(ResultSet rs) throws SQLException {
        List<Document> documents = new ArrayList<Document>();
        Document document;
        while(rs.next()) {
            document = new Document();
            //document.setId(rs.getLong("ID"));
            document.setAuthorID(rs.getLong("AUTHOR_ID"));
            document.setName(rs.getString("DOCUMENT_NAME"));
            document.setDescription(rs.getString("DESCRIPTION"));
            documents.add(document);
        }
        return documents;
    }


}
