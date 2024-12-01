package main.java.com.example.demo.controller;

import main.java.com.example.demo.model.Enterprise;
import main.java.com.example.demo.service.EnterpriseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/enterprises")
public class EnterpriseController {
    private final EnterpriseService enterpriseService;

    public EnterpriseController(EnterpriseService enterpriseService) {
        this.enterpriseService = enterpriseService;
    }

    @GetMapping
    public String listEnterprises(Model model) {
        List<Enterprise> enterprises = enterpriseService.getAllEnterprises();
        model.addAttribute("enterprises", enterprises);
        return "enterprises";
    }

    @PostMapping("/add")
    public String addEnterprise(@RequestParam String name) {
        Enterprise enterprise = new Enterprise();
        enterprise.setName(name);
        enterpriseService.addEnterprise(enterprise);
        return "redirect:/enterprises";
    }

    @GetMapping("/{name}")
    public String enterprisePage(@PathVariable String name, Model model) {
        Enterprise enterprise = enterpriseService.getAllEnterprises().stream()
                .filter(e -> e.getName().equals(name))
                .findFirst()
                .orElse(null);
        model.addAttribute("enterprise", enterprise);
        return "enterprise";
    }
}