package db.old; /**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 19.02.13
 * Time: 9:37
 * To change this template use File | Settings | File Templates.
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloWorld {
    public Logger logger = LoggerFactory.getLogger(HelloWorld.class);
    public static void main(String[] args) {

        HelloWorld n = new HelloWorld();

        n.logger.debug("Hello world.");
        n.logger.info("boom-boom-pow");
        n.logger.info(n.logger.getName());
        System.out.println("");
        HelloWorld.class.getClassLoader().getResource("C:\\Documents and Settings\\alni\\Desktop\\project\\docvers-master\\DocsVersProject\\BakendModule\\src\\main\\resources\\logback.xml");
    }

}