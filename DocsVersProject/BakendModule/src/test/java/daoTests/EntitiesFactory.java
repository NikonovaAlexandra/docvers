package daoTests;

import entities.Author;
import entities.Document;


/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 11.02.13
 * Time: 9:29
 * To change this template use File | Settings | File Templates.
 */
public class EntitiesFactory {
    public static Document createNewDocument() {
        return new Document(1,"", "","".hashCode());
    }

    public static Author createNewAuthor() {
        return new Author();
    }
}
