package util;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 12.02.13
 * Time: 9:15
 * To change this template use File | Settings | File Templates.
 */
public class Queries {
    public static final String INSERT_INTO_DOCUMENT_AUTHOR_NAME_DESCRIPTION_VALUES = "insert into document (author_id, document_name, description ) values (?,?,?)";
    public static final String SELECT_FROM_VERSION_WHERE_DOCUMENT_ID = "select * from version where document_id=?";
    public static final String SELECT_FROM_AUTHOR_WHERE_ID = "select * from author where id=?";
    public static final String SELECT_FROM_DOCUMENT = "select * from document";
    public static final String DELETE_FROM_DOCUMENT_WHERE_ID = "delete from document where id=?";
    public static final String SELECT_FROM_DOCUMENT_WHERE_AUTHOR_ID = "select * from document where author_id = ?";
    public static final String SELECT_AUTHOR_ID_FROM_DOCUMENT_WHERE_ID = "select author_id from document where id=?";
    public static final String INSERT_INTO_AUTHOR = "insert into author (login, password) values (?,?)";
    public static final String INSERT_INTO_VERSION = "insert into version (document_id, author_id, date, version_description, document_path) values (?,?,?,?,?)";
    public static final String INSERT_INTO_DOCUMENT = "insert into document (author_id, document_name, description) values (?,?,?)";
}
