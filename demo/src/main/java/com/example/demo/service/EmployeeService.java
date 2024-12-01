package com.example.demo.service;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Random;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public boolean addEmployee(Employee employee, String file, boolean fromCamera) {
        try {
            String photo = file;
            employee.setPhoto(photo); // Установить фото или пустую строку
            employeeRepository.save(employee); // Сохранить в базу данных
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean removeEmployee(Long id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
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
        // Возвращаем обработанные данные (например, base64 или метаданные)
        return file != null ? "processed_photo_data" : "";
    }
}
