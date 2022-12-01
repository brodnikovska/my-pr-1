package ui;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.epam.reportportal.testng.ReportPortalTestNGListener;
import utils.PropertyController;
import org.testng.annotations.*;

import static com.codeborne.selenide.Selenide.open;

@Listeners({ReportPortalTestNGListener.class})
public abstract class BaseTestCase {

    @BeforeMethod
    public void before() {
        Configuration.browser = PropertyController.getPropertyByKey("driver");
        open(PropertyController.getPropertyByKey("base.url"));
    }

    @AfterMethod
    public void after() {
        WebDriverRunner.closeWebDriver();
    }
}
