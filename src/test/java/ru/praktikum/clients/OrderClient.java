package ru.praktikum.clients;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.praktikum.constants.Endpoints;
import ru.praktikum.models.Order;

/**
 * Клиент для работы с API заказов
 * ИСПРАВЛЕНИЕ:
 * Наследуется от BaseClient для использования общих настроек
 * Использует константы из Endpoints вместо хардкода путей
 */
public class OrderClient extends BaseClient {
    @Step("Создание заказа")
    public Response create(Order order) {
        return getBaseSpec()
                .body(order)
                .when()
                .post(Endpoints.ORDERS);
    }

    @Step("Получение списка заказов")
    public Response getOrders() {
        return getBaseSpec()
                .when()
                .get(Endpoints.ORDERS);
    }

    @Step("Принятие заказа")
    public Response acceptOrder(int orderId, int courierId) {
        return getBaseSpec()
                .queryParam("courierId", courierId)
                .when()
                .put(Endpoints.ACCEPT_ORDER + orderId);
    }

    @Step("Получение заказа по номеру")
    public Response getOrderByTrack(int track) {
        return getBaseSpec()
                .queryParam("t", track)
                .when()
                .get(Endpoints.TRACK_ORDER);
    }

    @Step("Отмена заказа")
    public void cancelOrder(int track) {
        getBaseSpec()
                .body("{\"track\": " + track + "}")
                .when()
                .put("/api/v1/orders/cancel");
    }
}