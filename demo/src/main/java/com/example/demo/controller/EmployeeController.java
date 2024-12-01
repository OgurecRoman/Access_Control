package main.java.com.example.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import main.java.com.example.demo.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// использует ResponseEntity для формирования ответов с соответствующими статусами и сообщениями
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
/*
Указывает, что этот класс является контроллером, который
обрабатывает HTTP-запросы и возвращает данные в формате JSON.
 */
@RequestMapping("/structure")
/*
Указывает базовый путь для всех методов в этом контроллере.
Все запросы будут начинаться с /structure
 */
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/add_employee") // Обрабатывает POST-запросы на /model/add_employee
    public ResponseEntity<Map<String, Object>> addEmployee(
            @RequestParam("data") String data,
            @RequestParam("file") MultipartFile file
    ) {
        try {
            Map json;
            json = new ObjectMapper().readValue(data, Map.class);
            // ObjectMapper для преобразования строки JSON в Map
            String uuid = (String) json.get("uuid");
            employeeService.addEmployee(uuid, file);
            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "message", "Employee added successfully.",
                    "uuid", uuid
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "error",
                    "message", e.getMessage()
            ));
        }
    }

    @DeleteMapping("/remove_employee") // Обрабатывает DELETE-запросы на /model/remove_employee
    public ResponseEntity<Map<String, Object>> removeEmployee(@RequestBody Map<String, String> body) {
        String uuid = body.get("uuid");
        boolean removed = employeeService.removeEmployee(uuid);
        if (removed) {
            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "message", "Employee removed successfully."
            ));
        }
        return ResponseEntity.badRequest().body(Map.of(
                "status", "error",
                "message", "Employee not found."
        ));
    }

    @PostMapping("/check_employee")
    public ResponseEntity<Map<String, Object>> checkEmployee(@RequestParam("file") MultipartFile file) {
        String uuid = employeeService.checkEmployee(file);
        if (uuid != null) {
            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "message", "Employee recognized.",
                    "uuid", uuid
            ));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                "status", "error",
                "message", "Employee not recognized."
        ));
    }

    @GetMapping("/get_employees")
    public ResponseEntity<Map<String, Object>> getEmployees() {
        List<String> faces = employeeService.getEmployees();
        return ResponseEntity.ok(Map.of(
                "status", "success",
                "employees", faces.stream().map(uuid -> Map.of("uuid", uuid)).collect(Collectors.toList())
        ));
    }
}
