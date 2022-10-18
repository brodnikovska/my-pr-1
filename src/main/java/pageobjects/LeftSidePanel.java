package pageobjects;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import entities.LeftSideBar;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.$x;

@Getter
public class LeftSidePanel {
    private static Logger logger = LoggerFactory.getLogger(LeftSidePanel.class);

    @FindBy(xpath = "//a[contains(@class,'sidebarButton') and contains(@href,'personal')]")
    private ElementsCollection sideBarIcons;

    private final static String SIDE_BAR_ICON = "//a[contains(@class,'sidebarButton') and contains(@href,'personal')]//following::span[text()='%s']/..";

    @Step
    public List<String> getSideBarIconsTitles() {
        return sideBarIcons.stream().map(SelenideElement::innerText).collect(Collectors.toList());
    }

    @Step
    public LeftSidePanel selectButton(String icon) {
        $x(String.format(SIDE_BAR_ICON, icon)).click();
        return this;
    }

}
