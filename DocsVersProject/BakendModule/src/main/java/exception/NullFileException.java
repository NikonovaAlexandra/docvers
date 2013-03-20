package exception;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 01.03.13
 * Time: 11:40
 * To change this template use File | Settings | File Templates.
 */
public class NullFileException extends BusinessException {
    private String message;

    public NullFileException(String message) {
        super(null);
        this.message = message;
    }

    public String toString() {
        return message;
    }
}
