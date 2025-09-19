package ru.praktikum.clients;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import ru.praktikum.models.Order;

import static io.restassured.RestAssured.given;

public class OrderClient {
    private static final String BASE_URL = "http://qa-scooter.praktikum-services.ru";

    @Step("Создание заказа")
    public Response create(Order order) {
        return given()
                .filter(new AllureRestAssured())
                .header("Content-type", "application/json")
                .baseUri(BASE_URL)
                .body(order)
                .when()
                .post("/api/v1/orders");

    }

    @Step("Получение списка заказов")
    public Response getOrders() {
        return given()
                .filter(new AllureRestAssured())
                .header("Content-type", "application/json")
                .baseUri(BASE_URL)
                .when()
                .get("/api/v1/orders");
    }

    @Step("Принятие заказа")
    public Response acceptOrder(int orderId, int courierId) {
        return given()
                .filter(new AllureRestAssured())
                .header("Content-type", "application/json")
                .baseUri(BASE_URL)
                .queryParam("courierId", courierId)
                .when()
                .put("/api/v1/orders/accept/" + orderId);
    }

    @Step("Получение заказа по номеру")
    public Response getOrderByTrack(int track) {
        return given()
                .filter(new AllureRestAssured())
                .header("Content-type", "application/json")
                .baseUri(BASE_URL)
                .queryParam("t", track)
                .when()
                .get("/api/v1/orders/track");
    }
}