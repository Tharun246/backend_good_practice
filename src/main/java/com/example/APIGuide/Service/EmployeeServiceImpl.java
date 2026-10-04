package com.example.APIGuide.Service;

import com.example.APIGuide.Dto.EmployeeRequest;
import com.example.APIGuide.Dto.EmployeeResponse;
import com.example.APIGuide.Dto.PageResponse;
import com.example.APIGuide.Helper.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeServiceImpl
{
    ApiResponse<EmployeeResponse> createEmployee(EmployeeRequest request);

    ApiResponse<EmployeeResponse> getEmployee(Long id);

    ApiResponse<PageResponse<EmployeeResponse>> getAllEmployees(Pageable pageable, String department);

    ApiResponse<EmployeeResponse> updateEmployee(
            Long id,
            EmployeeRequest request
    );

    ApiResponse<String> deleteEmployee(Long id);
}
