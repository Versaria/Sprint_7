package ru.praktikum.clients;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

/**
 * Базовый класс для REST-клиентов
 */
public class BaseClient {
    protected static final String BASE_URL = "http://qa-scooter.praktikum-services.ru";

    protected RequestSpecification getBaseSpec() {
        return given()
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL);
    }
}