package kitchenpos;

import static io.restassured.RestAssured.UNDEFINED_PORT;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("/db/migration/truncate_tables.sql")
public abstract class AcceptanceTest {
    @LocalServerPort
    int port;


    @BeforeEach
    public void beforeEach() {
        if (RestAssured.port == UNDEFINED_PORT) {
            RestAssured.port = port;
        }
    }
}