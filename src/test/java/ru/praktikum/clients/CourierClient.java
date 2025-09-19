package ru.praktikum.clients;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import ru.praktikum.models.Courier;
import ru.praktikum.models.CourierCredentials;

import static io.restassured.RestAssured.given;

public class CourierClient {
    private static final String BASE_URL = "http://qa-scooter.praktikum-services.ru";

    @Step("Создание курьера")
    public Response create(Courier courier) {
        return given()
                .filter(new AllureRestAssured())
                .header("Content-type", "application/json")
                .baseUri(BASE_URL)
                .body(courier)
                .when()
                .post("/api/v1/courier");
    }

    @Step("Логин курьера")
    public Response login(CourierCredentials credentials) {
        return given()
                .filter(new AllureRestAssured())
                .header("Content-type", "application/json")
                .baseUri(BASE_URL)
                .body(credentials)
                .when()
                .post("/api/v1/courier/login");
    }

    @Step("Удаление курьера")
    public Response delete(int id) {
        return given()
                .filter(new AllureRestAssured())
                .header("Content-type", "application/json")
                .baseUri(BASE_URL)
                .when()
                .delete("/api/v1/courier/" + id);
    }
}