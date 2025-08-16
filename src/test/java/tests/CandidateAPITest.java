package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class CandidateAPITest {

    private static final Logger logger = LoggerFactory.getLogger(CandidateAPITest.class);

    @Test
    public void addAndDeleteCandidateTest() {


        RestAssured.baseURI = "https://opensource-demo.orangehrmlive.com/web/index.php/api/v2/recruitment";


        String cookie = "\n" +
                "orangehrm=dc02jubnkiuesep5kumbq70mms";


        String addRequestBody = "{\n" +
                "  \"firstName\": \"test\",\n" +
                "  \"lastName\": \"y\",\n" +
                "  \"email\": \"admin@example.com\",\n" +
                "  \"contactNumber\": \"0100000000\",\n" +
                "  \"vacancyId\": 5,\n" +
                "  \"keywords\": \"automation testing\",\n" +
                "  \"consentToKeepData\": true\n" +
                "}";


        Response addResponse = given()
                .header("Content-Type", "application/json")
                .header("Cookie", cookie)
                .body(addRequestBody)
                .when()
                .post("/candidates")
                .then()
                .extract().response();

        logger.info("Add Status Code: {}", addResponse.statusCode());
        logger.info("Add Response Body: {}", addResponse.asString());


        int candidateId = addResponse.jsonPath().getInt("data.id");
        logger.info("Candidate ID to delete: {}", candidateId);

        // م DELETE endpoint غير مدعوم من قبل API لذلك حذف الـ candidate غير ممكن
        Response deleteResponse = given()
                .header("Content-Type", "application/json")
                .header("Cookie", cookie)
                .when()
                .delete("/candidates/" + candidateId)
                .then()
                .extract().response();

        logger.info("Delete Status Code: {}", deleteResponse.statusCode());
        logger.info("Delete Response Body: {}", deleteResponse.asString());
    }
}
