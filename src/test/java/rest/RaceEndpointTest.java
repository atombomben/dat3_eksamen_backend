/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dtos.carRace.RaceDTO;
import entities.Race;
import errorhandling.API_Exception;
import facades.RaceFacade;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import static io.restassured.path.json.config.JsonParserType.GSON;
import io.restassured.specification.RequestSpecification;
import java.net.URI;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;
import utils.StartDataSet;

/**
 *
 * @author peter
 */
public class RaceEndpointTest {
    
    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;
    private static RaceFacade facade;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();



    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = RaceFacade.getRaceFacade(emf);

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
    
    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/race").then().statusCode(200);
    }
    
    //This test assumes the database contains two rows
    @Test
    public void testWelcomeMsg() throws Exception {
        given()
                .contentType("application/json")
                .get("/race/").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("msg", equalTo("Welcome to RacePage"));
    }
    

    @Test
    public void testShowAllRaces(){
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .get("/race/show").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("name", Matchers.hasItems("24 Hours of Le Mans", "Indianapolis 500", "Monaco Grand Prix"));
    }

    @Test
    public void testGetRaceById() throws API_Exception {
        List<Race> races = facade.getAllRacesFromEntity();
        Race race = facade.getRaceById(races.get(1).getId());
        
        given()
                .contentType("application/json")
                .get("/race/"+race.getId().intValue()).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("name", is(race.getName()));
    }
    
    @Test       //Shows the same Race 3 times and the specifik Cars
    public void testShowSpecifikCarsFromRace() throws API_Exception {
        List<Race> races = facade.getAllRacesFromEntity();
        Race race = facade.getRaceById(races.get(1).getId());
        
        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .when()
                .get("/race/"+race.getId().intValue()).then()
                .statusCode(200)
                .body("size()", is(6));
    }
    
    /*
    
    @Test
    public void testAddRace() {
       
       RaceDTO rDTO = new RaceDTO();
       rDTO.setName("Test1");
       rDTO.setDate("Test2");
       rDTO.setTime("Test3");
       rDTO.setLocation("Test4");
       
       String body = GSON.ToJson(rDTO);
       
        given()
        .contentType("application/json")
        .and()
        .body(body)
        .when()
        .post("/addrace")
        .then()
        .assertThat()
        .statusCode(HttpStatus.OK_200.getStatusCode());
    }
   
    
    
    @Test
    public void editRace() {
        
    }

    */
}
