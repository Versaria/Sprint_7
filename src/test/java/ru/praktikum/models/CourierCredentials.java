package ru.praktikum.models;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Модели учетных данных курьера с использованием Lombok
 */
@Data
@AllArgsConstructor
public class CourierCredentials {
    private String login;
    private String password;
}