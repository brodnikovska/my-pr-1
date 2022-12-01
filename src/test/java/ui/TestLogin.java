package ui;

import beans.User;
import com.epam.reportportal.testng.ReportPortalTestNGListener;
import utils.PropertyController;
import utils.StringHelper;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pageobjects.LoginPage;

import static com.codeborne.selenide.Selenide.page;

@Listeners({ReportPortalTestNGListener.class})
public class TestLogin extends BaseTestCase {

    @DataProvider(name = "validCredentials")
    public static Object[][] validCredentials() {
        return new Object[][]{
                {PropertyController.getPropertyByKey("user.name"), PropertyController.getPropertyByKey("user.password")},
                {PropertyController.getPropertyByKey("admin.name"), PropertyController.getPropertyByKey("admin.password")}
        };
    }

    @DataProvider(name = "invalidCredentials")
    public static Object[][] invalidCredentials() {
        return new Object[][]{
                {null, ""},
                {StringHelper.generateRandomString(1, StringHelper.getValidCharacters()),
                        StringHelper.generateRandomString(3, StringHelper.getValidCharacters())}
        };
    }

    @DataProvider(name = "badCredentials")
    public static Object[][] badCredentials() {
        return new Object[][]{
                {StringHelper.generateRandomString(2, StringHelper.getValidCharacters()),
                StringHelper.generateRandomString(4, StringHelper.getValidCharacters())}
        };
    }

    @Test(priority = 1, dataProvider = "invalidCredentials")
    public void loginWithInvalidCredentials(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        LoginPage loginPage = page(LoginPage.class);
        loginPage.login(user);
        loginPage.verifyNoLogin();
    }

    @Test(priority = 2, dataProvider = "badCredentials")
    public void loginWithBadCredentials(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        LoginPage loginPage = page(LoginPage.class);
        loginPage.login(user);
        loginPage.verifyLoginUnsuccessful();
    }

    @Test(priority = 3, dataProvider = "validCredentials")
    public void loginWithValidDefaultCredentials(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        LoginPage loginPage = page(LoginPage.class);
        loginPage.login(user);
        loginPage.verifyLoginSuccessful();
    }
}
