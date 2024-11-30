package com.example.demo2.controller;

import com.example.demo2.model.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class EmployeeController {

    private final List<Employee> employeeList;

    public EmployeeController() {
        this.employeeList = new ArrayList<>();
        employeeList.add(new Employee(1L, "John", "Doe", "/images/john_doe.jpg", true));
        employeeList.add(new Employee(2L, "Jane", "Smith", "/images/jane_smith.jpg", false));
    }

    @GetMapping("/employees")
    public String getEmployees(Model model) {
        model.addAttribute("employees", employeeList);
        return "employees";
    }
}
