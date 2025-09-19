package ru.praktikum.models;

@SuppressWarnings("unused")
public class Order {
    @SuppressWarnings("unused")
    private final String firstName;
    @SuppressWarnings("unused")
    private final String lastName;
    @SuppressWarnings("unused")
    private final String address;
    @SuppressWarnings("unused")
    private final String metroStation;
    @SuppressWarnings("unused")
    private final String phone;
    @SuppressWarnings("unused")
    private final int rentTime;
    @SuppressWarnings("unused")
    private final String deliveryDate;
    @SuppressWarnings("unused")
    private final String comment;
    @SuppressWarnings("unused")
    private final String[] color;

    public Order(String firstName, String lastName, String address, String metroStation,
                 String phone, int rentTime, String deliveryDate, String comment, String[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    public String[] getColor() {
        return color;
    }
}