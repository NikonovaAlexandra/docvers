package DocsVersProject;

import org.junit.Test;
/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 06.02.13
 * Time: 16:19
 * To change this template use File | Settings | File Templates.
 */
public class CreateNewDocumentStoryTest {
    @Test
    public void successfulDocumentCreation() {
        userClickCreateNewDocumentButton();
        createNewDocumentDialogAreOpened();
        userPutDocumentNameInDocumentNameField();
        userPutDocumentDescriptionInDocumentDescriptionField();
        userClickCreateButton();
        assertNewDocumentIsCreated();
    }

    @Test
    public void unsuccessfulDocumentCreation() {
        userClickCreateNewDocumentButton();
        createNewDocumentDialogAreOpened();
        userPutDocumentNameInDocumentNameField();
        userPutDocumentDescriptionInDocumentDescriptionField();
        userClickCreateButton();
        assertNewDocumentIsNotCreated();
    }

    @Test
    public void cancelDocumentCreation() {
        userClickCreateNewDocumentButton();
        createNewDocumentDialogAreOpened();
        userPutDocumentNameInDocumentNameField();
        userPutDocumentDescriptionInDocumentDescriptionField();
        userClickCancelButton();
        assertNewDocumentIsNotCreated();
    }
    @Test
    public void unsuccessfulCreateNewDocumentDialogOpening() {
        userClickCreateNewDocumentButton();
        assertCreateNewDocumentDialogAreOpened();
    }

    private void assertCreateNewDocumentDialogAreOpened() {
    }

    private void userClickCancelButton() {
    }

    private void assertNewDocumentIsNotCreated() {
    }

    private void userClickCreateNewDocumentButton() {
        //To change body of created methods use File | Settings | File Templates.
    }

    private void createNewDocumentDialogAreOpened() {
    }

    private void userPutDocumentNameInDocumentNameField() {
    }

    private void userPutDocumentDescriptionInDocumentDescriptionField() {
    }

    private void userClickCreateButton() {
    }

    private void assertNewDocumentIsCreated() {
    }

}
