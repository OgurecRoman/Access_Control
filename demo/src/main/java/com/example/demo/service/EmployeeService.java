package com.example.demo.service;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class EmployeeService {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
    public List<Employee> getAllEmployees() {
        try {
            List<Employee> employees = employeeRepository.findAll();
            logger.info("Successfully fetched employees from the database.");
            employees.forEach(employee -> logger.info("Employee details: {}", employee)); // Вывод всех полей
            return employees;
        } catch (Exception e) {
            logger.error("Error retrieving employees from the database: {}", e.getMessage(), e);
            throw e; // Rethrow to allow handling at the controller level
        }
    }
}
