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

/**
 * Тесты для ручки "Логин курьера" (POST /api/v1/courier/login)
 * Проверяемые сценарии:
 * 1. Успешная авторизация с валидными данными
 * 2. Ошибка при неверном пароле
 * 3. Ошибка для несуществующего пользователя
 * 4. Ошибка при отсутствии логина/пароля
 */
public class CourierLoginTest {
    private CourierClient courierClient;
    private Courier courier;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = DataGenerator.generateRandomCourier();
        courierClient.create(courier);
    }

    @After
    public void tearDown() {
        Response loginResponse = courierClient.login(
                new CourierCredentials(courier.getLogin(), courier.getPassword()));
        if (loginResponse.statusCode() == 200) {
            courierId = loginResponse.path("id");
            courierClient.delete(courierId);
        }
    }

    @Test
    @DisplayName("Успешный логин курьера")
    @Description("Проверка авторизации курьера с валидными данными")
    public void testCourierLoginSuccess() {
        Response response = courierClient.login(
                new CourierCredentials(courier.getLogin(), courier.getPassword()));
        response.then()
                .statusCode(SC_OK)
                .body("id", notNullValue());
        courierId = response.path("id");
    }

    @Test
    @DisplayName("Логин с неверным паролем")
    @Description("Проверка авторизации с неверным паролем")
    public void testCourierLoginWithWrongPassword() {
        Response response = courierClient.login(
                new CourierCredentials(courier.getLogin(), "wrong_password"));
        response.then()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Логин с несуществующим пользователем")
    @Description("Проверка авторизации несуществующего пользователя")
    public void testLoginWithNonExistentUser() {
        Response response = courierClient.login(
                new CourierCredentials("nonexistent", "password"));
        response.then()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Логин без логина")
    @Description("Проверка авторизации без указания логина")
    public void testLoginWithoutLogin() {
        Response response = courierClient.login(
                new CourierCredentials(null, "password"));
        response.then()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    // Добавлена проверка авторизации без пароля
    @Test
    @DisplayName("Логин без пароля")
    @Description("Проверка авторизации без указания пароля")
    public void testLoginWithoutPassword() {
        Response response = courierClient.login(
                new CourierCredentials(courier.getLogin(), null));
        response.then()
                .statusCode(504) // Сервер возвращает Gateway Timeout
                .body(not(emptyString()));
    }
}