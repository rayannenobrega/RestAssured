package br.com.rayanne.rest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Test;

import static io.restassured.RestAssured.*;

public class EnvioDadosTest {

    @Test
    public void deveEnviarValorViaQuery(){

        given()
                .log().all()
        .when()
                .get("https://restapi.wcaquino.me/v2/users?format=json")
        .then()
                .log().all()
                .statusCode(200)
                .contentType(ContentType.JSON)
        ;
    }
}
