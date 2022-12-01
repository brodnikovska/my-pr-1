package api;

import api_endpoints.Dashboard;
import com.epam.reportportal.testng.ReportPortalTestNGListener;
import utils.PropertyController;
import utils.StringHelper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.hamcrest.Matchers;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static java.nio.file.Files.newInputStream;
import static java.nio.file.Paths.*;

import entities.StatusCodes;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Slf4j
@Listeners({ReportPortalTestNGListener.class})
public class CreateModifyRemoveDashboardTest extends BaseApiTestCase {
    private static final String PROJECT_NAME = PropertyController.getPropertyByKey("rp.project");
    private static final String DASHBOARD_NAME = StringHelper.generateRandomString(5, StringHelper.getValidCharacters());
    private static final int SUCCESS = Integer.parseInt(StatusCodes.SUCCESS.toString());
    private static final int CREATED = Integer.parseInt(StatusCodes.CREATED.toString());
    private static final int NOT_FOUND = Integer.parseInt(StatusCodes.NOT_FOUND.toString());
    private static final int ID_TO_MODIFY = 18;
    private static final String NAME_TO_MODIFY = ID_TO_MODIFY + "_" + DASHBOARD_NAME;
    private final InputStream newSchema;
    private final InputStream updatedSchema;
    private int dashboardId;

    {
        try {
            updatedSchema = newInputStream(get(new File("").getAbsolutePath() + "/src/main/java/json/updated-dashboard.json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            newSchema = newInputStream(get(new File("").getAbsolutePath() + "/src/main/java/json/new-dashboard.json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    @Test()
    public void createNewDashboard() {
        dashboardId = given().spec(requestSpecification)
                    .body(String.format(IOUtils.toString(newSchema, StandardCharsets.UTF_8), DASHBOARD_NAME, DASHBOARD_NAME))
                    .post(Dashboard.DASHBOARD.toString(), PROJECT_NAME)
                    .then()
                    .statusCode(CREATED)
               .extract()
               .body()
               .path("id");
    }

    @Test(dependsOnMethods = "createNewDashboard")
    public void deleteDashboard() {
       given().spec(requestSpecification)
                .delete(Dashboard.DASHBOARD_ID.toString(), PROJECT_NAME, dashboardId)
                .then()
                .statusCode(SUCCESS);

        given().spec(requestSpecification)
                .get(Dashboard.DASHBOARD_ID.toString(), PROJECT_NAME, dashboardId)
                .then()
                .statusCode(NOT_FOUND);
    }

    @SneakyThrows
    @Test()
    public void modifyDashboard() {
        given().spec(requestSpecification)
                .get(Dashboard.DASHBOARD_ID.toString(), PROJECT_NAME, ID_TO_MODIFY)
                .then()
                .statusCode(SUCCESS)
                .and()
                .body("name", Matchers.not(Matchers.equalTo(NAME_TO_MODIFY)));

        given().spec(requestSpecification)
                .body(String.format(IOUtils.toString(updatedSchema, StandardCharsets.UTF_8), DASHBOARD_NAME, ID_TO_MODIFY, NAME_TO_MODIFY))
                .put(Dashboard.DASHBOARD_ID.toString(), PROJECT_NAME, Integer.toString(ID_TO_MODIFY))
                .then()
                .statusCode(SUCCESS);

        given().spec(requestSpecification)
                .get(Dashboard.DASHBOARD_ID.toString(), PROJECT_NAME, ID_TO_MODIFY)
                .then()
                .statusCode(SUCCESS)
                .and()
                .body("name", Matchers.equalTo(NAME_TO_MODIFY));
    }
}
