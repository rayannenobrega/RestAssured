package br.com.rayanne.rest;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;



public class UserXMLTest {

    @Test
    public void devoTrabalharComXML(){
        given()
        .when()
                .get("https://restapi.wcaquino.me/usersXML/3")
        .then()
                .statusCode(200)
                .body("user.name", is("Ana Julia"))
                .body("user.@id", is("3"))
                .body("user.filhos.name.size()", is(2)) // aqui não se usa "" porque esse dois não está vindo como um valor do XML, ele é seu tamanho relativo a uma chave do XML.
                .body ("user.filhos.name[0]",is("Zezinho"))
                .body ("user.filhos.name[1]",is("Luizinho"))
                .body ("user.filhos.name",hasItem("Luizinho"))
                .body ("user.filhos.name",hasItems("Luizinho", "Zezinho"))
                ;
    }
}
