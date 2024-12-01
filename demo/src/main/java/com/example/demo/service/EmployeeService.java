// src/main/java/com/example/demo/service/EmployeeService.java
package com.example.demo.service;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
    public boolean addEmployee(Employee employee, MultipartFile file, boolean fromCamera) {
        try {
            String photo = fromCamera ? processPhotoFromCamera(file) : "";
            employee.setPhoto(photo);
            employeeRepository.save(employee);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public boolean removeEmployee(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            employeeRepository.delete(employee.get());
            return true;
        }
        return false;
    }
    public boolean poll() {
        return new Random().nextBoolean();
    }
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    private String processPhotoFromCamera(MultipartFile file) {
        return file != null ? "processed_photo_data" : "";
    }
}
