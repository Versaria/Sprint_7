package ru.praktikum.utils;

import ru.praktikum.models.Courier;

public class DataGenerator {
    public static Courier generateRandomCourier() {
        String timestamp = String.valueOf(System.currentTimeMillis());
        return new Courier(
                "courier_" + timestamp,
                "password_" + timestamp,
                "name_" + timestamp
        );
    }

    public static String[] getRandomColor() {
        String[] colors = {"BLACK", "GREY"};
        int randomIndex = (int) (Math.random() * colors.length);
        return new String[]{colors[randomIndex]};
    }
}