package br.com.rayanne.rest;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import org.junit.Assert;
import org.junit.Test;

import io.restassured.response.Response;

public class UserJsonTest {

    @Test
    public void deveVerificarPrimeiroNivel(){
        given()
        .when()
                .get("https://restapi.wcaquino.me/users/1")
        .then()
                .statusCode(200)
                .body("id",is(1))
                .body("name", containsString("Silva"))
                .body("age", greaterThan(18))
                ;
    }

    @Test
    public void deveVerificarPrimeiroNívelOutrasFormas(){
        Response response = (Response) RestAssured.request(Method.GET, "https://restapi.wcaquino.me/users/1");

        //Path
        Assert.assertEquals(new Integer(1), response.path("id"));
        Assert.assertEquals(new Integer(1), response.path("%s","id"));

        //JSONPath
        JsonPath jpath = new JsonPath(response.asString());
        Assert.assertEquals(1, jpath.getInt("id"));

        //from
        int id = JsonPath.from(response.asString()).getInt("id");
        Assert.assertEquals(1, id);
    }

    @Test
    public void deveVerificarSegundoNivel(){
        given()
        .when()
                .get("https://restapi.wcaquino.me/users/2")
        .then()
                .statusCode(200)
                .body("name", containsString("Joaquina"))
                .body("endereco.rua", is("Rua dos bobos"))
        ;

    }
}
