package pageobjects;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import entities.UtilityPage;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;

@Getter
public class DashboardsPage {
    private static Logger logger = LoggerFactory.getLogger(DashboardsPage.class);
    private static final String WIDGET_DRAG_AND_DROP = "(//div[text()='%s']/../../../../..//following::span[@class='react-resizable-handle react-resizable-handle-se'])[1]";
    private static final String WIDGET_WINDOW = "//div[text()='%s']/../../../../../..";
    private static final String GRID_ROW = "//div[text()='%s']/../../../../../..//div[contains(@class,'gridRow__grid-row-wrapper')]";
    private static final String DASHBOARD = "(//a[text()='%s'])[2]";

    @Step
    public DashboardsPage openDashboard(String dashboardName) {
        $x(String.format(DASHBOARD, dashboardName)).shouldBe(Condition.visible, Duration.ofMillis(10000));
        $x(String.format(DASHBOARD, dashboardName)).click();
        UtilityPage.getTheFullScreen();
        return this;
    }

    @Step
    public DashboardsPage resizeWidget(String widgetName, int pixelsX, int pixelsY) {
        UtilityPage.resizeWindow($x(String.format(WIDGET_DRAG_AND_DROP, widgetName)), pixelsX, pixelsY);
        return this;
    }

    @Step
    public DashboardsPage scrollToTestRun(String widgetName, int index) {
        UtilityPage.scrollToViewUsingJS($$x(String.format(GRID_ROW, widgetName)).get(index));
        return this;
    }

    @Step
    public int getNumberOfVisibleRuns(String widgetName) {
        $$x(String.format(GRID_ROW, widgetName)).first().shouldBe(Condition.visible, Duration.ofMillis(6000));
        return $$x(String.format(GRID_ROW, widgetName)).filter(Condition.visible).size();
    }

    @Step
    public Long getYPositionOfTestRun(String widgetName, int index) {
        return UtilityPage.getPositionOfElementUsingJS($$x(String.format(GRID_ROW, widgetName)).get(index));
    }

    @Step
    public int getWidgetWidth(String widgetName) {
        $x(String.format(WIDGET_WINDOW, widgetName)).shouldBe(Condition.visible, Duration.ofMillis(6000));
        return Integer.parseInt($x(String.format(WIDGET_WINDOW, widgetName))
                .getCssValue("width")
                .replace("px", ""));
    }

    @Step
    public int getWidgetHeight(String widgetName) {
        $x(String.format(WIDGET_WINDOW, widgetName)).shouldBe(Condition.visible, Duration.ofMillis(6000));
        return Integer.parseInt($x(String.format(WIDGET_WINDOW, widgetName))
                .getCssValue("height")
                .replace("px", ""));
    }

    @Step
    public float getWidgetPositionX(String widgetName) {
        $x(String.format(WIDGET_WINDOW, widgetName)).shouldBe(Condition.visible, Duration.ofMillis(6000));
        String coordinates = $x(String.format(WIDGET_WINDOW, widgetName))
                .getCssValue("transform")
                .replace("matrix(1, 0, 0, 1, ", "")
                .replace(")", "")
                .replace(" ", "");
        int comma = coordinates.indexOf(",");
        return Float.parseFloat(coordinates.substring(0, comma));
    }

    @Step
    public float getWidgetPositionY(String widgetName) {
        $x(String.format(WIDGET_WINDOW, widgetName)).shouldBe(Condition.visible, Duration.ofMillis(6000));
        String coordinates = $x(String.format(WIDGET_WINDOW, widgetName))
                .getCssValue("transform")
                .replace("matrix(1, 0, 0, 1, ", "")
                .replace(")", "")
                .replace(" ", "");
        int comma = coordinates.indexOf(",");
        return Float.parseFloat(coordinates.substring(comma + 1));
    }

    @Step
    public DashboardsPage dragAndDropWidget(String widgetName) {
        $x(String.format(WIDGET_WINDOW, widgetName)).shouldBe(Condition.visible, Duration.ofMillis(6000));
        $x(String.format(DASHBOARD, widgetName)).click();
        return this;
    }

}
