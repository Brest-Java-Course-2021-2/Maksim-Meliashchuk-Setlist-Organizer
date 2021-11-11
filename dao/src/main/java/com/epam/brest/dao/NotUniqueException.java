package com.epam.brest.dao;

public class NotUniqueException extends IllegalArgumentException {

    public NotUniqueException(String message) {
        super(message);
    }
}