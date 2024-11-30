package com.example.demo;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class FaceRecognition {
    static {
        try {
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            System.out.println("Библиотека OpenCV успешно загружена.");
        } catch (UnsatisfiedLinkError e) {
            System.err.println("Ошибка: не удалось загрузить библиотеку OpenCV!");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        String faceCascadePath = "src/main/resources/haarcascade_frontalface_default.xml";
        CascadeClassifier faceCascade = new CascadeClassifier(faceCascadePath);

        if (faceCascade.empty()) {
            System.err.println("Ошибка: не удалось загрузить классификатор лиц из " + faceCascadePath);
            return;
        }
        System.out.println("Классификатор лиц успешно загружен.");

        VideoCapture capture = new VideoCapture(0); // Камера
        if (!capture.isOpened()) {
            System.err.println("Ошибка: не удалось открыть камеру!");
            return;
        }

        Mat frame = new Mat();

        while (true) {
            if (!capture.read(frame) || frame.empty()) {
                System.err.println("Ошибка: не удалось захватить кадр!");
                break;
            }

            Mat gray = new Mat();
            Imgproc.cvtColor(frame, gray, Imgproc.COLOR_BGR2GRAY);

            MatOfRect faces = new MatOfRect();
            faceCascade.detectMultiScale(gray, faces);

            boolean faceDetected = faces.toArray().length > 0;
            sendFaceDetectionResult(faceDetected);

            try {
                Thread.sleep(10000); // Проверка каждые 10 секунд
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        capture.release();
    }

    private static void sendFaceDetectionResult(boolean faceDetected) {
        try {
            URL url = new URL("http://localhost:8080/api/check_face");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            String jsonInput = String.format("{\"faceDetected\":%b}", faceDetected);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(jsonInput.getBytes(StandardCharsets.UTF_8));
                os.flush();
            }

            if (conn.getResponseCode() == 200) {
                System.out.println("Результат отправлен успешно: " + jsonInput);
            } else {
                System.err.println("Ошибка отправки данных: " + conn.getResponseCode());
            }

            conn.disconnect();
        } catch (Exception e) {
            System.err.println("Ошибка при отправке запроса: " + e.getMessage());
        }
    }
}
