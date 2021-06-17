package br.com.rayanne.rest;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class OlaMundoTest {

    @Test
   public void testeOlaMundo(){
        Response response = request(Method.GET, "http://restapi.wcaquino.me/ola");
        Assert.assertTrue(response.getBody().asString().equals("Ola Mundo!"));
        Assert.assertTrue(response.statusCode() == 200);
        Assert.assertTrue("O status code deveria ser 200",response.statusCode() == 200 );
        //Mensagem mais clara em caso de algum erro. Esperado e atual.
        Assert.assertEquals(200,response.statusCode());


      ValidatableResponse validacao = response.then();
      validacao.statusCode(200);
    }

    @Test
    public void devoConhecerOutrasFormasRestAssured(){
        //Fez uma requisição do tipo GET nesse recurso/path (requisação)
        Response response = request(Method.GET, "http://restapi.wcaquino.me/ola");
        //Obteve o objeto de validação (coleta da resposta)
        ValidatableResponse validacao = response.then();
        //Pediu para verificar se o status code é 200 (validação)
        validacao.statusCode(200);


        //Mesmo método acima só encadeado em uma única linha. Importando o método statico ainda é possível tirar RestAssured do início, deixando apenas get.
        get("http://restapi.wcaquino.me/ola").then().statusCode(200);


        // Seguindo protocolo Given When e Then. Given = Condições pré-existentes/ When = Ação de fato / Then = Assertivas a serem validadas
        given()
        .when()
                .get("http://restapi.wcaquino.me/ola")
        .then()
                .statusCode(200);
    }

    @Test
    public void devoConhecerMatchersHamcrest(){
        Assert.assertThat("Maria", Matchers.is("Maria"));
        Assert.assertThat(128, Matchers.is(128));

        //Verificando se é um inteiro
        Assert.assertThat(128, Matchers.isA(Integer.class));
        Assert.assertThat(128d, Matchers.isA(Double.class));
        Assert.assertThat(128d, Matchers.greaterThan(120d));
        Assert.assertThat(128d, Matchers.lessThan(130d));

        List<Integer> impares = Arrays.asList(1,3,5,7,9);
        assertThat(impares, hasSize(5));
        assertThat(impares, contains(1,3,5,7,9));
        assertThat(impares, containsInAnyOrder(1,3,5,9,7));
        assertThat(impares, hasItem(1));
        assertThat(impares, hasItems(1,5));

        assertThat("Maria", is(not("João")));
        assertThat("Maria", not("João"));
        assertThat("Maria", anyOf(is("Maria"), is("Joaquina")));
        assertThat("Joaquina", allOf(startsWith("Joa"), endsWith("ina"), containsString("qui")));
    }
}
