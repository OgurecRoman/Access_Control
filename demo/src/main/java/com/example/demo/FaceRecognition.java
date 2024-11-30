package com.example.demo;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class FaceRecognition {

    static {
        try {
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME); // Загружаем OpenCV
            System.load("S:/opencv/opencv/build/bin/opencv_videoio_ffmpeg470_64.dll");
            System.out.println("OpenCV загружен.");
        } catch (UnsatisfiedLinkError e) {
            System.err.println("Не удалось загрузить OpenCV!");
            e.printStackTrace();
            System.exit(1);
        }
    }

    private boolean isFaceDetected() {
        String faceCascadePath = "src/main/resources/haarcascade_frontalface_default.xml";
        CascadeClassifier faceCascade = new CascadeClassifier(faceCascadePath);

        if (faceCascade.empty()) {
            System.err.println("Ошибка загрузки классификатора.");
            return false;
        }

        String streamUrl = "rtmp://localhost/live/demo";
        VideoCapture capture = new VideoCapture(streamUrl); // Загружаем поток

        if (!capture.isOpened()) {
            System.err.println("Ошибка открытия видеопотока!");
            return false;
        }

        Mat frame = new Mat();
        boolean faceFound = false;

        // Only check for faces once for the API response
        if (capture.read(frame) && !frame.empty()) {
            Mat gray = new Mat();
            Imgproc.cvtColor(frame, gray, Imgproc.COLOR_BGR2GRAY);

            MatOfRect faces = new MatOfRect();
            faceCascade.detectMultiScale(gray, faces);

            if (faces.toArray().length > 0) {
                faceFound = true;
                saveFaces(frame, faces);
            }
        }

        capture.release();
        return faceFound;
    }

    private static void saveFaces(Mat frame, MatOfRect faces) {
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

    @GetMapping("/api/check_face_status")
    public boolean checkFaceStatus() {
        return isFaceDetected();
    }
}
