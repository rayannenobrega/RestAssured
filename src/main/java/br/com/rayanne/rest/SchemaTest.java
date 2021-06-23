package br.com.rayanne.rest;

import io.restassured.RestAssured;
import io.restassured.matcher.RestAssuredMatchers;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.Test;
import org.xml.sax.SAXParseException;

import static io.restassured.RestAssured.*;

public class SchemaTest {

    @Test
    public void deveValidarSchemaXML(){
        //Para essa validação é necessário um arquivo XSD que dirá qual é a formatação a ser validada. Para isso, entramos no site https://freeformatter.com/xsd-generator.html
        //Enviamos então nosso XML e ele formatou para nós. Incluímos um novo file em resources  colocamos o conteúdo gerado dentro.
        given()
                .log().all()
        .when()
                .get("https://restapi.wcaquino.me/usersXML")
                .then()
                .log().all()
                .statusCode(200)
                .body(RestAssuredMatchers.matchesXsdInClasspath("users.xsd")) //Esse matchers faz verificar se o que estamos pegando no get está no formato esperado do xsd.
        ;
    }

    @Test(expected = SAXParseException.class) //aqui é esperado que uma exceção seja lançada, por isso precisamos avisar no @test
    public void naoDeveValidarSchemaXMLInvalido(){
    //Esse teste possui como rota um arquivo que deve ser visto como inválido pelo xsd, pois não possui nome.
        given()
                .log().all()
        .when()
                .get("https://restapi.wcaquino.me/invalidUsersXML")
        .then()
                .log().all()
                .statusCode(200)
                .body(RestAssuredMatchers.matchesXsdInClasspath("users.xsd")) //Quando se deve validar a partir desse XSD a resposta esperada é que não se está válido.
        ;
    }

    @Test
    public void deveValidarJson(){
        //Para essa validação é necessário um arquivo JSON que dirá qual é a formatação a ser validada. Para isso, entramos no site https://jsonschema.net/home
        //Enviamos então nosso JSON e ele formatou para nós. Incluímos um novo file em resources  colocamos o conteúdo gerado dentro.
        //foi necessário incluir uma nova dependência no POM.
        given()
                .log().all()
        .when()
                .get("https://restapi.wcaquino.me/users")
        .then()
                .log().all()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("users.json")) //Esse matchers faz verificar se o que estamos pegando no get está no formato esperado do json.
        ;
    }
}
