package ui;

import beans.User;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.epam.reportportal.testng.ReportPortalTestNGListener;
import entities.LeftSideBar;
import entities.UtilityPage;
import org.testng.Assert;
import org.testng.annotations.*;
import pageobjects.DashboardsPage;
import pageobjects.LeftSidePanel;
import pageobjects.LoginPage;
import utils.PropertyController;

import static com.codeborne.selenide.Selenide.page;

@Listeners({ReportPortalTestNGListener.class})
public class WidgetResizeTest extends BaseTestCase {
    private LoginPage loginPage = page(LoginPage.class);
    private LeftSidePanel leftSidePanel = page(LeftSidePanel.class);
    private DashboardsPage dashboardsPage = page(DashboardsPage.class);
    private static final String TEST_DASHBOARD_NAME = "!wRRV";
    private static final String TEST_WIDGET_NAME = "DEMO_FILTER_752";
    private static final String OTHER_WIDGET_NAME = "DEMO_FILTER_2000";
    private static final String USERNAME = PropertyController.getPropertyByKey("user.name");
    private static final String PASSWORD = PropertyController.getPropertyByKey("user.password");
    private static final int X_OFFSET = 100;
    private static final int Y_OFFSET = 90;

    @BeforeMethod()
    public void login(){
        User user = new User();
        user.setUsername(USERNAME);
        user.setPassword(PASSWORD);
        loginPage
                .login(user)
                .verifyLoginSuccessful();
    }

    @AfterMethod()
    public void logout(){
        WebDriverRunner.closeWebDriver();
    }

    @Test()
    public void verifySelectedSizeIsSaved() {
        leftSidePanel.selectButton(LeftSideBar.DASHBOARDS.toString());
        dashboardsPage
                .openDashboard(TEST_DASHBOARD_NAME);
        UtilityPage.getTheFullScreen();
        int widgetWidthBeforeTransform = dashboardsPage.getWidgetWidth(TEST_WIDGET_NAME);
        int widgetHeightBeforeTransform = dashboardsPage.getWidgetHeight(TEST_WIDGET_NAME);

        dashboardsPage.resizeWidget(TEST_WIDGET_NAME, X_OFFSET, Y_OFFSET);
        int widgetWidthAfterTransform = dashboardsPage.getWidgetWidth(TEST_WIDGET_NAME);
        int widgetHeightAfterTransform = dashboardsPage.getWidgetHeight(TEST_WIDGET_NAME);
        Assert.assertTrue(widgetWidthAfterTransform > widgetWidthBeforeTransform);
        Assert.assertTrue(widgetHeightAfterTransform > widgetHeightBeforeTransform);

        dashboardsPage.resizeWidget(TEST_WIDGET_NAME, -X_OFFSET, -Y_OFFSET);
        Assert.assertTrue(dashboardsPage.getWidgetWidth(TEST_WIDGET_NAME) < widgetWidthAfterTransform);
        Assert.assertTrue(dashboardsPage.getWidgetHeight(TEST_WIDGET_NAME) < widgetHeightAfterTransform);
    }

    @Test()
    public void verifyContentOfWidgetResizes() {
        leftSidePanel.selectButton(LeftSideBar.DASHBOARDS.toString());
        dashboardsPage
                .openDashboard(TEST_DASHBOARD_NAME);
        UtilityPage.getTheFullScreen();
        int numberOfTestRunsBeforeTransform = dashboardsPage.getNumberOfVisibleRuns(TEST_WIDGET_NAME);

        dashboardsPage.resizeWidget(TEST_WIDGET_NAME, X_OFFSET, Y_OFFSET);
        int numberOfTestRunsAfterTransform = dashboardsPage.getWidgetWidth(TEST_WIDGET_NAME);
        Assert.assertTrue(numberOfTestRunsBeforeTransform < numberOfTestRunsAfterTransform);

        dashboardsPage.resizeWidget(TEST_WIDGET_NAME, -X_OFFSET, -Y_OFFSET);
        Assert.assertTrue(dashboardsPage.getNumberOfVisibleRuns(TEST_WIDGET_NAME) < numberOfTestRunsAfterTransform);
    }

    @Test()
    public void verifyOtherWidgetsMoveWhileResizing() {
        leftSidePanel.selectButton(LeftSideBar.DASHBOARDS.toString());
        dashboardsPage
                .openDashboard(TEST_DASHBOARD_NAME);
        UtilityPage.getTheFullScreen();
        float otherWidgetPositionXBeforeTransform = dashboardsPage.getWidgetPositionX(OTHER_WIDGET_NAME);
        float otherWidgetPositionYBeforeTransform = dashboardsPage.getWidgetPositionY(OTHER_WIDGET_NAME);

        dashboardsPage.resizeWidget(TEST_WIDGET_NAME, X_OFFSET, Y_OFFSET);
        float otherWidgetPositionXAfterTransform = dashboardsPage.getWidgetPositionX(OTHER_WIDGET_NAME);
        float otherWidgetPositionYAfterTransform = dashboardsPage.getWidgetPositionY(OTHER_WIDGET_NAME);
        Assert.assertNotEquals(otherWidgetPositionXBeforeTransform, otherWidgetPositionXAfterTransform);
        Assert.assertNotEquals(otherWidgetPositionYBeforeTransform, otherWidgetPositionYAfterTransform);

        dashboardsPage.resizeWidget(TEST_WIDGET_NAME, -X_OFFSET, -Y_OFFSET);
    }
}
