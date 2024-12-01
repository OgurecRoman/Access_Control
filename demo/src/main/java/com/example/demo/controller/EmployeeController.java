// src/main/java/com/example/demo/controller/EmployeeController.java
package com.example.demo.controller;
import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    @PostMapping("/add")
    public ResponseEntity<?> addEmployee(
            @RequestParam("name") String name,
            @RequestParam("age") int age,
            @RequestParam("idOfEnterprise") Long idOfEnterprise,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam("fromCamera") boolean fromCamera) {
        Employee employee = new Employee();
        employee.setName(name);
        employee.setAge(age);
        employee.setIdOfEnterprise(idOfEnterprise);
        boolean success = employeeService.addEmployee(employee, file, fromCamera);
        return success
                ? ResponseEntity.ok("Employee added successfully.")
                : ResponseEntity.badRequest().body("Failed to add employee.");
    }
    @DeleteMapping("/remove/{id}")
    public ResponseEntity<?> removeEmployee(@PathVariable Long id) {
        boolean success = employeeService.removeEmployee(id);
        return success
                ? ResponseEntity.ok("Employee removed successfully.")
                : ResponseEntity.badRequest().body("Employee not found.");
    }
    @GetMapping("/poll")
    public ResponseEntity<?> poll() {
        boolean result = employeeService.poll();
        return ResponseEntity.ok(result);
    }
    @GetMapping("/getall")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }
}
