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
    private static Logger logger = LoggerFactory.getLogger(LoginPage.class);

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
        logger.info("Login as " + user.toString());
        loginInput.setValue(user.getUsername());
        logger.info(user.getUsername() + " username has been set up");
        return this;
    }

    @Step
    public LoginPage setPassword(User user) {
        passwordInput.shouldBe(Condition.visible).setValue(user.getPassword());
        logger.info(user.getPassword() + " password has been set up");
        return this;
    }

    @Step
    public LoginPage submitLogin() {
        loginButton.shouldBe(Condition.visible).click();
        return this;
    }

    @Step
    public LoginPage verifyLoginSuccessful() {
        loginButton.shouldNotBe(Condition.exist);
        $x(String.format(PAGE_MESSAGE, PageMessages.LOGIN_SUCCESSFUL)).shouldBe(Condition.visible);
        $x(String.format(PAGE_MESSAGE, PageMessages.LOGIN_SUCCESSFUL)).shouldBe(Condition.disappear, Duration.ofMillis(10000));
        logger.info(PageMessages.LOGIN_SUCCESSFUL.toString());
        return this;
    }

    @Step
    public LoginPage verifyNoLogin() {
        loginButton.shouldBe(Condition.exist);
        $x(String.format(PAGE_MESSAGE, PageMessages.LOGIN_SUCCESSFUL)).shouldNotBe(Condition.visible);
        logger.info("No login was detected");
        return this;
    }

    @Step
    public LoginPage verifyLoginUnsuccessful() {
        loginButton.shouldBe(Condition.exist);
        $x(String.format(PAGE_MESSAGE, PageMessages.BAD_CREDENTIALS)).shouldBe(Condition.visible);
        logger.info(PageMessages.BAD_CREDENTIALS.toString());
        return this;
    }
}
