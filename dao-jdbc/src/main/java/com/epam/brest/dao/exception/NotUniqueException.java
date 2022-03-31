package com.epam.brest.dao.exception;

public class NotUniqueException extends IllegalArgumentException {

    public NotUniqueException(String message) {
        super(message);
    }
}