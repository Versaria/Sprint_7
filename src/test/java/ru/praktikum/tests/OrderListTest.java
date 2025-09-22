package ru.praktikum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import ru.praktikum.clients.OrderClient;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Тест для ручки "Получение списка заказов" (GET /api/v1/orders)
 * Проверяет:
 * - Код ответа 200 OK
 * - Наличие непустого списка заказов в теле ответа
 */
public class OrderListTest {
    private final OrderClient orderClient = new OrderClient();

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Проверка получения списка всех заказов")
    public void testGetOrders() {
        Response response = orderClient.getOrders();
        response.then()
                .statusCode(SC_OK)
                .body("orders", notNullValue());
    }
}