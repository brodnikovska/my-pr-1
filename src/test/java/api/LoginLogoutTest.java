package api;

import api_endpoints.User;
import com.epam.reportportal.testng.ReportPortalTestNGListener;
import utils.PropertyController;
import utils.StringHelper;
import entities.StatusCodes;
import org.hamcrest.Matchers;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

@Listeners({ReportPortalTestNGListener.class})
public class LoginLogoutTest extends BaseApiTestCase {
    private static final String USERNAME = PropertyController.getPropertyByKey("user.name");
    private static final String PASSWORD = PropertyController.getPropertyByKey("user.password");
    private static final String DASHBOARD_NAME = StringHelper.generateRandomString(5, StringHelper.getValidCharacters());
    private static final int SUCCESS = Integer.parseInt(StatusCodes.SUCCESS.toString());
    private static final int CREATED = Integer.parseInt(StatusCodes.CREATED.toString());
    private static final int NOT_FOUND = Integer.parseInt(StatusCodes.NOT_FOUND.toString());
    private static final int ID_TO_MODIFY = 18;
    private static final int NOT_EXISTING_ID = 1;
    private static final String NAME_TO_MODIFY = ID_TO_MODIFY + "_" + DASHBOARD_NAME;
    private String dashboardId;

    @Test()
    public void login() {
        /** TO DO */
        given()
                .spec(requestSpecification)
                .when()
                        .get(User.USER.toString())
                                .then()
                                        .statusCode(SUCCESS)
                .and()
                                                .body("userId", Matchers.equalTo(USERNAME));
    }


}
