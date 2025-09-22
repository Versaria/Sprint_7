package ru.praktikum.models;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Модели данных курьера с использованием Lombok
 */
@Data
@AllArgsConstructor
public class Courier {
    private String login;
    private String password;
    private String firstName;
}