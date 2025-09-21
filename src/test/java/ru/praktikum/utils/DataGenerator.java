package ru.praktikum.utils;

import com.github.javafaker.Faker;
import ru.praktikum.models.Courier;

import java.util.Locale;

/**
 * Генератор тестовых данных
 * ИСПРАВЛЕНИЕ:
 * Заменена ручная генерация данных на использование JavaFaker
 */
public class DataGenerator {
    private static final Faker faker = new Faker(new Locale("ru"));

    public static Courier generateRandomCourier() {
        return new Courier(
                faker.name().username(),
                faker.internet().password(),
                faker.name().firstName()
        );
    }

    public static String[] getRandomColor() {
        String[] colors = {"BLACK", "GREY"};
        int randomIndex = faker.random().nextInt(colors.length);
        return new String[]{colors[randomIndex]};
    }
}