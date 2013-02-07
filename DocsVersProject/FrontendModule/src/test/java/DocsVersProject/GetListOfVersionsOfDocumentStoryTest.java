package DocsVersProject;
import org.junit.Test;
/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 07.02.13
 * Time: 10:04
 * To change this template use File | Settings | File Templates.
 */
public class GetListOfVersionsOfDocumentStoryTest {
    @Test
    public void successfulGettingListOfVersionsOfDocument(){
        userChooseDocumentFromListOfDocuments();
        userClickGetVersionsOfCurrentDocumentButton();
        assertListOfVersionsAreDisplayed();
    }

    @Test
    public void unsuccessfulGettingListOfVersionsOfDocument(){
        userChooseDocumentFromListOfDocuments();
        userClickGetVersionsOfCurrentDocumentButton();
        assertListOfVersionsAreNotDisplayed();
    }

    @Test
    public void unsuccessfulSelectDocumentFromListOfDocuments(){
        userChooseDocumentFromListOfDocuments();
        assertDocumentIsNotSelected();
    }

    private void assertDocumentIsNotSelected() {
    }

    private void userChooseDocumentFromListOfDocuments() {
    }

    private void userClickGetVersionsOfCurrentDocumentButton() {
    }

    private void assertListOfVersionsAreNotDisplayed() {
    }

    private void assertListOfVersionsAreDisplayed() {
    }
}
