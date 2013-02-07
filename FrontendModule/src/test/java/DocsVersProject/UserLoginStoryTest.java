package DocsVersProject;

import org.junit.Test;
/**
 * Created with IntelliJ IDEA.
 * User: alni
 * Date: 06.02.13
 * Time: 14:50
 * To change this template use File | Settings | File Templates.
 */
public class UserLoginStoryTest {

    @Test
    public void successfullLogin() {
        userPutUsernameIntoUsernameField();
        userPutPasswordIntoPasswordField();
        userClickLoginButton();
        assertUserIsLoggedIn();
    }

    @Test
    public void unsuccessfullLogin() {
        userPutUsernameIntoUsernameField();
        userPutPasswordIntoPasswordField();
        userClickLoginButton();
        assertUserIsNotLoggedIn();
    }

    @Test
    public void incorrectLogin() {
        userPutUsernameIntoUsernameField();
        assertIncorrectLogin();
    }

    @Test
    public void incorrectPassword() {
        userPutUsernameIntoUsernameField();
        assertIncorrectPassword();
    }

    private void assertIncorrectLogin() {
    }

    private void assertIncorrectPassword() {
    }

    private void assertUserIsNotLoggedIn() {
    }


    private void assertUserIsLoggedIn() {


    }

    private void userClickLoginButton() {
        //To change body of created methods use File | Settings | File Templates.
    }

    private void userPutPasswordIntoPasswordField() {
        //To change body of created methods use File | Settings | File Templates.
    }

    private void userPutUsernameIntoUsernameField() {


    }
}
