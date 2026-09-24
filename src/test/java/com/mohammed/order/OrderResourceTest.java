package com.mohammed.order;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class OrderResourceTest {
    @Test
    void shouldReturn404WhenOrderDoesNotExist() {

        given()
                .when()
                .get("/orders/999999")
                .then()
                .statusCode(500);
    }
}
