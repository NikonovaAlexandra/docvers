package service;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 17.03.13
 * Time: 20:51
 * To change this template use File | Settings | File Templates.
 */
public class QueriesHQL {

//    public static final String INSERT_INTO_DOCUMENT_AUTHOR_NAME_DESCRIPTION_VALUES = "insert into document (author_id, document_name, description, code_document_name ) values (?,?,?,?)";
//    public static final String INSERT_INTO_DOCUMENT = "insert into document (author_id, document_name, description, code_document_name) values (?,?,?,?)";
//    public static final String SELECT_FROM_DOCUMENT = "select * from document order by id desc";
    public static final String SELECT_FROM_DOCUMENT_WHERE_AUTHOR_ID = "from DocumentEntity where author_id = :id order by id desc";
//    public static final String SELECT_FROM_DOCUMENT_WHERE_DOCUMENT_NAME_AND_AUTHOR_ID = "from DocumentEntity where document_name=? and author_id= (select id from author  where login = ?)";
//    public static final String SELECT_AUTHOR_ID_FROM_DOCUMENT_WHERE_ID = "select author_id from document where id=?";
//    public static final String DELETE_FROM_DOCUMENT_WHERE_ID = "delete from document where id=?";
//    public static final String DELETE_FROM_DOCUMENT_WHERE_AUTHOR_ID_AND_LOGIN= "delete from document where author_id=(select id from author where login = ?) and document_name=?";
//    public static final String UPDATE_DOCUMENT_SET_DESCRIPTION_WHERE_DOCUMENT_NAME_AND_LOGIN = "update document set description = ?  where document_name=? and author_id= (select id from author  where login = ?)";
    public static final String DELETE_FROM_DOCUMENT_WHERE_AUTHOR_ID_AND_CODE = "delete from DocumentEntity d where d.authorId = ( select a.id from AuthorEntity a where a.login = :login) and d.codeDocumentName = :codeDocumentName";
    public static final String SELECT_FROM_DOCUMENT_WHERE_DOCUMENT_NAME_CODE_AND_AUTHOR_ID = "select d from DocumentEntity d, AuthorEntity a where a.id=d.authorId and ( d.codeDocumentName=:codeDocumentName and a.login=:login)";
    public static final String SELECT_ID_FROM_DOCUMENT = "select d.id from DocumentEntity d, AuthorEntity a where a.id=d.authorId and ( d.codeDocumentName=:codeDocumentName and a.login=:login)";
//
//    public static final String INSERT_INTO_AUTHOR = "insert into author (login, password) values (?,?)";
    public static final String SELECT_FROM_AUTHOR_WHERE_LOGIN = "from AuthorEntity a where a.login = :login";
    public static final String SELECT_FROM_AUTHOR_WHERE_ID = "from AuthorEntity a where a.id = :id";
    public static final String DELETE_FROM_VERSION_WHERE_VERSION_NAME_AND_DOC_AND_LOGIN = "delete from VersionEntity v where v.versionName=:versionName and v.documentId = (select id from DocumentEntity where codeDocumentName=:codeDocumentName and authorId=(select id from AuthorEntity where login =:login))";
    public static final String SELECT_FROM_VERSION_WHERE_DOCUMENT_ID = "from VersionEntity v where v.documentId=:id order by id desc";
    public static final String SELECT_VERSION_NAME_FROM_VERSION = "select max(versionName) from VersionEntity  where documentId =:id";
    public static final String UPDATE_VERSION_SET_IS_RELEASED = "update VersionEntity v set v.released =:isReleased where documentId =:id and released <>:isReleased";


    public static final String SELECT_VERSION_TYPE_FROM_VERSION = "select v.versionType from VersionEntity v, DocumentEntity d, AuthorEntity a where v.versionName=:versionName and v.documentId=d.id and d.codeDocumentName =:codeDocumentName and d.authorId=a.id and a.login =:login";
    public static final String SELECT_FROM_VERSION_WHERE_DOCUMENT_ID_AND_VERSION_NAME = "from VersionEntity where documentId =:id and versionName =:versionName";
}
