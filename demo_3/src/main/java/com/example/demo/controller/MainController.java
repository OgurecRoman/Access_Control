package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    private List<Employee> employees = new ArrayList<>();
    private List<Depart> depart = new ArrayList<>();

    public MainController() {
        employees.add(new Employee("Иван Иванов", "на объекте", "ivan.jpg"));
        employees.add(new Employee("Петр Петров", "не на объекте", "petr.jpg"));
        employees.add(new Employee("Сергей Сергеев", "на объекте", "sergey.jpg"));
        depart.add(new Depart("Цех номер 1", "камера 1"));
        depart.add(new Depart("Цех номер 2", "камера 2"));
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/video")
    public String video() {
        return "video";
    }

    @GetMapping("/department")
    public String department(Model model) {
        model.addAttribute("department", depart);
        return "department";
    }

    @GetMapping("/employees")
    public String employees(Model model) {
        model.addAttribute("employees", employees);
        return "employees";
    }

    @GetMapping("/addEmployees")
    public String addEmployeeForm(Model model) {
        model.addAttribute("employees", new Employee("", "", ""));
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

        employees.add(employee);
        return "redirect:/employees";
    }

    @GetMapping("/addDepartment")
    public String addDepartmentForm(Model model) {
        model.addAttribute("Department", new Depart("", ""));
        return "addDepartment";
    }

    @PostMapping("/addDepartment")
    public String addDepartment(@ModelAttribute Depart department) throws IOException {
        depart.add(department);
        return "redirect:/department";
    }

    public static class Employee {
        private transient MultipartFile photoFile;
        private String name;
        private String status;
        private String photo;

        public Employee(String name, String status, String photo) {
            this.name = name;
            this.status = status;
            this.photo = photo;
        }

        public String getName() {
            return name;
        }

        public String getStatus() {
            return status;
        }

        public String getPhoto() {
            return photo;
        }

// сеттеры отсюда

        public void setName(String name) {
            this.name = name;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }
        // файлы фото

        public MultipartFile getPhotoFile() {
            return photoFile;
        }

        public void setPhotoFile(MultipartFile photoFile) {
            this.photoFile = photoFile;
        }
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
