package com.example.demo.controller;

import com.example.demo.model.Camera;
import com.example.demo.service.CameraService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cameras")
public class CameraController {
    private final CameraService cameraService;

    public CameraController(CameraService cameraService) {
        this.cameraService = cameraService;
    }

    @GetMapping
    public String listCameras(Model model) {
        List<Camera> cameras = cameraService.getAllCameras();
        model.addAttribute("cameras", cameras);
        return "cameras";
    }

    @PostMapping("/add")
    public String addCamera(@RequestParam String ip, @RequestParam Long idOfEnterprise) {
        Camera camera = new Camera();
        camera.setIp(ip);
        camera.setIdOfEnterprise(idOfEnterprise);
        cameraService.addCamera(camera);
        return "redirect:/cameras";
    }
}