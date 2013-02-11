package dao;

import entities.Author;
import entities.Document;
import entities.Version;
import exception.NullConnectionException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 07.02.13
 * Time: 11:03
 * To change this template use File | Settings | File Templates.
 */
public class DocumentDAOImpl implements DocumentDAO {
    public static final String INSERT_INTO_DOCUMENT_ID_AUTHOR_NAME_DESCRIPTION_VALUES = "insert into document (id, author, document_name, description ) values (?,?,?,?)";
    public static final String SELECT_FROM_VERSION_WHERE_DOCUMENT_ID = "select * from version where document_id=?";
    public static final String SELECT_FROM_AUTHOR_WHERE_ID = "select * from author where id=?";
    public static final String SELECT_FROM_DOCUMENT = "select * from document";
    public static final String SELECT_FROM_DOCUMENT_WHERE_AUTHOR = "select * from document where author_id=?";
    public static final String SELECT_FROM_AUTHOR_WHERE_DOCUMENT_ID = "select * from author where document_id=?";
    private Connection conn;

    public DocumentDAOImpl(Connection conn) throws NullConnectionException {
        if(conn == null) throw new NullConnectionException(DocumentDAOImpl.class);
        this.conn = conn;
    }

    @Override
    public List<Document> getAllDocuments() throws SQLException {
        List<Document> documents = new ArrayList<Document>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Document document = new Document();
       try{
            ps = conn.prepareStatement(SELECT_FROM_DOCUMENT);
            rs = ps.executeQuery();
            long id;
            while(rs.next()){
                id = rs.getLong("ID");
                document.setId(id);
                document.setName(rs.getString("DOCUMENT_NAME"));
                document.setDescription(rs.getClob("DESCRIPTION"));
                document.setAuthor(getAuthorByID(id));
                documents.add(document);
            }
            return documents; }
        finally {

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
        ps = conn.prepareStatement(INSERT_INTO_DOCUMENT_ID_AUTHOR_NAME_DESCRIPTION_VALUES);
        ps.setLong(1,document.getId());
        ps.setObject(2, document.getAuthor());
        ps.setString(3, document.getName());
        ps.setClob(4, document.getDescription());
        ps.executeUpdate(); }
        finally {
            if(ps!=null) ps.close();
        }
    }

    @Override
    public void deleteDocument(Document document) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    @Override
    public List<Version> getVersionsOfDocument(Document document) throws SQLException {
        List<Version> versions = new ArrayList<Version>();
        PreparedStatement ps = null;
        ResultSet rsVersions = null;
        Version version = new Version();
        if(document == null) {
            throw new IllegalArgumentException();
        }
       try{
        ps = conn.prepareStatement(SELECT_FROM_VERSION_WHERE_DOCUMENT_ID);
        ps.setLong(1,document.getId());
        rsVersions = ps.executeQuery();
        long id;
        while(rsVersions.next()) {
            id = rsVersions.getLong("ID");
            version.setId(id);
            version.setAuthor(getAuthorByID(id));
            version.setDate(rsVersions.getDate("DATE"));
            version.setDocument(document);
            version.setDocumentPath(rsVersions.getString("DOCUMENT_PATH"));
            version.setVersionDescription(rsVersions.getClob("VERSION_DESCRIPTION"));
            versions.add(version);
        }
        return versions;}
    finally {

        if(ps!= null) ps.close();
        if(rsVersions!= null) rsVersions.close();
    }
    }

    @Override
    public Author getAuthorByID(long id) throws SQLException {
//        if(conn==null) {
//            throw new NullPointerException();
//        }
//        PreparedStatement ps = conn.prepareStatement(SELECT_FROM_AUTHOR_WHERE_ID);
//        ps.setLong(1,id);
//        ResultSet rs = ps.executeQuery();
//        Author author = new Author(id, rs.getString("LOGIN"), rs.getString("PASSWORD"));
//        return author;
        return null;
    }

    @Override
    public Author getAuthorByDocument(Document document) throws SQLException {
//        if(document == null) {
//            throw new IllegalArgumentException();
//        }
//        if(conn == null) {
//            throw new NullPointerException();
//        }
//        PreparedStatement ps = conn.prepareStatement(SELECT_FROM_AUTHOR_WHERE_DOCUMENT_ID);
//        ps.setLong(1,document.getId());
//        ResultSet rs = ps.executeQuery();
//        Author author = new Author(rs.getLong("ID"), rs.getString("LOGIN"), rs.getString("PASSWORD"));
//        return author;
        return null;
    }

    @Override
    public List<Document> getDocumentsByAuthor(Author author) throws SQLException {
//        List<Document> documents = new ArrayList<Document>();
//        Document document = new Document();
//        if(author == null) {
//            throw new IllegalArgumentException();
//        }
//        PreparedStatement ps = conn.prepareStatement(SELECT_FROM_DOCUMENT_WHERE_AUTHOR);
//        ps.setLong(1,author.getId());
//        ResultSet rsDocs = ps.executeQuery();
//        long id;
//        while(rsDocs.next()) {
//            id = rsDocs.getLong("ID");
//            document.setId(id);
//            document.setAuthor(author);
//            document.setName(rsDocs.getString("DOCUMENT_NAME"));
//            document.setDescription(rsDocs.getClob("DESCRIPTION"));
//            documents.add(document);
//        }
//        return documents;
        return null;
    }


}
