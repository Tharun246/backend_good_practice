package com.example.APIGuide.Helper;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class ApiError
{
    private String field;
    private String message;
}
