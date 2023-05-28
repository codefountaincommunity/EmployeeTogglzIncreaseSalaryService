package com.code.fountain.services.impl;

import com.code.fountain.exception.ResourceExistException;
import com.code.fountain.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import com.code.fountain.entities.Employee;
import com.code.fountain.dto.EmployeeDTO;
import com.code.fountain.services.EmployeeService;
import com.code.fountain.repositories.EmployeeRepository;
import org.togglz.core.Feature;
import org.togglz.core.manager.FeatureManager;
import org.togglz.core.util.NamedFeature;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private FeatureManager featureManager;
    public static final Feature INCREMENT_SALARY= new NamedFeature("INCREMENT_SALARY");
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee createEmployee(EmployeeDTO employeeDTO) {
        employeeRepository.findByEmail(employeeDTO.getEmail()).ifPresent(
                existingEmployee->{
            throw new ResourceExistException("employee with this mail "+existingEmployee.getEmail()+" already exists");
        });
        Employee employee = new Employee();
        Employee newEmployee=employee.dtoToEntity(employeeDTO);
        return employeeRepository.save(newEmployee);


    }


    @Override
    public Employee updateEmployee(Long id, EmployeeDTO employeeDTO) {
        Employee existingEmployee = getEmployeeById(id);
        Employee updatedEmployee = existingEmployee.dtoToEntity(employeeDTO);
        LOGGER.info("Employee updated");
        return employeeRepository.save(updatedEmployee);
    }

    @Override
    public Employee getEmployeeById(Long id) {
        LOGGER.info("getEmployeeId:" + id);
        return employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found by id "+id));
    }

    @Override
    public List<Employee> getAllEmployees() {
        LOGGER.info("getAll Employees");
        return employeeRepository.findAll();
    }

    @Override
    public Page<Employee> getAllEmployees(Pageable pageable) {
        LOGGER.info("getAll Pageable Employees");
        return employeeRepository.findAll(pageable);
    }

    @Override
    public void deleteEmployee(Long id) {
        Employee employee = getEmployeeById(id);
        //if(employee!=null) {
            LOGGER.info("delete Employee Id" + id);
            employeeRepository.delete(employee);
      //  }
        //else{
          //  throw new ResourceNotFoundException("Employee not found with id "+id);
       // }
    }

    @Override
    public void deleteAllEmployees() {
        LOGGER.info("delete Employees");
        employeeRepository.deleteAll();
    }
    @Override
    public String incrementSalary(Long id) {

            Optional<Employee> existingEmployee = employeeRepository.findById(id);
            if (existingEmployee.isPresent() ) {
                if(existingEmployee.get().getSalary()==null)
                    return  "salary not applied please try again";
                if(featureManager.isActive(INCREMENT_SALARY)) {
                    // Email already exists, throw an exception
                    Double salary = existingEmployee.get().getSalary();
                    Double increaseAmount = salary * 10 / 100.00;
                    salary = salary + increaseAmount;
                    existingEmployee.get().setSalary(salary);
                    employeeRepository.save(existingEmployee.get());
                    return "salary increased";
                }
                else{
                    return "salary increment not applied";
                }
            } else {

                throw new ResourceNotFoundException("employee wiht this id not found " + id);
            }


    }

    }
