package api;

import com.epam.reportportal.testng.ReportPortalTestNGListener;
import utils.PropertyController;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import lombok.extern.log4j.Log4j;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;

@Listeners({ReportPortalTestNGListener.class})
@Log4j
public abstract class BaseApiTestCase {
    RequestSpecification requestSpecification;

    @BeforeTest
    public void initialiseConfig() {
        RestAssured.baseURI = PropertyController.getPropertyByKey("base.url.api");
        requestSpecification = new RequestSpecBuilder()
                .addHeader("Authorization", "Bearer " + PropertyController.getPropertyByKey("rp.uuid"))
                .addHeader("Accept", "*/*")
                .addHeader("Content-Type", "application/json")
                .log(LogDetail.ALL)
                .build();
    }

}
