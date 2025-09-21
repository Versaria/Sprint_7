package ru.praktikum.models;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Модель заказа
 * ИСПРАВЛЕНИЕ:
 * Использованы аннотации Lombok @Data и @AllArgsConstructor
 * Автоматически генерирует геттеры, сеттеры, equals, hashCode и конструктор со всеми полями
 */
@Data
@AllArgsConstructor
public class Order {
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private String[] color;
}