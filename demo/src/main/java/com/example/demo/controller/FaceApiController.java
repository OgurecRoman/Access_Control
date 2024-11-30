package main.java.com.example.demo.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class FaceApiController {

    private boolean faceDetected = false; // Хранит текущее состояние

    // Обработка POST-запроса от FaceRecognition
    @PostMapping("/check_face")
    public void checkFace(@RequestBody FaceDetectionRequest request) {
        this.faceDetected = request.faceDetected; // Сохраняем состояние
        System.out.println("Результат обнаружения лица: " + (faceDetected ? "Лицо обнаружено" : "Лицо не обнаружено"));
    }

    // Отправка состояния клиенту
    @GetMapping("/check_face_status")
    public boolean getFaceDetectionStatus() {
        System.out.println("Отправляем статус лица клиенту: " + faceDetected);
        return faceDetected;
    }


    // DTO-класс для приема JSON
    static class FaceDetectionRequest {
        public boolean faceDetected; // Поле для приема результата
    }
}
