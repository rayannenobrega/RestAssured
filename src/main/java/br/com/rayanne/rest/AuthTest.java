package br.com.rayanne.rest;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class AuthTest {

    @Test
    public void deveAcessarSWAPI(){
        given()
                .log().all()
                .when()
                .get("https://swapi.dev/api/people/1/")
                .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Luke Skywalker"))
        ;
    }
    @Test
    public void deveObterClima(){
        given()
                .log().all()
                .queryParam("q", "Fortaleza,BR")
                .queryParam("appid","bce591034fdad7846f91283bc7bc4f47" )
                .queryParam("units", "metric")
        .when()
                .get("https://api.openweathermap.org/data/2.5/weather")
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Fortaleza"))
                .body("coord.lon", is(-38.5247f))
                .body("main.temp", greaterThan(25f)) //f ao final pra indicar que é um float.
        ;
    }
}

