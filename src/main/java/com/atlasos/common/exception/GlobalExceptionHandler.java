package com.atlasos.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.Instant;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateResourceException.class)
public ApiError handleDuplicateResourceException(
        DuplicateResourceException exception
) {

    return new ApiError(
            HttpStatus.CONFLICT.value(),
            exception.getMessage(),
            Instant.now()
    );
}

    @ExceptionHandler(MethodArgumentNotValidException.class)
public ApiError handleValidationException(
        MethodArgumentNotValidException exception
) {

    String message = exception.getBindingResult()
            .getFieldErrors()
            .stream()
            .findFirst()
            .map(error -> error.getDefaultMessage())
            .orElse("Invalid request");

    return new ApiError(
            HttpStatus.BAD_REQUEST.value(),
            message,
            Instant.now()
    );
}

    @ExceptionHandler(Exception.class)
    public ApiError handleGenericException(Exception exception) {

        return new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An unexpected error occurred",
                Instant.now()
        );
    }
}



