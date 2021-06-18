package br.com.rayanne.rest;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import org.junit.Assert;
import org.junit.Test;

import io.restassured.response.Response;

import java.util.Arrays;

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

    @Test
    public void deveVerificarLista(){
        given()
        .when()
                .get("https://restapi.wcaquino.me/users/3")
        .then()
                .statusCode(200)
                .body("name", containsString("Ana"))
                .body("filhos", hasSize(2))
                .body("filhos[0].name", is("Zezinho"))
                .body("filhos[1].name", is("Luizinho"))
                .body("filhos.name", hasItem("Zezinho"))
                .body("filhos.name", hasItems("Zezinho", "Luizinho"))
        ;
    }
   @Test
    public void deveRetornarErroUsuarioInexistente(){
       given()
       .when()
               .get("https://restapi.wcaquino.me/users/4")
       .then()
               .statusCode(404)
               .body("error", is("Usuário inexistente"))
       ;
   }

   @Test
    public void deveVerificarListaRaiz(){
       given()
       .when()
               .get("https://restapi.wcaquino.me/users")
       .then()
               .statusCode(200)
               .body("$", hasSize(3))
               .body("name", hasItems("João da Silva", "Maria Joaquina","Ana Júlia")) //Devolve uma coleção com todos os nomes
               .body("age[1]", is(25))
               .body("filhos.name", hasItem(Arrays.asList("Zezinho", "Luizinho")))
               .body("salary", contains(1234.5678f, 2500, null))
       ;

   }
}
