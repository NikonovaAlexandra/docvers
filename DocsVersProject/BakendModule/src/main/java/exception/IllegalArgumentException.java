package exception;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 13.03.13
 * Time: 12:02
 * To change this template use File | Settings | File Templates.
 */
public class IllegalArgumentException extends DAOException{

    private String ob;

    public IllegalArgumentException(String ob) {
        this.ob = ob;
    }

    public String toString() {
        return "Illegal argument : " + ob;
    }
}
