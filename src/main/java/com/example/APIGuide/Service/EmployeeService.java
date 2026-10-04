package com.example.APIGuide.Service;

import com.example.APIGuide.Dto.EmployeeRequest;
import com.example.APIGuide.Dto.EmployeeResponse;
import com.example.APIGuide.Dto.PageResponse;
import com.example.APIGuide.Entity.Employee;
import com.example.APIGuide.Exception.DuplicateEmployeeException;
import com.example.APIGuide.Exception.EmployeeNotFoundException;
import com.example.APIGuide.Helper.ApiResponse;
import com.example.APIGuide.Repository.EmployeeRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class EmployeeService implements EmployeeServiceImpl
{
    private final EmployeeRepo employeeRepo;

    public EmployeeService(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    @Override
    @Transactional
    public ApiResponse<EmployeeResponse> createEmployee(EmployeeRequest request) {

        if(employeeRepo.existsByEmail(request.getEmail())){
            throw new DuplicateEmployeeException("employee already exists with given email");
        }

        Employee toBeSaved = Employee.builder()
                .name(request.getName())
                .email(request.getEmail())
                .department(request.getDepartment())
                .salary(request.getSalary())
                .build();

        Employee saved = employeeRepo.save(toBeSaved);


        EmployeeResponse resp = EmployeeResponse.builder()
                .name(saved.getName())
                .id(saved.getId())
                .email(saved.getEmail())
                .department(saved.getDepartment())
                .salary(saved.getSalary())
                .build();

        return ApiResponse.success(
                "Employee created successfully",
                resp
        );
    }

    @Override
    public ApiResponse<EmployeeResponse> getEmployee(Long id) {
        Employee checkEmployee = employeeRepo
                .findById(id)
                .orElseThrow(
                        ()->new EmployeeNotFoundException(
                                "Employee does not exist with given id "+id)
                );

            EmployeeResponse resp = EmployeeResponse.builder()
                    .name(checkEmployee.getName())
                    .id(checkEmployee.getId())
                    .email(checkEmployee.getEmail())
                    .department(checkEmployee.getDepartment())
                    .salary(checkEmployee.getSalary())
                    .build();

        return ApiResponse.success(
                "Employee fetched successfully",
                resp);
    }

    @Override
    public ApiResponse<PageResponse<EmployeeResponse>> getAllEmployees(Pageable pageable,String department) {

        Page<Employee> employees;
        if(department !=null && !department.isBlank()){
            employees= employeeRepo
                    .findByDepartment(pageable,department);
        }
        else {
            employees=employeeRepo.findAll(pageable);
        }
        Page<EmployeeResponse> responsePage = employees.map(this::toEmployeeResponse);

        PageResponse<EmployeeResponse> response = PageResponse.<EmployeeResponse>builder()
                .content(responsePage.getContent())
                .page(responsePage.getNumber())
                .size(responsePage.getSize())
                .totalElements(responsePage.getTotalElements())
                .totalPages(responsePage.getTotalPages())
                .first(responsePage.isFirst())
                .last(responsePage.isLast())
                .build();

        return ApiResponse.success(
                "fetched",
                response
        );
    }


    @Override
    @Transactional
    public ApiResponse<EmployeeResponse> updateEmployee(Long id, EmployeeRequest request) {

        if(employeeRepo.duplicateCheck(request.getEmail(),id).isPresent()){
            throw new DuplicateEmployeeException("The email is already registered");
        }
        Employee employee = employeeRepo.findById(id).orElseThrow(()->
            new EmployeeNotFoundException("employee not found")
        );

        if(false){
            throw new Error("for testing");
        }

        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setDepartment(request.getDepartment());
        employee.setSalary(request.getSalary());

        return ApiResponse.success(
                "employee info updated",
                toEmployeeResponse(employee));
    }

    @Override
    public ApiResponse<String> deleteEmployee(Long id) {
        Optional<Employee> checkEmployee = employeeRepo.findById(id);

        if(checkEmployee.isEmpty()){
            return ApiResponse.error(
                    "employee does not exist with given id",
                    null
            );
        }
        employeeRepo.deleteById(id);
        return ApiResponse.success(
                "Employee deleted",
                checkEmployee.get().getName()
        );
    }

    public EmployeeResponse toEmployeeResponse(Employee request){

        return EmployeeResponse.builder()
                .id(request.getId())
                .name(request.getName())
                .email(request.getEmail())
                .department(request.getDepartment())
                .salary(request.getSalary())
                .build();
    }

    public String getEmail(Long id){
        return employeeRepo.getEmail(id).orElse("");
    }
}