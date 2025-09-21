package ru.praktikum.constants;

/**
 * Класс для хранения констант с путями API
 * ИСПРАВЛЕНИЕ:
 * Вынесены все пути API в отдельный класс
 */
public class Endpoints {
    public static final String COURIER = "/api/v1/courier";
    public static final String COURIER_LOGIN = "/api/v1/courier/login";
    public static final String COURIER_ID = "/api/v1/courier/";
    public static final String ORDERS = "/api/v1/orders";
    public static final String ACCEPT_ORDER = "/api/v1/orders/accept/";
    public static final String TRACK_ORDER = "/api/v1/orders/track";
}