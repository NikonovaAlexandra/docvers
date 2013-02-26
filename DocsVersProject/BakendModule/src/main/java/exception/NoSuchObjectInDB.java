package exception;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 25.02.13
 * Time: 11:02
 * To change this template use File | Settings | File Templates.
 */
public class NoSuchObjectInDB extends DAOException {
    private String ob;

    public NoSuchObjectInDB(String ob) {
        this.ob = ob;
    }

    public String toString() {
        return "No such object in database : " + ob;
    }
}
