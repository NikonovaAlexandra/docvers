package dao.version;

import dao.DAO;
import entities.Document;
import entities.Version;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 12.02.13
 * Time: 9:05
 * To change this template use File | Settings | File Templates.
 */
public interface VersionDAO extends DAO {
    public List<Version> getVersionsOfDocument(Document document) throws SQLException;
}
