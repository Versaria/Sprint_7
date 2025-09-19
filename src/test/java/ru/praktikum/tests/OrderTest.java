package ru.praktikum.tests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.praktikum.clients.OrderClient;
import ru.praktikum.models.Order;

import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderTest {
    private final OrderClient orderClient = new OrderClient();
    private final String[] color;

    public OrderTest(String[] color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getColorData() {
        return new Object[][]{
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{"BLACK", "GREY"}},
                {new String[]{}}
        };
    }

    @Test
    @DisplayName("Создание заказа с разными цветами")
    public void testOrderCreationWithColors() {
        Order order = new Order(
                "Иван",
                "Иванов",
                "Москва, ул. Ленина, 1",
                "4",
                "+79999999999",
                5,
                "2024-01-01",
                "Комментарий",
                color
        );

        Response response = orderClient.create(order);
        response.then().statusCode(201).body("track", notNullValue());
    }

    @Test
    @DisplayName("Получение списка заказов")
    public void testGetOrders() {
        Response response = orderClient.getOrders();
        response.then().statusCode(200).body("orders", notNullValue());
    }
}