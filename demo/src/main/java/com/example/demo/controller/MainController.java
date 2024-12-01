package main.java.com.example.demo.controller;

import main.java.com.example.demo.model.Employee;
import main.java.com.example.demo.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);
    private final EmployeeService employeeService;

    public MainController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public String employees(Model model) {
        try {
            List<Employee> employees = employeeService.getAllEmployees();
            logger.info("Fetched {} employees from the database.", employees.size());
            model.addAttribute("employees", employees);
        } catch (Exception e) {
            logger.error("Error fetching employees: {}", e.getMessage(), e);
            model.addAttribute("error", "Unable to load employees.");
        }
        return "employees";
    }

    @GetMapping("/")
    public String index() {
        logger.info("Accessed index page.");
        return "index";
    }
}