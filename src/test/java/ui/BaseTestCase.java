package ui;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.epam.reportportal.testng.ReportPortalTestNGListener;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pageobjects.LoginPage;
import utils.PropertyController;
import org.testng.annotations.*;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.open;

@Listeners({ReportPortalTestNGListener.class})
public abstract class BaseTestCase {

    @BeforeMethod
    public void before() {
        Configuration.browser = PropertyController.getPropertyByKey("driver");
        open(PropertyController.getPropertyByKey("base.url"));
        WebDriverRunner.getWebDriver().manage().timeouts().implicitlyWait(Duration.ofMillis(3000));
        Configuration.reportsFolder = "target/selenide-reports";
        Configuration.timeout = 30000;
    }

    @AfterMethod
    public void after() {
        WebDriverRunner.closeWebDriver();
    }
}
