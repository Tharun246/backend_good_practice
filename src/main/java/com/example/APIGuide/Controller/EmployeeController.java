package com.example.APIGuide.Controller;

import com.example.APIGuide.Dto.EmployeeRequest;
import com.example.APIGuide.Dto.EmployeeResponse;
import com.example.APIGuide.Dto.PageResponse;
import com.example.APIGuide.Helper.ApiResponse;
import com.example.APIGuide.Service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping(path = "/api/v1/employees")
public class EmployeeController
{
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService service) {
        this.employeeService = service;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<EmployeeResponse>> createEmployee(
            @Valid @RequestBody EmployeeRequest employee) {


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(employeeService.createEmployee(employee));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeResponse>> getEmployee(
            @PathVariable Long id) {


        return ResponseEntity.
                status(HttpStatus.OK)
                .body(employeeService.getEmployee(id));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<EmployeeResponse>>> getAllEmployees(
            Pageable pageable,@RequestParam(required = false) String department)
    {

        return ResponseEntity.ok(
                employeeService.getAllEmployees(pageable,department)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeResponse>> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeRequest employee) {

        return ResponseEntity.
                ok(employeeService.updateEmployee(id,employee));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteEmployee(
            @PathVariable Long id) throws Exception {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body( employeeService.deleteEmployee(id));
    }

    @GetMapping("/test")
    public String test(@RequestParam("id") Long id)
    {
        return employeeService.getEmail(id);
    }
}
