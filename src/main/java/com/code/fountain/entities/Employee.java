package com.code.fountain.entities;

import com.code.fountain.util.MaskData;
import com.code.fountain.util.DataProtect;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.code.fountain.dto.EmployeeDTO;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "employee")
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "name")
    private String name;
    @Column(name = "phNo")
    @DataProtect
    private Long phNo;
    @DataProtect
    @Column(name = "accountNumber")
    private Long accountNumber;

    @Column(name = "salary")
    private Double salary;

    public Employee dtoToEntity(EmployeeDTO employeeDto) {
        Employee employee = new Employee();
        MaskData p = new MaskData();
        employee.setId(employeeDto.getId());
        employee.setEmail(employeeDto.getEmail());
        employee.setName(employeeDto.getName());
        employee.setPhNo(employeeDto.getPhNo());
        employee.setAccountNumber(employeeDto.getAccountNumber());
        return employee;
    }


}