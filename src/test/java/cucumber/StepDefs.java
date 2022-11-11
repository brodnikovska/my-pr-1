package cucumber;

import com.codeborne.selenide.Configuration;
import entities.LaunchesMenus;
import entities.LeftSideBar;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pageobjects.LaunchesPage;
import pageobjects.LeftSidePanel;
import pageobjects.LoginPage;

import java.util.List;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.page;

public class StepDefs {

    private LoginPage loginPage = page(LoginPage.class);
    private LeftSidePanel leftSidePanel = page(LeftSidePanel.class);
    private LaunchesPage launchesPage = page(LaunchesPage.class);

    @Given("User opens {string} and navigates to {string}")
    public void userOpensEdgeAndNavigatesToHttpLocalhost(String browser, String url) {
        Configuration.browser = browser;
        open(url);
    }

    @When("User enters {string} and {string}")
    public void userEntersUsernameAndPassword(String username, String password) {
        loginPage
                .setUsername(username)
                .setPassword(password)
                .submitLogin();
    }

    @And("User is logged in")
    public void userIsLoggedIn() {
        loginPage.verifyLoginSuccessful();
    }

    @Then("Left side panel is visible")
    public void leftSidePanelIsVisible() {
        Assert.assertEquals(leftSidePanel.getSideBarIconsTitles(), LeftSideBar.getValues());
    }

    @And("User selects launches")
    public void userSelectsLaunches() {
        leftSidePanel
                .selectButton(LeftSideBar.LAUNCHES.toString());
    }

    @Then("Launch menus are visible")
    public void launchMenusAreVisible() {
        Assert.assertEquals(launchesPage.getLaunchesMenuTitles(), LaunchesMenus.getValues());
    }

    @Then("Following icons are present in left side bar")
    public void followingIconsArePresentInLeftSideBar(List<String> icons) {
        Assert.assertEquals(leftSidePanel.getSideBarIconsTitles(),icons);
    }

    @When("User passes {string} and {string}")
    public void userPassesUsernameAndPassword(String username, String password) {
        loginPage
                .setUsername(username)
                .setPassword(password)
                .submitLogin();
    }
}
