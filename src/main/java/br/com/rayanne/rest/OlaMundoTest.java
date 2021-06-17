package br.com.rayanne.rest;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Test;

public class OlaMundoTest {

    @Test
   public void testeOlaMundo(){
        Response response = RestAssured.request(Method.GET, "http://restapi.wcaquino.me/ola");
        Assert.assertTrue(response.getBody().asString().equals("Ola Mundo!"));
        Assert.assertTrue(response.statusCode() == 200);
        Assert.assertTrue("O status code deveria ser 200",response.statusCode() == 200 );
        //Mensagem mais clara em caso de algum erro. Esperado e atual.
        Assert.assertEquals(200,response.statusCode());


      ValidatableResponse validacao = response.then();
      validacao.statusCode(200);
    }
}
