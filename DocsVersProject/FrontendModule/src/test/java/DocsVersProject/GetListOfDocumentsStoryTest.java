package DocsVersProject;

import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 06.02.13
 * Time: 16:35
 * To change this template use File | Settings | File Templates.
 */
public class GetListOfDocumentsStoryTest {
    @Test
    public void successfulDocumentsListGetting() {
        userClickGetListOfDocumentsButton();
        assertListOfDocumentsAreDisplayed();
    }

    private void userClickGetListOfDocumentsButton() {
    }

    private void assertListOfDocumentsAreDisplayed() {
    }
}
