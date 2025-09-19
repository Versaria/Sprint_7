package ru.praktikum.tests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.clients.CourierClient;
import ru.praktikum.models.Courier;
import ru.praktikum.models.CourierCredentials;
import ru.praktikum.utils.DataGenerator;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;

public class CourierTest {
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
            courierClient.delete(courierId);
        }
    }

    @Test
    @DisplayName("Успешное создание курьера")
    public void testCourierCreationSuccess() {
        Response response = courierClient.create(courier);
        response.then().statusCode(201).body("ok", is(true));

        // Логин для получения ID
        Response loginResponse = courierClient.login(
                new CourierCredentials(courier.getLogin(), courier.getPassword()));
        courierId = loginResponse.path("id");
        assertTrue("ID курьера должен быть больше 0", courierId > 0);
    }

    @Test
    @DisplayName("Создание дубликата курьера")
    public void testDuplicateCourierCreation() {
        // Создаем первого курьера
        courierClient.create(courier);

        // Пытаемся создать такого же курьера
        Response response = courierClient.create(courier);
        response.then().statusCode(409).body("message", equalTo("Этот логин уже используется. Попробуйте другой."));

        // Очистка
        Response loginResponse = courierClient.login(
                new CourierCredentials(courier.getLogin(), courier.getPassword()));
        courierId = loginResponse.path("id");
    }

    @Test
    @DisplayName("Создание курьера без логина")
    public void testCreateCourierWithoutLogin() {
        Courier courierWithoutLogin = new Courier(null, "password", "name");
        Response response = courierClient.create(courierWithoutLogin);
        response.then().statusCode(400).body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    public void testCreateCourierWithoutPassword() {
        Courier courierWithoutPassword = new Courier("login", null, "name");
        Response response = courierClient.create(courierWithoutPassword);
        response.then().statusCode(400).body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Успешный логин курьера")
    public void testCourierLoginSuccess() {
        // Создаем курьера
        courierClient.create(courier);

        // Логинимся
        Response response = courierClient.login(
                new CourierCredentials(courier.getLogin(), courier.getPassword()));
        response.then().statusCode(200).body("id", notNullValue());
        courierId = response.path("id");
    }

    @Test
    @DisplayName("Логин с неверным паролем")
    public void testCourierLoginWithWrongPassword() {
        // Создаем курьера
        courierClient.create(courier);

        // Пытаемся логиниться с неверным паролем
        Response response = courierClient.login(
                new CourierCredentials(courier.getLogin(), "wrong_password"));
        response.then().statusCode(404).body("message", equalTo("Учетная запись не найдена"));

        // Очистка
        Response loginResponse = courierClient.login(
                new CourierCredentials(courier.getLogin(), courier.getPassword()));
        courierId = loginResponse.path("id");
    }

    @Test
    @DisplayName("Логин с несуществующим пользователем")
    public void testLoginWithNonExistentUser() {
        Response response = courierClient.login(
                new CourierCredentials("nonexistent", "password"));
        response.then().statusCode(404).body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Логин без пароля")
    public void testLoginWithoutPassword() {
        Response response = courierClient.login(
                new CourierCredentials("login", null));
        response.then().statusCode(504); // Сервер возвращает Gateway Timeout
    }

    @Test
    @DisplayName("Логин без логина")
    public void testLoginWithoutLogin() {
        Response response = courierClient.login(
                new CourierCredentials(null, "password"));
        response.then().statusCode(400).body("message", equalTo("Недостаточно данных для входа"));
    }
}