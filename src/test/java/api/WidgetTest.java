package api;

import api_endpoints.Widget;
import com.epam.reportportal.testng.ReportPortalTestNGListener;
import utils.PropertyController;
import utils.StringHelper;
import entities.StatusCodes;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.hamcrest.Matchers;
import org.testng.annotations.Ignore;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static io.restassured.RestAssured.given;
import static java.nio.file.Files.newInputStream;
import static java.nio.file.Paths.get;

@Slf4j
@Listeners({ReportPortalTestNGListener.class})
public class WidgetTest extends BaseApiTestCase {
    private static final String PROJECT_NAME = PropertyController.getPropertyByKey("rp.project");
    private static final String WIDGET_NAME = StringHelper.generateRandomString(5, "aAbBcCdDeEfFgGhHiIgGkKlLmMnNoOpP");
    private static final int SUCCESS = Integer.parseInt(StatusCodes.SUCCESS.toString());
    private static final int CREATED = Integer.parseInt(StatusCodes.CREATED.toString());
    private static final int NOT_FOUND = Integer.parseInt(StatusCodes.NOT_FOUND.toString());
    private static final int ID_TO_MODIFY = 120;
    private final InputStream newSchema;
    private final InputStream updatedSchema;
    private int widgetId;

    {
        try {
            updatedSchema = newInputStream(get(new File("").getAbsolutePath() + "/src/main/java/json/updated-widget.json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            newSchema = newInputStream(get(new File("").getAbsolutePath() + "/src/main/java/json/new-widget.json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    @Test()
    public void createNewWidget() {
        widgetId = given().spec(requestSpecification)
                    .body(String.format(IOUtils.toString(newSchema, StandardCharsets.UTF_8), 5, WIDGET_NAME))
                    .post(Widget.WIDGET.toString(), PROJECT_NAME)
                    .then()
                    .statusCode(CREATED)
                .extract()
                .body()
                .path("id");

        given().spec(requestSpecification)
                .get(Widget.WIDGET_ID.toString(), PROJECT_NAME, widgetId)
                .then()
                .statusCode(SUCCESS);
    }

    /** Test is not eligible as DELETE 405 Method Not Allowed*/
    @Ignore
    @Test(dependsOnMethods = "createNewWidget")
    public void deleteWidget() {
        given().spec(requestSpecification)
                .delete(Widget.WIDGET_ID.toString(), PROJECT_NAME, widgetId)
                .then()
                .statusCode(SUCCESS);

        given().spec(requestSpecification)
                .get(Widget.WIDGET_ID.toString(), PROJECT_NAME, widgetId)
                .then()
                .statusCode(NOT_FOUND);
    }

    @SneakyThrows
    @Test()
    public void modifyDashboard() {
        given().spec(requestSpecification)
                .get(Widget.WIDGET_ID.toString(), PROJECT_NAME, ID_TO_MODIFY)
                .then()
                .statusCode(SUCCESS)
                .and()
                .body("name", Matchers.not(Matchers.containsString(WIDGET_NAME)));

        given().spec(requestSpecification)
                .body(String.format(IOUtils.toString(updatedSchema, StandardCharsets.UTF_8), WIDGET_NAME))
                .put(Widget.WIDGET_ID.toString(), PROJECT_NAME, ID_TO_MODIFY)
                .then()
                .statusCode(SUCCESS);

        given().spec(requestSpecification)
                .get(Widget.WIDGET_ID.toString(), PROJECT_NAME, ID_TO_MODIFY)
                .then()
                .statusCode(SUCCESS)
                .and()
                .body("name", Matchers.containsString(WIDGET_NAME));
    }
}
