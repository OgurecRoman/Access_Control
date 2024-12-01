package main.java.com.example.demo.controller;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import org.springframework.web.bind.annotation.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
@RestController
@RequestMapping("/api")
public class FaceController {
    private boolean faceDetected = false; // To store face detection status
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
    public boolean getFaceDetectionStatus(@RequestParam(required = false) String enterprise) {
        if ("TEST".equalsIgnoreCase(enterprise)) {
            faceDetected = isFaceDetected(); // Update faceDetected based on actual detection
            System.out.println("Статус обнаружения лица для TEST: " + faceDetected);
        }
        System.out.println("Отправляем статус лица клиенту: " + faceDetected);
        return faceDetected;
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
                saveFaces(frame, faces);
            } else {
                System.out.println("Лица не обнаружены.");
            }
        } else {
            System.err.println("Не удалось считать кадр.");
        }
        capture.release();
        return faceFound;
    }
    private void saveFaces(Mat frame, MatOfRect faces) {
        String outputFolder = "captured_faces";
        File folder = new File(outputFolder);
        if (!folder.exists() && !folder.mkdirs()) {
            System.err.println("Не удалось создать папку для лиц.");
            return;
        }
        Rect[] faceArray = faces.toArray();
        for (int i = 0; i < faceArray.length; i++) {
            Mat face = new Mat(frame, faceArray[i]);
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String filename = String.format("%s/face_%s_%d.png", outputFolder, timestamp, i + 1);
            if (Imgcodecs.imwrite(filename, face)) {
                System.out.println("Лицо сохранено: " + filename);
            } else {
                System.err.println("Ошибка сохранения: " + filename);
            }
        }
    }
}
