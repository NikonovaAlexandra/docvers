package service;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 12.02.13
 * Time: 9:15
 * To change this template use File | Settings | File Templates.
 */
public class QueriesSQL {
    public static final String INSERT_INTO_DOCUMENT_AUTHOR_NAME_DESCRIPTION_VALUES = "insert into document (author_id, document_name, description, code_document_name ) values (?,?,?,?)";
    public static final String SELECT_FROM_DOCUMENT_WHERE_AUTHOR_ID = "select * from document where author_id = ? order by id desc";
    public static final String UPDATE_DOCUMENT_SET_DESCRIPTION_WHERE_DOCUMENT_NAME_AND_LOGIN = "update document set description = ?  where document_name=? and author_id= (select id from author  where login = ?)";
    public static final String DELETE_FROM_DOCUMENT_WHERE_AUTHOR_ID_AND_CODE = "delete from document where author_id=(select id from author where login = ?) and code_document_name=?";
    public static final String SELECT_FROM_DOCUMENT_WHERE_DOCUMENT_NAME_CODE_AND_AUTHOR_ID = "select * from document where code_document_name=? and author_id= (select id from author  where login = ?)";
    public static final String SELECT_ID_FROM_DOCUMENT = "select id from document where author_id=(select id from author where login = ?) and code_document_name = ?";
    public static final String UPDATE_DOCUMENT_DESCRIPTION = "update Document set description =?  where code_document_name =?  and author_id=(select id from author where login =?)";

    public static final String SELECT_FROM_AUTHOR_WHERE_LOGIN = "select * from author where login = ?";
    public static final String SELECT_FROM_AUTHOR_WHERE_ID = "select * from author where id=?";

    public static final String INSERT_INTO_VERSION = "insert into version (document_id, author_id, date, version_description, document_path, is_released, version_type, version_name) values (?,?,?,?,?,?,?,?)";
    public static final String DELETE_FROM_VERSION_WHERE_VERSION_NAME_AND_DOC_AND_LOGIN = "delete from version where version_name=? and document_id = (select id from document where code_document_name = ? and author_id=(select id from author where login = ?))";
    public static final String SELECT_FROM_VERSION_WHERE_DOCUMENT_ID = "select * from version where document_id=? order by id desc";
    public static final String SELECT_VERSION_NAME_FROM_VERSION = "select max(version_name) as version_max_name from version  where document_id = ?";
    public static final String UPDATE_VERSION_SET_IS_RELEASED = "update version set is_released = ? where document_id = ? and is_released <> ?";


    public static final String SELECT_VERSION_TYPE_FROM_VERSION = "select version_type from version where version_name=? and document_id = (select id from document where code_document_name = ? and author_id=(select id from author where login = ?))";
    public static final String SELECT_FROM_VERSION_WHERE_DOCUMENT_ID_AND_VERSION_NAME = "select * from version where document_id = ? and version_name = ?";
    public static final String UPDATE_VERSION_DESCRIPTION = "update Version set version_description =?  where version_name=? and document_id=(select id from document  where code_document_name =?  and author_id=(select id from author where login =?))";

}
