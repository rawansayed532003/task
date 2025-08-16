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

        // Base URI
        RestAssured.baseURI = "https://opensource-demo.orangehrmlive.com/web/index.php/api/v2/recruitment";

        // ----- إضافة المرشح -----
        String addRequestBody = "{\n" +
                "  \"firstName\": \"test\",\n" +
                "  \"lastName\": \"y\",\n" +
                "  \"email\": \"admin@example.com\",\n" +
                "  \"contactNumber\": \"0100000000\",\n" +
                "  \"vacancyId\": 5,\n" +
                "  \"keywords\": \"automation, testing\",\n" +
                "  \"consentToKeepData\": true\n" +
                "}";

        Response addResponse = given()
                .header("Content-Type", "application/json")
                .header("Cookie", "orangehrm=gugsbo7mdt57ihe4id1u4ste6g") // ضعي الكوكي هنا
                .body(addRequestBody)
                .when()
                .post("/candidates")
                .then()
                .extract().response();

        logger.info("Add Status Code: {}", addResponse.statusCode());
        logger.info("Add Response Body: {}", addResponse.asString());

        // جلب الـ ID للمرشح المضاف
        int candidateId = addResponse.jsonPath().getInt("data.id");
        logger.info("Candidate ID to delete: {}", candidateId);

        // ----- حذف المرشح -----
        Response deleteResponse = given()
                .header("Content-Type", "application/json")
                .header("Cookie", "orangehrm=gugsbo7mdt57ihe4id1u4ste6g")
                .when()
                .delete("/candidates/" + candidateId)
                .then()
                .extract().response();

        logger.info("Delete Status Code: {}", deleteResponse.statusCode());
        logger.info("Delete Response Body: {}", deleteResponse.asString());
    }
}
