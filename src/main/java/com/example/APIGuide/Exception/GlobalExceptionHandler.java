package com.example.APIGuide.Exception;

import com.example.APIGuide.Helper.ApiError;
import com.example.APIGuide.Helper.ApiResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> methodArguments(MethodArgumentNotValidException exception) {

        List<ApiError> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(
                        err ->
                            ApiError.builder()
                                    .field(err.getField())
                                    .message(err.getDefaultMessage())
                                    .build()

                ).toList();

        ApiResponse<Void> response = ApiResponse.error(
                "Validation failed",
                errors
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> genericHandler(Exception ex){


        ApiResponse<Void> response = ApiResponse.error(
                ex.getMessage(),
                null
        );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleEmployeeNotFound(EmployeeNotFoundException exception){

        ApiResponse<Void> error = ApiResponse.error(
                exception.getMessage(),
                null
        );
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    @ExceptionHandler(DuplicateEmployeeException.class)
    public ResponseEntity<ApiResponse<Void>> handleDuplicate(DuplicateEmployeeException exception){

        ApiResponse<Void> response = ApiResponse.error(
                exception.getMessage(),
                null
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);

    }
}