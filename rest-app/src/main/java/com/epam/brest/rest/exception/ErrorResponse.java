package com.epam.brest.rest.exception;

import java.util.Arrays;
import java.util.List;

public class ErrorResponse
{
    public ErrorResponse() {
        super();
    }

    public ErrorResponse(String message, List<String> details) {
        super();
        this.message = message;
        this.details = details;
    }

    private String message;
    private List<String> details;

    public ErrorResponse(String message, Exception ex) {
        super();
        this.message = message;
        if (ex != null) {
            this.details = Arrays.asList(ex.getMessage());
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }

}