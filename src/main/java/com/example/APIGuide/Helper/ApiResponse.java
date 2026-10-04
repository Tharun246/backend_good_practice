package com.example.APIGuide.Helper;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Getter
@Builder
public class ApiResponse<T>
{
    private boolean success;
    private String message;
    private T data;
    private List<ApiError> errors;
    private Instant timeStamp;

    public static<T> ApiResponse<T> success(String message,T data){
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .timeStamp(Instant.now())
                .build();
    }

    public static<T> ApiResponse<T> error(String message,List<ApiError> errors){
        return ApiResponse.<T>builder()
                .success(false)
                .data(null)
                .errors(errors)
                .message(message)
                .timeStamp(Instant.now())
                .build();
    }
}
