package entities;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import org.openqa.selenium.JavascriptExecutor;

public abstract class UtilityPage {

    @Step
    public static void clickUsingJS(SelenideElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) WebDriverRunner.getWebDriver();
        executor.executeScript("arguments[0].click();", element);
    }

    @Step
    public static void getTheFullScreen() {
        WebDriverRunner.getWebDriver().manage().window().fullscreen();
    }
}
