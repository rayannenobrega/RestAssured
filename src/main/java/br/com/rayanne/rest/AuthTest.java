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
        //Inicialmente se fez o cadastro no site da API. Lá ele gerou uma key que podemos utilizar em nossos testes.
        //Essa chave precisa ser passada nos queryParams antes de iniciar o teste, é ela que verifica que você possui a autorização de acesso, caso contrário daria mensagem de erro.
       // No postman também é possível passar esses parâmetros nas pesquisas.
        given()
                .log().all()
                .queryParam("q", "Fortaleza,BR") //cidade pesquisada
                .queryParam("appid","bce591034fdad7846f91283bc7bc4f47" ) //chave passada pela API
                .queryParam("units", "metric") //convertendo a unidade (estava na documentação da API como alterar)
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

    @Test
    public void naoDeveAcessarSemSenha(){
        //Assim que aberta a pág ele pede usuário e senha. usuário: admin / senha: senha
        given()
                .log().all()
        .when()
                .get("https://restapi.wcaquino.me/basicauth")
        .then()
                .log().all()
                .statusCode(401)

        ;

    }

    @Test
    public void deveFazerAutenticacaoBasica(){
         given()
                .log().all()
         .when()
                .get("https://admin:senha@restapi.wcaquino.me/basicauth")//Dessa forma, colocando o login e a senha antes é possível fazer o acesso.
         .then()
                .log().all()
                .statusCode(200)
                 .body("status", is("logado"))

        ;

    }

    @Test
    public void deveFazerAutenticacaoBasica2(){
        given()
                .log().all()
                .auth().basic("admin","senha")//mesma forma de acesso do anterior só que informações passada logo no Given.
        .when()
                .get("https://restapi.wcaquino.me/basicauth")
        .then()
                .log().all()
                .statusCode(200)
                .body("status", is("logado"))

        ;

    }
    @Test
    public void deveFazerAutenticacaoBasicaChallenge(){
        //Em alguns casos, mesmo sendo uma autenticação básica é necessário incluir um comando a mais o preemptive().
        given()
                .log().all()
                .auth().preemptive().basic("admin","senha")//Basta incluir o preemptive() que a autenticação volta a funcionar.
        .when()
                .get("https://restapi.wcaquino.me/basicauth2")
        .then()
                .log().all()
                .statusCode(200)
                .body("status", is("logado"))

        ;

    }
}

