package ru.praktikum.models;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Модель курьера
 * ИСПРАВЛЕНИЕ:
 * Использованы аннотации Lombok @Data и @AllArgsConstructor
 * Автоматически генерирует геттеры, сеттеры, equals, hashCode и конструктор со всеми полями
 */
@Data
@AllArgsConstructor
public class Courier {
    private String login;
    private String password;
    private String firstName;
}