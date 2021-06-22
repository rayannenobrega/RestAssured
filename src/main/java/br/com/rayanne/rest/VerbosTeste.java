package br.com.rayanne.rest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class VerbosTeste {

    @Test
    public void deveSalvarUsuario() {
        given()
                .log().all()
                .contentType("application/json")
                .body("{\"name\":\"Jose\",\"age\":50}")
        .when()
                .post("https://restapi.wcaquino.me/users")
        .then()
                .log().all()
                .statusCode(201)
                .body("id", is(notNullValue()))
                .body("name", is("Jose"))
                .body("age", is(50))
        ;
    }

    @Test
    public void naoDeveSalvarUsuarioSemNome(){
        given()
                .log().all()
                .contentType("application/json")
                .body("{\"age\":50}")
        .when()
                .post("https://restapi.wcaquino.me/users")
        .then()
                .log().all()
                .statusCode(400)
                .body("id", is(nullValue()))
                .body("error", is("Name é um atributo obrigatório"))

        ;
    }

    @Test
    public void deveSalvarUsuarioViaXML() {
        given()
                .log().all()
                .contentType(ContentType.XML)
                .body("<user><name>Jose</name><age>50</age></user>")
        .when()
                .post("https://restapi.wcaquino.me/usersXML")
        .then()
                .log().all()
                .statusCode(201)
                .body("user.@id", is(notNullValue()))
                .body("user.name", is("Jose"))
                .body("user.age", is("50"))
        ;
    }

    @Test
    public void deveAlterarUsuario() {
        given()
                .log().all()
                .contentType("application/json")
                .body("{\"name\":\"Usuario alterado\",\"age\":80}")
        .when()
                .put("https://restapi.wcaquino.me/users/1")
        .then()
                .log().all()
                .statusCode(200)
                .body("id", is(1))
                .body("name", is("Usuario alterado"))
                .body("age", is(80))
                .body("salary", is(1234.5678f))
        ;
    }

    @Test
    public void devoCustomizarURL() {
        given()
                .log().all()
                .contentType("application/json")
                .body("{\"name\":\"Usuario alterado\",\"age\":80}")
        .when()
                .put("https://restapi.wcaquino.me/{entidade}/{userId}", "users", "1")
        .then()
                .log().all()
                .statusCode(200)
                .body("id", is(1))
                .body("name", is("Usuario alterado"))
                .body("age", is(80))
                .body("salary", is(1234.5678f))
        ;
    }

    @Test
    public void devoCustomizarURLParte2() {
        given()
                .log().all()
                .contentType("application/json")
                .body("{\"name\":\"Usuario alterado\",\"age\":80}")
                .pathParam("entidade", "users")
                .pathParam("userId", "1")
        .when()
                .put("https://restapi.wcaquino.me/{entidade}/{userId}")
        .then()
                .log().all()
                .statusCode(200)
                .body("id", is(1))
                .body("name", is("Usuario alterado"))
                .body("age", is(80))
                .body("salary", is(1234.5678f))
        ;
    }

    @Test
    public void deveRomoverUsuario(){
        given()
                .log().all()
        .when()
                .delete("https://restapi.wcaquino.me/users/1")
        .then()
                .log().all()
                .statusCode(204)
        ;
    }

    @Test
    public void naoDeveRomoverUsuarioInexistente(){
        given()
                .log().all()
        .when()
                .delete("https://restapi.wcaquino.me/users/1000")
        .then()
                .log().all()
                .statusCode(400)
                .body("error",is("Registro inexistente"))
        ;
    }

    @Test
    public void deveSalvarUsuarioUsandoMap() {
        //um map é como se fosse uma lista, só que ele armazena pares
        //É necessário incluir no POM a biblioteca GSON, ou ele dirá que não encontrou o objeto devido a não serialização no JSON.

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", "Usuario via map");
        params.put("age", 25);

        given()
                .log().all()
                .contentType("application/json")
                .body(params) //O rest entende que por estar enviando pares de chaves e valor e contentType acima dizendo que ele é um JSON ele faz a conversão sozinho.
        .when()
                .post("https://restapi.wcaquino.me/users")
        .then()
                .log().all()
                .statusCode(201)
                .body("id", is(notNullValue()))
                .body("name", is("Usuario via map"))
                .body("age", is(25))
        ;
    }

    @Test
    public void deveSalvarUsuarioUsandoObjeto() {
        //Foi criada uma nova classe User para realizar esse teste, onde ele tem todos os gets e sets e o construtor padrão com name e age.
        // O objeto é instaciado, passando os parâmetros necessários pedidos pelo construtor.
        //Pode parecer complicado criar um objeto somente pra usá-lo aqui, mas provavelmente esse objeto já existe em uma aplicação real, então podemos reaproveitá-la aqui.

        User user = new User ("Usuario via objeto", 35);

        given()
                .log().all()
                .contentType("application/json")
                .body(user)
        .when()
                .post("https://restapi.wcaquino.me/users")
        .then()
                .log().all()
                .statusCode(201)
                .body("id", is(notNullValue()))
                .body("name", is("Usuario via objeto"))
                .body("age", is(35))
        ;
    }

    @Test
    public void deveDeserializarObjetoAoSalvarUsuario() {
        //convertendo o JSON que volta da nossa requisição em um objeto

        User user = new User ("Usuario deserializado", 35);

        //Importante lembrar que como é um teste encadeado é necessário guardar a variável desde cima.
        User usuarioInserido = given()
                .log().all()
                .contentType("application/json")
                .body(user)
        .when()
                .post("https://restapi.wcaquino.me/users")
        .then()
                .log().all()
                .statusCode(201)
                .extract().body().as(User.class)
        ;
        System.out.println(usuarioInserido);
        Assert.assertEquals("Usuario deserializado", usuarioInserido.getName());
        Assert.assertThat(usuarioInserido.getAge(), is(35));
        Assert.assertThat(usuarioInserido.getId(), notNullValue());
    }

    @Test
    public void deveSalvarUsuarioViaXMLUsandoObjeto() {

        User user = new User("Usuario XML", 40);

        given()
                .log().all()
                .contentType(ContentType.XML)
                .body(user)
       .when()
                .post("https://restapi.wcaquino.me/usersXML")
       .then()
                .log().all()
                .statusCode(201)
                .body("user.@id", is(notNullValue()))
                .body("user.name", is("Usuario XML"))
                .body("user.age", is("40"))
       ;
    }

    @Test
    public void deveDeserializarXMLAoSalvarUsuario() {

        User user = new User("Usuario XML", 40);

        User usuarioInserido = given()
                .log().all()
                .contentType(ContentType.XML)
                .body(user)
        .when()
                .post("https://restapi.wcaquino.me/usersXML")
        .then()
                .log().all()
                .statusCode(201)
                .extract().body().as(User.class)
        ;

        Assert.assertThat(usuarioInserido.getId(), notNullValue());
        Assert.assertThat(usuarioInserido.getName(), is("Usuario XML"));
        Assert.assertThat(usuarioInserido.getAge(), is(40));
        Assert.assertThat(usuarioInserido.getSalary(), nullValue());
    }
}


