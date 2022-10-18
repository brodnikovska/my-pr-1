import beans.User;
import com.epam.reportportal.testng.ReportPortalTestNGListener;
import core.PropertyController;
import entities.LeftSideBar;
import org.testng.Assert;
import org.testng.annotations.*;
import pageobjects.LeftSidePanel;
import pageobjects.LoginPage;

import static com.codeborne.selenide.Selenide.page;

@Listeners({ReportPortalTestNGListener.class})
public class TestLeftSideTabs extends BaseTestCase {

    @DataProvider(name = "validCredentials")
    public static Object[][] validCredentials() {
        return new Object[][]{
                {PropertyController.getPropertyByKey("user.name"), PropertyController.getPropertyByKey("user.password")},
                {PropertyController.getPropertyByKey("admin.name"), PropertyController.getPropertyByKey("admin.password")}
        };
    }

    @Test(dataProvider = "validCredentials")
    public void verifyLeftSideBarIconsPresent(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        LoginPage loginPage = page(LoginPage.class);
        loginPage.login(user);
        loginPage.verifyLoginSuccessful();

        LeftSidePanel leftSidePanel = page(LeftSidePanel.class);
        Assert.assertEquals(leftSidePanel.getSideBarIconsTitles(), LeftSideBar.getValues());
    }
}
