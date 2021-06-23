package br.com.rayanne.rest;


import org.hamcrest.Matchers;
import org.junit.Test;

import java.io.File;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class FileTest {

    @Test
    public void deveObrigarEnvioArquivo() {
        given()
                .log().all()
        .when()
                .post("http://restapi.wcaquino.me/upload")
        .then()
                .log().all()
                .statusCode(404)//Deveria ser 400 - api foi mal configurada pelo dev
                .body("error", is("Arquivo não enviado"))
        ;
    }

    @Test
    public void deveFazerUploadArquivo(){
        //esse teste está falhando porque o arquivo não foi incluido no diretório interno do java.
        given()
                .log().all()
                .multiPart("arquivo", new File("C:\\Users\\Nany\\Documents\\Estudos")) //caminho absoluto, podemos colocar o relativo quando incluimos ele no projeto.
        .when()
                .post("http://restapi.wcaquino.me/upload")
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("users.pdf"))

        ;

    }
}
