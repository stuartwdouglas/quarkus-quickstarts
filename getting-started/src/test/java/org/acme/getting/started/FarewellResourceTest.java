package org.acme.getting.started;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class FarewellResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
                .when().get("/bye")
                .then()
                .statusCode(200)
                .body(is("bye"));
    }

    @Test
    public void testGreetingEndpoint() {
        String uuid = UUID.randomUUID().toString();
        given()
                .pathParam("name", uuid)
                .when().get("/bye/farewell/{name}")
                .then()
                .statusCode(200)
                .body(is("bye " + uuid));
    }

}
