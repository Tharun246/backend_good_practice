package com.example.APIGuide.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequest
{
    @NotBlank(message = "provide a valid value name ")
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank(message = "provide a valid for department")
    private String department;

    @Min(0)
    private Double salary;
}
