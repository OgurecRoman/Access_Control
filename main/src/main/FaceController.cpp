package com.example.demo.controller;

import com.example.demo.model.Employee;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import org.springframework.web.bind.annotation.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Random;
import com.example.demo.service.EmployeeService;

@RestController
@RequestMapping("/api")
public class FaceController {
    private EmployeeService employeeService;
    private boolean faceDetected = false; // To store face detection status
    public FaceController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    static {
        try {
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            System.load("S:/opencv/opencv/build/bin/opencv_videoio_ffmpeg470_64.dll");
            System.out.println("OpenCV загружен.");
        } catch (UnsatisfiedLinkError e) {
            System.err.println("Не удалось загрузить OpenCV!");
            e.printStackTrace();
            System.exit(1);
        }
    }
    @GetMapping("/check_face_status")
    public Map<String, Boolean> getFaceDetectionStatus(@RequestParam(required = false) String enterprise) {
        if ("TEST".equalsIgnoreCase(enterprise)) {
            faceDetected = isFaceDetected(); // Update faceDetected based on actual detection
            System.out.println("Статус обнаружения лица для TEST: " + faceDetected);
        }
        else {faceDetected = new Random().nextBoolean();}
        System.out.println("Отправляем статус лица клиенту: " + faceDetected);
        Map<String, Boolean> response = new HashMap<>();
        response.put("faceDetected", faceDetected);
        return response;
    }
    private boolean isFaceDetected() {
        String faceCascadePath = "src/main/resources/haarcascade_frontalface_default.xml";
        File cascadeFile = new File(faceCascadePath);
        if (!cascadeFile.exists()) {
            System.err.println("Файл классификатора не найден: " + faceCascadePath);
            return false;
        }
        CascadeClassifier faceCascade = new CascadeClassifier(faceCascadePath);
        if (faceCascade.empty()) {
            System.err.println("Ошибка загрузки классификатора.");
            return false;
        }
        String streamUrl = "rtmp://localhost/live/demo";
        VideoCapture capture = new VideoCapture(streamUrl);
        if (!capture.isOpened()) {
            System.err.println("Ошибка открытия видеопотока!");
            return false;
        }
        Mat frame = new Mat();
        boolean faceFound = false;
        if (capture.read(frame) && !frame.empty()) {
            System.out.println("Кадр успешно считан.");
            Mat gray = new Mat();
            Imgproc.cvtColor(frame, gray, Imgproc.COLOR_BGR2GRAY);
            MatOfRect faces = new MatOfRect();
            faceCascade.detectMultiScale(gray, faces);
            if (faces.toArray().length > 0) {
                faceFound = true;

                System.out.println("Лица обнаружены.");
                if (employeeService != null) {
                    List<Employee> employees = employeeService.getAllEmployees();
                    Random random = new Random();
                    if (!employees.isEmpty()) {
                        Employee employee = employees.get(random.nextInt(employees.size()));
                        employee.setDateTime(LocalDateTime.now());
                        saveFaces(frame, faces);
                    }
                }
            } else {
                System.out.println("Лица не обнаружены.");
            }
        } else {
            System.err.println("Не удалось считать кадр.");
        }
        capture.release();
        return faceFound;
    }
    private Mat saveFaces(Mat frame, MatOfRect faces) {
        Rect[] faceArray = faces.toArray();
        Mat face = new Mat(frame, faceArray[0]);
        return face;
    }
}
