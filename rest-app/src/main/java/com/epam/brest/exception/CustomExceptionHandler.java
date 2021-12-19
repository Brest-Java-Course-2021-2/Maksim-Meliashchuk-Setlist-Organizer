package com.epam.brest.exception;

import com.epam.brest.dao.exception.NotUniqueException;
import com.epam.brest.service.exception.BandNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String BAND_NOT_FOUND = "band.not_found";
    public static final String VALIDATION_ERROR = "validation_error";
    public static final String NOT_UNIQUE_ERROR = "not_unique_error";

    @ExceptionHandler(BandNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleUserNotFoundException (BandNotFoundException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse(BAND_NOT_FOUND, details);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(Exception ex, WebRequest request) {

        return new ResponseEntity<>(
                new ErrorResponse(VALIDATION_ERROR, ex),
                HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = {NotUniqueException.class})
    public ResponseEntity<ErrorResponse> handleNotUniqueException(Exception ex, WebRequest request) {

        return new ResponseEntity<>(
                new ErrorResponse(NOT_UNIQUE_ERROR, ex),
                HttpStatus.UNPROCESSABLE_ENTITY);
    }
}