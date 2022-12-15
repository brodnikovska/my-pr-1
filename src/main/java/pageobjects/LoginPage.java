package pageobjects;

import beans.User;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import entities.PageMessages;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

@Getter
public class LoginPage {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginPage.class);

    @FindBy(xpath = "//button[text()='Login']")
    private SelenideElement loginButton;

    @FindBy(xpath = "//input[@placeholder='Login']")
    private SelenideElement loginInput;

    @FindBy(xpath = "//input[@placeholder='Password']")
    private SelenideElement passwordInput;

    private static final String PAGE_MESSAGE = "//p[text()='%s']";

    @Step
    public LoginPage login(User user) {
        setUsername(user);
        setPassword(user);
        submitLogin();
        return this;
    }

    @Step
    public LoginPage setUsername(User user) {
        LOGGER.info(String.format("Login as %s", user.toString()));
        loginInput.setValue(user.getUsername());
        LOGGER.info(String.format("%s username has been set up", user.getUsername()));
        return this;
    }

    @Step
    public LoginPage setUsername(String username) {
        loginInput.setValue(username);
        return this;
    }

    @Step
    public LoginPage setPassword(User user) {
        passwordInput.shouldBe(Condition.visible, Duration.ofMillis(6000)).setValue(user.getPassword());
        LOGGER.info(String.format("%s password has been set up", user.getPassword()));
        return this;
    }

    @Step
    public LoginPage setPassword(String password) {
        passwordInput.shouldBe(Condition.visible, Duration.ofMillis(6000)).setValue(password);
        return this;
    }

    @Step
    public LoginPage submitLogin() {
        loginButton.shouldBe(Condition.visible, Duration.ofMillis(6000)).click();
        return this;
    }

    @Step
    public LoginPage verifyLoginSuccessful() {
        loginButton.shouldNotBe(Condition.exist, Duration.ofMillis(6000));
        $x(String.format(PAGE_MESSAGE, PageMessages.LOGIN_SUCCESSFUL)).shouldBe(Condition.visible);
        $x(String.format(PAGE_MESSAGE, PageMessages.LOGIN_SUCCESSFUL)).shouldBe(Condition.disappear, Duration.ofMillis(10000));
        LOGGER.info(PageMessages.LOGIN_SUCCESSFUL.toString());
        return this;
    }

    @Step
    public LoginPage verifyNoLogin() {
        loginButton.shouldBe(Condition.exist, Duration.ofMillis(6000));
        $x(String.format(PAGE_MESSAGE, PageMessages.LOGIN_SUCCESSFUL)).shouldNotBe(Condition.visible);
        LOGGER.info("No login was detected");
        return this;
    }

    @Step
    public LoginPage verifyLoginUnsuccessful() {
        loginButton.shouldBe(Condition.exist, Duration.ofMillis(6000));
        $x(String.format(PAGE_MESSAGE, PageMessages.BAD_CREDENTIALS)).shouldBe(Condition.visible);
        LOGGER.info(PageMessages.BAD_CREDENTIALS.toString());
        return this;
    }
}
