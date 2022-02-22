package com.epam.brest.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Setter
@Getter
@Schema(name="ErrorResponse", description = "ErrorResponse")
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

}