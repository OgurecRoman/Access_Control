package com.example.demo.controller;
import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
@Controller
public class MainController {
    private List<Depart> depart = new ArrayList<>();
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
    @GetMapping("/video")
    public String video() {
        return "video";
    }
    @GetMapping("/enterprise")
    public String department(Model model) {
        model.addAttribute("enterprise", depart);
        return "enterprise";
    }
    @GetMapping("/addEmployees")
    public String addEmployeeForm(Model model) {
        model.addAttribute("employees", new Employee());
        return "addEmployees";
    }
    @PostMapping("/addEmployee")
    public String addEmployee(@ModelAttribute Employee employee, @RequestParam("photo") MultipartFile file) throws IOException {
        // Сохранение файла в директорию "uploads"
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            Path path = Paths.get("uploads/" + fileName);
            Files.createDirectories(path.getParent());  // Создание папки, если она не существует
            file.transferTo(path);  // Сохранение файла на диск
            employee.setPhoto(fileName);  // Сохранение имени файла в объекте Employee
        }
        return "redirect:/employees";
    }
    @GetMapping("/addEnterprise")
    public String addDepartmentForm(Model model) {
        model.addAttribute("Enterprise", new Depart("", ""));
        return "addEnterprise";
    }
    @PostMapping("/addEnterprise")
    public String addDepartment(@ModelAttribute Depart department) throws IOException {
        depart.add(department);
        return "redirect:/enterprise";
    }
    @GetMapping("/")
    public String index() {
        logger.info("Accessed index page.");
        return "index";
    }
    public static class Depart {
        private String name;
        private String status;

        public Depart(String name, String status) {
            this.name = name;
            this.status = status;
        }
        public String getName() {
            return name;
        }
        public String getStatus() {
            return status;
        }
        public void setName(String name) {
            this.name = name;
        }
        public void setStatus(String status) {
            this.status = status;
        }
    }
}