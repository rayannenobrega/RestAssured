package br.com.rayanne.rest;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;

public class OlaMundo {
    public static void main(String[] args) {
        Response response = RestAssured.request(Method.GET, "http://restapi.wcaquino.me/ola");
        System.out.println(response.getBody().asString());
        //Acessa a URL acima através do request, passando um método Get e a URLe  retorna pra gente uma response. Essa response está sendo guardada em uma variável
        //A variável está sendo printada no sout, pegando o body e passando como uma string.
    }
}
