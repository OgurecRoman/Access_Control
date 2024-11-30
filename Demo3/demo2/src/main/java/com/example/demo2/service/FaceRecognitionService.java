package com.example.demo2.service;

import com.example.demo2.model.Employee;
import org.opencv.core.*;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FaceRecognitionService {

    private static final Logger logger = LoggerFactory.getLogger(FaceRecognitionService.class);

    private final List<Employee> employeeList = List.of(
            new Employee(1L, "John", "Doe", "/images/john_doe.jpg", false),
            new Employee(2L, "Jane", "Smith", "/images/jane_smith.jpg", true)
    );
    private final CascadeClassifier faceDetector;

    static {
        System.setProperty("java.library.path", "C:\\Users\\borga\\Downloads\\opencv\\build\\java\\x64");
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public FaceRecognitionService() {
        faceDetector = new CascadeClassifier(getClass().getClassLoader()
                .getResource("haarcascade_frontalface_default.xml").getPath());
    }

    public void processVideoStreams() {
        VideoCapture capture = new VideoCapture(0); // Камера по умолчанию
        Mat frame = new Mat();

        try {
            while (true) {
                if (capture.read(frame)) {
                    processFrame(frame);
                }
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            logger.error("Thread interrupted", e);
        } finally {
            capture.release();
        }
    }

    private void processFrame(Mat frame) {
        MatOfRect faces = new MatOfRect();
        faceDetector.detectMultiScale(frame, faces);

        for (Rect face : faces.toArray()) {
            String recognizedEmployee = recognizeFace();
            if (recognizedEmployee != null) {
                updateEmployeeStatus(recognizedEmployee);
            }
        }
    }

    private String recognizeFace() {
        // Пример распознавания
        return "John Doe";
    }

    private void updateEmployeeStatus(String employeeName) {
        employeeList.stream()
                .filter(e -> (e.getFirstName() + " " + e.getLastName()).equals(employeeName))
                .findFirst()
                .ifPresent(employee -> employee.setStatus(true));
    }
}
