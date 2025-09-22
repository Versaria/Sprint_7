package ru.praktikum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.clients.CourierClient;
import ru.praktikum.clients.OrderClient;
import ru.praktikum.models.Courier;
import ru.praktikum.models.CourierCredentials;
import ru.praktikum.models.Order;
import ru.praktikum.utils.DataGenerator;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;

/**
 * Содержит тесты принятия заказов курьером
 */

/**
 * Тесты для ручки "Принять заказ" (PUT /api/v1/orders/accept/:id)
 * Проверяемые сценарии:
 * 1. Успешное принятие заказа курьером
 * 2. Ошибка при отсутствии ID курьера
 * 3. Ошибка для несуществующего заказа
 */
public class OrderAcceptTest {
    private CourierClient courierClient;
    private OrderClient orderClient;
    private int courierId;
    private int track;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        orderClient = new OrderClient();

        Courier courier = DataGenerator.generateRandomCourier();
        courierClient.create(courier);
        Response loginResponse = courierClient.login(
                new CourierCredentials(courier.getLogin(), courier.getPassword()));
        courierId = loginResponse.path("id");

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
        orderResponse.then().statusCode(SC_CREATED); // Проверяем успешное создание
        track = orderResponse.path("track");
    }

    @After
    public void tearDown() {
        if (track != 0) {
            orderClient.cancelOrder(track);
        }
        if (courierId != 0) {
            courierClient.delete(courierId);
        }
    }

    @Test
    @DisplayName("Успешное принятие заказа")
    @Description("Проверка принятия заказа курьером")
    public void testAcceptOrderSuccess() {
        Response orderResponse = orderClient.getOrderByTrack(track);
        orderResponse.then().statusCode(SC_OK); // Проверяем успешное получение заказа
        int orderId = orderResponse.path("order.id");

        Response response = orderClient.acceptOrder(orderId, courierId);
        response.then()
                .statusCode(SC_OK)
                .body("ok", is(true));
    }

    @Test
    @DisplayName("Принятие заказа без ID курьера")
    @Description("Проверка принятия заказа без указания ID курьера")
    public void testAcceptOrderWithoutCourierId() {
        Response orderResponse = orderClient.getOrderByTrack(track);
        orderResponse.then().statusCode(SC_OK); // Проверяем успешное получение заказа
        int orderId = orderResponse.path("order.id");

        Response response = orderClient.acceptOrder(orderId, 0);
        response.then().statusCode(SC_NOT_FOUND);
    }

    @Test
    @DisplayName("Принятие несуществующего заказа")
    @Description("Проверка принятия несуществующего заказа")
    public void testAcceptNonExistentOrder() {
        Response response = orderClient.acceptOrder(999999, courierId);
        response.then()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Заказа с таким id не существует"));
    }
}