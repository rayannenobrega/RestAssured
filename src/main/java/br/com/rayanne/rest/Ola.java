package br.com.rayanne.rest;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class Ola {
    public static void main(String[] args) {
        Response response = RestAssured.request(Method.GET, "http://restapi.wcaquino.me/ola");
        System.out.println(response.getBody().asString().equals("Ola Mundo!"));
        System.out.println(response.statusCode() == 200);

        //ferramenta de validação do próprio RestAssured - A partir da resposta se faz um then. O then devolve uma validatableResponse que chamamos de validacao.
        //A partir da response obtemos um status code. A partir da validação passamos um statuscode e verificamos se o satatuscode passado é igual.
        ValidatableResponse validacao = response.then();
        validacao.statusCode(200);
    }

}
