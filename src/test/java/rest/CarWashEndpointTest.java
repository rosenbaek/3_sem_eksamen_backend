package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import entities.WashingAssistants;
import facades.Facade;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;
import utils.StartDataSet;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CarWashEndpointTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;
    private static Facade facade;



    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = Facade.getFacade(emf);

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;

    }

    @AfterAll
    public static void closeTestServer() {
        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();

        httpServer.shutdownNow();
    }

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST

    @BeforeEach
    public void setUp() {
        StartDataSet.setupInitialData(emf);
    }

    //This is how we hold on to the token after login, similar to that a client must store the token somewhere
    private static String securityToken;


    //Utility method to login and set the returned securityToken
    private static void login(String username, String password) {
        String json = String.format("{username: \"%s\", password: \"%s\"}", username, password);
        securityToken = given()
                .contentType("application/json")
                .body(json)
                //.when().post("/api/login")
                .when().post("/login")
                .then()
                .extract().path("token");
        //System.out.println("TOKEN ---> " + securityToken);
    }

    private void logOut() {
        securityToken = null;
    }

    @Test
    public void test_VerifyConnection() {
        login(StartDataSet.user.getUserName(), "testUser");
        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .when().get("/carwash/verify")
                .then()
                .statusCode(200)
                .body("number", equalTo(3));
    }



    @Test
    public void test_AddBooking() {
        WashingAssistants w =  facade.getWashingAssistants().get(0); //Because id changes in unitTest
        String body = "{carReg:\"reg1\",dateTime:\"2022-01-25T11:26:00.000Z\",duration:0,washingAssistants:[{id:"+w.getId()+",name:\"name1\",primaryLanguage:\"danish\",rate:10,experience:0.5}]}";
        login(StartDataSet.user.getUserName(), "testUser");
        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .body(body)
                .when().post("/carwash/booking")
                .then()
                .statusCode(200)
                .body("bookings", hasSize(2));

    }
    @Test
    public void test_GetWashingAssistants() {
        login(StartDataSet.user.getUserName(), "testUser");
        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .when().get("/carwash/assistants")
                .then()
                .statusCode(200)
                .body("washingAssistants", hasSize(3));
    }
    @Test
    public void test_AddWashingAssistant() {
        String body = "{name:\"NewAssistant\",primaryLanguage:\"danish\",rate:10,experience:0.5}";
        login(StartDataSet.admin.getUserName(), "testAdmin");
        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .body(body)
                .when().post("/carwash/assistants")
                .then()
                .statusCode(200)
                .body("id", is(notNullValue()));

    }
    @Test
    public void test_GetAllCars() {
        login(StartDataSet.admin.getUserName(), "testAdmin");
        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .when().get("/carwash/cars")
                .then()
                .statusCode(200)
                .body("$", hasSize(3));
    }
}
