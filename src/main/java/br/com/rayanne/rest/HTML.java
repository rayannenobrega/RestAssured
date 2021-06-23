package br.com.rayanne.rest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class HTML {

    @Test
    public void deveFazerBuscasComHTML(){
        //navegamos dentro do html através de "."
        given()
                .log().all()
        .when()
                .get("https://restapi.wcaquino.me/v2/users")
        .then()
                .log().all()
                .statusCode(200)
                .contentType(ContentType.HTML)
                .body("html.body.div.table.tbody.tr.size()", is(3))
                .body("html.body.div.table.tbody.tr[1].td[2]", is("25"))
                .appendRootPath("html.body.div.table.tbody")
                .body("tr.find{it.toString().startsWith('2)}.td[1]", is("Maria Joaquina")) //usando o iterador facilita, lembra um xpath.
        ;
    }
}
