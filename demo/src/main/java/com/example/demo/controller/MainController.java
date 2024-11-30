package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/employees")
    public String employees(Model model) {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Иван Иванов", "на объекте", "ivan.jpg"));
        employees.add(new Employee("Петр Петров", "не на объекте", "petr.jpg"));
        employees.add(new Employee("Сергей Сергеев", "на объекте", "sergey.jpg"));

        model.addAttribute("employees", employees);
        return "employees";
    }

    @GetMapping("/video")
    public String video() {
        return "video";
    }

    public static class Employee {
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
    }
}