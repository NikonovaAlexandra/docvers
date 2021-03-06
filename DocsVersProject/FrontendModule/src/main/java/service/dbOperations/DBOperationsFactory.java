package service.dbOperations;

import dao.DAOType;

/**
* Created with IntelliJ IDEA.
* User: alni
* Date: 19.03.13
* Time: 12:48
* To change this template use File | Settings | File Templates.
*/
public class DBOperationsFactory {

    public static DBOperations getDBService(DAOType type) {
        if (type == DAOType.JDBC)
            return new DBOperationsJDBC();
        else
            return new DBOperationsH(type);
    }
}
