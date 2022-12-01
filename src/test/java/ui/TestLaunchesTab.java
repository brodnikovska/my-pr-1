package ui;

import beans.User;
import com.epam.reportportal.testng.ReportPortalTestNGListener;
import utils.PropertyController;
import entities.LaunchesMenus;
import entities.LeftSideBar;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pageobjects.LaunchesPage;
import pageobjects.LeftSidePanel;
import pageobjects.LoginPage;

import static com.codeborne.selenide.Selenide.page;

@Listeners({ReportPortalTestNGListener.class})
public class TestLaunchesTab extends BaseTestCase {
    private static final String USERNAME = PropertyController.getPropertyByKey("user.name");
    private static final String PASSWORD = PropertyController.getPropertyByKey("user.password");

    @DataProvider(name = "validCredentials")
    public static Object[][] validCredentials() {
        return new Object[][]{
                {PropertyController.getPropertyByKey("user.name"), PropertyController.getPropertyByKey("user.password")},
                {PropertyController.getPropertyByKey("admin.name"), PropertyController.getPropertyByKey("admin.password")}
        };
    }

    @Test(dataProvider = "validCredentials")
    public void verifyLaunchesMenuContainsAllFields(String username, String password) {
        LoginPage loginPage = page(LoginPage.class);
        LeftSidePanel leftSidePanel = page(LeftSidePanel.class);
        LaunchesPage launchesPage = page(LaunchesPage.class);
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        loginPage
                .login(user)
                .verifyLoginSuccessful();
        leftSidePanel
                .selectButton(LeftSideBar.LAUNCHES.toString());
        Assert.assertEquals(launchesPage.getLaunchesMenuTitles(), LaunchesMenus.getValues());

    }

    @Test
    public void verifyAnyLaunchContainsAllFields() {
        LoginPage loginPage = page(LoginPage.class);
        LeftSidePanel leftSidePanel = page(LeftSidePanel.class);
        LaunchesPage launchesPage = page(LaunchesPage.class);
        User user = new User();
        user.setUsername(USERNAME);
        user.setPassword(PASSWORD);

        loginPage
                .login(user)
                .verifyLoginSuccessful();
        leftSidePanel
                .selectButton(LeftSideBar.LAUNCHES.toString());
        launchesPage
                .openRandomTestResults();
        Assert.assertEquals(launchesPage.getLaunchesMenuTitles(), LaunchesMenus.getValues());
    }
}
