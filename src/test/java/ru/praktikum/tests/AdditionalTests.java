package ru.praktikum.tests;

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

import static org.hamcrest.Matchers.*;

public class AdditionalTests {
    private CourierClient courierClient;
    private OrderClient orderClient;
    private int courierId;
    private int orderTrack;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        orderClient = new OrderClient();
        Courier courier = DataGenerator.generateRandomCourier();

        // Создаем курьера для тестов
        courierClient.create(courier);
        Response loginResponse = courierClient.login(
                new CourierCredentials(courier.getLogin(), courier.getPassword()));
        courierId = loginResponse.path("id");

        // Создаем заказ для тестов
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
        orderTrack = orderResponse.path("track");
    }

    @After
    public void tearDown() {
        if (courierId != 0) {
            courierClient.delete(courierId);
        }
    }

    @Test
    @DisplayName("Успешное удаление курьера")
    public void testDeleteCourierSuccess() {
        Response response = courierClient.delete(courierId);
        response.then().statusCode(200).body("ok", is(true));
        courierId = 0;
    }

    @Test
    @DisplayName("Удаление несуществующего курьера")
    public void testDeleteNonExistentCourier() {
        Response response = courierClient.delete(999999);
        response.then().statusCode(404).body("message", equalTo("Курьера с таким id нет."));
    }

    @Test
    @DisplayName("Удаление курьера с некорректным ID")
    public void testDeleteCourierWithInvalidId() {
        Response response = courierClient.delete(0);
        response.then().statusCode(404);
    }

    @Test
    @DisplayName("Успешное принятие заказа")
    public void testAcceptOrderSuccess() {
        Response orderResponse = orderClient.getOrderByTrack(orderTrack);
        int orderId = orderResponse.path("order.id");

        Response response = orderClient.acceptOrder(orderId, courierId);
        response.then().statusCode(200).body("ok", is(true));
    }

    @Test
    @DisplayName("Принятие заказа без ID курьера")
    public void testAcceptOrderWithoutCourierId() {
        Response orderResponse = orderClient.getOrderByTrack(orderTrack);
        int orderId = orderResponse.path("order.id");

        Response response = orderClient.acceptOrder(orderId, 0);
        response.then().statusCode(404);
    }

    @Test
    @DisplayName("Принятие несуществующего заказа")
    public void testAcceptNonExistentOrder() {
        Response response = orderClient.acceptOrder(999999, courierId);
        response.then().statusCode(404).body("message", equalTo("Заказа с таким id не существует"));
    }

    @Test
    @DisplayName("Получение заказа по номеру")
    public void testGetOrderByTrack() {
        Response response = orderClient.getOrderByTrack(orderTrack);
        response.then().statusCode(200).body("order", notNullValue());
    }

    @Test
    @DisplayName("Получение заказа без номера")
    public void testGetOrderWithoutTrack() {
        Response response = orderClient.getOrderByTrack(0);
        response.then().statusCode(404);
    }

    @Test
    @DisplayName("Получение несуществующего заказа")
    public void testGetNonExistentOrder() {
        Response response = orderClient.getOrderByTrack(999999);
        response.then().statusCode(404).body("message", equalTo("Заказ не найден"));
    }
}