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
    public void deveFazerUploadArquivo() {

        given()
                .log().all()
                .multiPart("arquivo", new File("src/main/resources/user.pdf")) //caminho relativo, documento inserido dentro do projeto.
                .when()
                .post("http://restapi.wcaquino.me/upload")
                .then()
                .log().all()
                .statusCode(200)
                .body("name", is("user.pdf"))

        ;

    }

    @Test
    public void naoDeveFazerUploadArquivoGrande() {
        //Arquivos maiores demoram mais para serem realizados, logo é melhor pegar um arquivo um pouco maior apenas do limite, para não prejudicar o tempo.
        given()
                .log().all()
                .multiPart("arquivo", new File("src/main/resources/teste.jpg")) //caminho ficional
        .when()
                .post("http://restapi.wcaquino.me/upload")
        .then()
                .log().all()
                .time(lessThan(5000L)) //Aqui você limita o tempo máximo da execução de um teste, ele é computado em MILISEGUNDOS.
                .statusCode(413)

        ;

    }
}
