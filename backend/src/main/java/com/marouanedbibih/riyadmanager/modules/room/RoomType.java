package com.marouanedbibih.riyadmanager.modules.room;

public enum RoomType {
    SINGLE(100.0),
    DOUBLE(150.0),
    SUITE(250.0),
    TWIN(120.0),
    DELUXE(200.0),
    FAMILY(180.0),
    PRESIDENTIAL(500.0);

    private final double price;

    RoomType(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
}