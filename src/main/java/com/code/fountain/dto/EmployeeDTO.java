package com.code.fountain.dto;

import com.code.fountain.util.DataProtect;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDTO implements Serializable {

    private Long id;
    private String name;
    private String email;
    private Long phNo;
    private Long accountNumber;
    private Double salary;

}