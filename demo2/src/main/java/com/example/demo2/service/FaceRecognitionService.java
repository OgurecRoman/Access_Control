package com.example.demo2.service;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo2.model.Employee;
import com.example.demo2.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class FaceRecognitionService {

    private static final Logger logger = LoggerFactory.getLogger(FaceRecognitionService.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    static {
        System.setProperty("java.library.path", "C:\\Users\\borga\\Downloads\\opencv\\build\\java\\x64");
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    private CascadeClassifier faceDetector;

    public FaceRecognitionService() {
        // Загружаем классификатор для распознавания лиц
        faceDetector = new CascadeClassifier(getClass().getClassLoader().getResource("haarcascade_frontalface_default.xml").getPath());
    }

    public void processVideoStreams() {
        // Локальные пути к видеофайлам
        String videoPath1 = "C:/Users/borga/Downloads/кирби падает в яму мем.mp4";
        String videoPath2 = "C:/Users/borga/Downloads/кирби падает в яму мем.mp4";
        VideoCapture capture1 = new VideoCapture(videoPath1);
        VideoCapture capture2 = new VideoCapture(videoPath2);
        Mat frame1 = new Mat();
        Mat frame2 = new Mat();

        try {
            while (true) {
                if (capture1.read(frame1)) {
                    processFrame(frame1, "entry");
                }

                if (capture2.read(frame2)) {
                    processFrame(frame2, "exit");
                }

                // Задержка для стабильности
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            logger.error("Thread interrupted", e);
        } finally {
            capture1.release();
            capture2.release();
        }
    }

    private void processFrame(Mat frame, String location) {
        MatOfRect faces = new MatOfRect();
        faceDetector.detectMultiScale(frame, faces);

        List<Rect> faceList = faces.toList();

        for (Rect face : faceList) {
            Imgproc.rectangle(frame, face.tl(), face.br(), new Scalar(0, 255, 0), 2);

            // Распознавание лица и сравнение с базой данных
            Mat faceMat = new Mat(frame, face);
            String recognizedEmployee = recognizeFace(faceMat);

            if (recognizedEmployee != null) {
                updateEmployeeStatus(recognizedEmployee, location);
            }
        }
    }

    private String recognizeFace(Mat faceMat) {
        // Здесь должна быть логика распознавания лица
        // Например, сравнение с базой данных лиц
        // Возвращаем имя сотрудника, если лицо распознано
        return "John Doe"; // Пример
    }

    private void updateEmployeeStatus(String employeeName, String location) {
        String[] nameParts = employeeName.split(" ");
        Employee employee = null;

        try {
            employee = employeeRepository.findByFirstNameAndLastName(nameParts[0], nameParts[1]);
        } catch (Exception e) {
            logger.error("Error accessing the database: " + e.getMessage());
        }

        if (employee == null) {
            // Создаем дефолтного сотрудника
            employee = new Employee();
            employee.setFirstName(nameParts[0]);
            employee.setLastName(nameParts[1]);
            employee.setPhotoUrl("default_photo_url");
            employee.setStatus(false);
            employee = employeeRepository.save(employee);
            logger.info("Created default employee: " + employeeName);
        }

        if ("entry".equals(location)) {
            employee.setStatus(true);
        } else if ("exit".equals(location)) {
            employee.setStatus(false);
        }
        employeeRepository.save(employee);
    }
}