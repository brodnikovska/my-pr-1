package pageobjects;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import entities.UtilityPage;
import entities.LaunchesMenus;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Getter
public class LaunchesPage {
    private static Logger logger = LoggerFactory.getLogger(LaunchesPage.class);

    @FindBy(xpath = "//span[contains(@class,'headerCell__title-full')]")
    private ElementsCollection menus;

    @FindBy(xpath = "//div[contains(@class,'gridRow__grid-row-wrapper')]//div[contains(@class,'launchSuiteGrid__name')]/div/div/a")
    private ElementsCollection testRuns;

    @Step
    public List<String> getLaunchesMenuTitles() {
        UtilityPage.getTheFullScreen();
        Selenide.screenshot(Long. toString(System.currentTimeMillis()));
        menus.get(0).shouldBe(Condition.exist, Duration.ofMillis(12000));
        return menus.stream()
                .filter(e -> !e.text().isEmpty())
                .map(SelenideElement::getText)
                .collect(Collectors.toList());
    }

    @Step
    public LaunchesPage openRandomTestResults() {
        testRuns.get(0).shouldBe(Condition.visible, Duration.ofMillis(15000));
        int numberOfLaunches = testRuns.size();
        Random random = new Random();
        try {
            int randomNumberFromTheTop = random.nextInt(numberOfLaunches);
            UtilityPage.clickUsingJS(testRuns.get(randomNumberFromTheTop));
        } catch (IllegalArgumentException e) {
            e.toString();
        }
        return this;
    }

}
