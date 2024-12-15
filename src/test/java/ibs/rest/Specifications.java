package ibs.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Specifications {

    public static RequestSpecification requestSpecification(String url, Cookie cookie){

        return new RequestSpecBuilder()
                .setBaseUri(url)
                .addCookie(cookie)
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();
    }
    public static ResponseSpecification responseSpecification(int statusCode){

        return new ResponseSpecBuilder()
                .expectStatusCode(statusCode)
                .build();
    }
    public static void installSpecification
            (RequestSpecification requestSpecification, ResponseSpecification responseSpecification)
    {
        RestAssured.requestSpecification = requestSpecification;
        RestAssured.responseSpecification = responseSpecification;
    }
}
