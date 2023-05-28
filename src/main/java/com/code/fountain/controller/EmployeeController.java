package com.code.fountain.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

import com.code.fountain.services.EmployeeService;
import com.code.fountain.dto.EmployeeDTO;
import com.code.fountain.entities.Employee;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/create")
    public ResponseEntity<Employee> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee createdEmployee = employeeService.createEmployee(employeeDTO);
        LOGGER.info("Employee created");
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO employeeDTO) throws IOException {
        Employee updatedEmployee = employeeService.updateEmployee(id, employeeDTO);
        LOGGER.info("Employee updated");
        return ResponseEntity.ok(updatedEmployee);
    }

    @GetMapping("/getAllEmployees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        LOGGER.info("getAll Employees");
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/getEmployee/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) throws IOException {
        Employee employee = employeeService.getEmployeeById(id);
        LOGGER.info("getEmployeeId:" + id);
        return ResponseEntity.ok(employee);
    }

    @GetMapping("/getAllEmployees/pageable")
    public ResponseEntity<Page<Employee>> getAllEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Employee> employees = employeeService.getAllEmployees(pageable);
        LOGGER.info("getAll Pageable Employees");
        return ResponseEntity.ok(employees);
    }

    @DeleteMapping("/deleteEmployee/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) throws IOException {
        employeeService.deleteEmployee(id);
        LOGGER.info("delete Employee Id" + id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deleteAllEmployees")
    public ResponseEntity<Void> deleteAllEmployees() {
        employeeService.deleteAllEmployees();
        LOGGER.info("delete Employees");
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/increment/{id}")
    public ResponseEntity<String> increaseSalary(@PathVariable Long id) {
         String msg = employeeService.incrementSalary(id);
        LOGGER.info("Employee salary  increased");
        return ResponseEntity.status(HttpStatus.CREATED).body(msg);
    }
}