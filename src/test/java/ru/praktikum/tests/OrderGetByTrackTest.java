package ru.praktikum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.clients.OrderClient;
import ru.praktikum.models.Order;
import ru.praktikum.utils.DataGenerator;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;

public class OrderGetByTrackTest {
    private OrderClient orderClient;
    private int track;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
        Order order = new Order(
                "Иван",
                "Иванов",
                "Москва, ул. Ленина, 1",
                "4",
                "+79999999999",
                5,
                "2024-01-01",
                "Комментарий",
                DataGenerator.getRandomColor()
        );
        Response orderResponse = orderClient.create(order);
        track = orderResponse.path("track");
    }

    @After
    public void tearDown() {
        if (track != 0) {
            orderClient.cancelOrder(track);
        }
    }

    @Test
    @DisplayName("Получение заказа по номеру")
    @Description("Проверка получения заказа по его трек-номеру")
    public void testGetOrderByTrack() {
        Response response = orderClient.getOrderByTrack(track);
        response.then()
                .statusCode(SC_OK)
                .body("order", notNullValue());
    }

    @Test
    @DisplayName("Получение заказа без номера")
    @Description("Проверка получения заказа без указания трек-номера")
    public void testGetOrderWithoutTrack() {
        Response response = orderClient.getOrderByTrack(0);
        response.then().statusCode(SC_NOT_FOUND);
    }

    @Test
    @DisplayName("Получение несуществующего заказа")
    @Description("Проверка получения несуществующего заказа")
    public void testGetNonExistentOrder() {
        Response response = orderClient.getOrderByTrack(999999);
        response.then()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Заказ не найден"));
    }
}