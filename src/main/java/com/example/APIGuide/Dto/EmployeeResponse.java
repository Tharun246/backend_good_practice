package com.example.APIGuide.Dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponse
{
    private Long id;

    private String name;

    private String email;

    private String department;

    private Double salary;
}
