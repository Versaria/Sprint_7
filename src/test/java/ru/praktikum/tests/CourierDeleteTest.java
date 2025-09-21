package ru.praktikum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.clients.CourierClient;
import ru.praktikum.models.Courier;
import ru.praktikum.models.CourierCredentials;
import ru.praktikum.utils.DataGenerator;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;

public class CourierDeleteTest {
    private CourierClient courierClient;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        Courier courier = DataGenerator.generateRandomCourier();
        courierClient.create(courier);

        Response loginResponse = courierClient.login(
                new CourierCredentials(courier.getLogin(), courier.getPassword()));
        courierId = loginResponse.path("id");
    }

    @After
    public void tearDown() {
        if (courierId != 0) {
            courierClient.delete(courierId);
        }
    }

    @Test
    @DisplayName("Успешное удаление курьера")
    @Description("Проверка корректного удаления курьера")
    public void testDeleteCourierSuccess() {
        Response response = courierClient.delete(courierId);
        response.then()
                .statusCode(SC_OK)
                .body("ok", is(true));
        courierId = 0;
    }

    @Test
    @DisplayName("Удаление несуществующего курьера")
    @Description("Проверка удаления курьера с несуществующим ID")
    public void testDeleteNonExistentCourier() {
        Response response = courierClient.delete(999999);
        response.then()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Курьера с таким id нет."));
    }

    @Test
    @DisplayName("Удаление курьера с некорректным ID")
    @Description("Проверка удаления курьера с ID = 0")
    public void testDeleteCourierWithInvalidId() {
        Response response = courierClient.delete(0);
        response.then().statusCode(SC_NOT_FOUND);
    }
}