package ru.praktikum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.praktikum.clients.OrderClient;
import ru.praktikum.models.Order;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Содержит параметризованные тесты создания заказов
 */

/**
 * Параметризованные тесты для ручки "Создание заказа" (POST /api/v1/orders)
 * Проверяемые сценарии с разными цветами самоката:
 * 1. Черный (BLACK)
 * 2. Серый (GREY)
 * 3. Оба цвета
 * 4. Без указания цвета
 */
@RunWith(Parameterized.class)
public class OrderCreationTest {
    private final OrderClient orderClient = new OrderClient();
    private final String[] color;
    private int track;

    public OrderCreationTest(String[] color, @SuppressWarnings("unused") String description) {
        this.color = color;
    }

    @Parameterized.Parameters(name = "Цвет самоката: {1}")
    public static Object[][] getColorData() {
        return new Object[][]{
                {new String[]{"BLACK"}, "Черный"},
                {new String[]{"GREY"}, "Серый"},
                {new String[]{"BLACK", "GREY"}, "Оба цвета"},
                {new String[]{}, "Без цвета"}
        };
    }

    @After
    public void tearDown() {
        if (track != 0) {
            orderClient.cancelOrder(track);
        }
    }

    @Test
    @DisplayName("Создание заказа с разными цветами")
    @Description("Параметризованный тест создания заказа с разными вариантами цвета")
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
        response.then()
                .statusCode(SC_CREATED)
                .body("track", notNullValue());

        track = response.path("track");
    }
}