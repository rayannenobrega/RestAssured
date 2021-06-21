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
                .rootPath("user")
                .body("name", is("Ana Julia"))
                .body("@id", is("3"))

                .rootPath("user.filhos")//define uma rota
                .body("name.size()", is(2)) // aqui não se usa "" porque esse dois não está vindo como um valor do XML, ele é seu tamanho relativo a uma chave do XML.

                .detachRootPath("filhos")//retira um nó da rota
                .body ("filhos.name[0]",is("Zezinho"))
                .body ("filhos.name[1]",is("Luizinho"))

                .appendRootPath("filhos")//acrescenta um nó da rota
                .body ("name",hasItem("Luizinho"))
                .body ("name",hasItems("Luizinho", "Zezinho"))
                ;
    }

    @Test
    public void devoFazerPesquisasAvancadasComXML(){
        given()
        .when()
                .get("https://restapi.wcaquino.me/usersXML")
        .then()
                .statusCode(200)
                .body("users.user.size()", is(3)).body("users.user.findAll{it.age.toInteger() <= 25}.size()", is(2))
                .body("users.user.@id", hasItems("1","2","3"))
                .body("user.user.find{it.age == 25}.name", is("Maria Joaquina"))
                .body("users.user.findAll{it.name.toString().contains('n')}.name", hasItems("Maria Joaquina", "Ana Julia"))
                .body("users.user.salary.find{it != null}.toDouble()", is(1234.5678))
                .body("users.user.age.collect{it.toInteger() * 2}", hasItems(40, 50, 60))
                .body("users.user.name.findAll{it.toString().startsWith('Maria')}.collect{it.toString().toUpperCase()}",is ("MARIA JOAQUINA"))
        ;

    }
}
