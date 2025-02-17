package com.marouanedbibih.hotel_management.utils;

public class BasicException extends Exception {
    private BasicResponse response;

    public BasicException(String message) {
        super(message);
    }

    public BasicException(BasicResponse response) {
        this.response = response;
    }

    public BasicResponse getResponse() {
        return response;
    }
}
