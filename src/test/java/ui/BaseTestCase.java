package ui;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.epam.reportportal.testng.ReportPortalTestNGListener;
import lombok.SneakyThrows;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import utils.PropertyController;
import org.testng.annotations.*;

import java.net.URL;
import java.time.Duration;

import static com.codeborne.selenide.Selenide.open;

@Listeners({ReportPortalTestNGListener.class})
public abstract class BaseTestCase {

    @SneakyThrows
    @BeforeMethod
    public void before() {
        String urlToRemoteWD = "http://localhost:4444/wd/hub";
        //ChromeOptions options = new ChromeOptions();
        EdgeOptions options = new EdgeOptions();
        RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD), options);
        WebDriverRunner.setWebDriver(driver);
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
