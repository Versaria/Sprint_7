package ru.praktikum.models;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Модель учетных данных курьера
 * ИСПРАВЛЕНИЕ:
 * Использованы аннотации Lombok @Data и @AllArgsConstructor
 * Автоматически генерирует геттеры, сеттеры, equals, hashCode и конструктор со всеми полями
 */
@Data
@AllArgsConstructor
public class CourierCredentials {
    private String login;
    private String password;
}