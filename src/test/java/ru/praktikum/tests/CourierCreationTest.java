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
 * Переименовано из CourierTest.java -> CourierCreationTest.java
 * Содержит тесты создания курьеров
 * На каждую ручку создан отдельный тестовый класс
 * Добавлены аннотации @Description для всех тестов
 * Используются константы HTTP-статусов вместо числовых значений
 * Логин выполняется в tearDown() даже если тест упал
 */
public class CourierCreationTest {
    private CourierClient courierClient;
    private Courier courier;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = DataGenerator.generateRandomCourier();
    }

    @After
    public void tearDown() {
        if (courierId != 0) {
            Response loginResponse = courierClient.login(
                    new CourierCredentials(courier.getLogin(), courier.getPassword()));
            courierId = loginResponse.path("id");
            courierClient.delete(courierId);
        }
    }

    @Test
    @DisplayName("Успешное создание курьера")
    @Description("Проверка создания курьера с валидными данными")
    public void testCourierCreationSuccess() {
        Response response = courierClient.create(courier);
        response.then()
                .statusCode(SC_CREATED)
                .body("ok", is(true));

        Response loginResponse = courierClient.login(
                new CourierCredentials(courier.getLogin(), courier.getPassword()));
        courierId = loginResponse.path("id");
    }

    @Test
    @DisplayName("Создание дубликата курьера")
    @Description("Проверка невозможности создания двух одинаковых курьеров")
    public void testDuplicateCourierCreation() {
        courierClient.create(courier);

        Response response = courierClient.create(courier);
        response.then()
                .statusCode(SC_CONFLICT)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));

        Response loginResponse = courierClient.login(
                new CourierCredentials(courier.getLogin(), courier.getPassword()));
        courierId = loginResponse.path("id");
    }

    @Test
    @DisplayName("Создание курьера без логина")
    @Description("Проверка создания курьера без обязательного поля login")
    public void testCreateCourierWithoutLogin() {
        Courier courierWithoutLogin = new Courier(null, "password", "name");
        Response response = courierClient.create(courierWithoutLogin);
        response.then()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    @Description("Проверка создания курьера без обязательного поля password")
    public void testCreateCourierWithoutPassword() {
        Courier courierWithoutPassword = new Courier("login", null, "name");
        Response response = courierClient.create(courierWithoutPassword);
        response.then()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
}