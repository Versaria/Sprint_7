package ru.praktikum.clients;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.praktikum.constants.Endpoints;
import ru.praktikum.models.Courier;
import ru.praktikum.models.CourierCredentials;

/**
 * Клиент для работы с API курьеров
 */
public class CourierClient extends BaseClient {
    @Step("Создание курьера")
    public Response create(Courier courier) {
        return getBaseSpec()
                .body(courier)
                .when()
                .post(Endpoints.COURIER);
    }

    @Step("Логин курьера")
    public Response login(CourierCredentials credentials) {
        return getBaseSpec()
                .body(credentials)
                .when()
                .post(Endpoints.COURIER_LOGIN);
    }

    @Step("Удаление курьера")
    public Response delete(int id) {
        return getBaseSpec()
                .when()
                .delete(Endpoints.COURIER_ID + id);
    }
}