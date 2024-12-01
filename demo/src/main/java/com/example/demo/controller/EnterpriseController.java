package com.example.demo.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
@Controller
@RequestMapping("/enterprises")
public class EnterpriseController {
    private final List<String> enterprises = new ArrayList<>();
    public EnterpriseController() {
        enterprises.add("TEST"); // Добавляем TEST по умолчанию
    }
    @GetMapping
    public String listEnterprises(Model model) {
        model.addAttribute("enterprises", enterprises);
        return "enterprises";
    }
    @PostMapping("/add")
    public String addEnterprise(@RequestParam String name) {
        enterprises.add(name);
        return "redirect:/enterprises";
    }
    @GetMapping("/{name}")
    public String enterprisePage(@PathVariable String name, Model model) {
        model.addAttribute("enterprise", name);
        return "enterprise";
    }
}
