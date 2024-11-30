package com.example.demo;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import org.opencv.highgui.HighGui;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        // Путь к XML-файлу с классификатором лиц
        String faceCascadePath = "src/main/resources/haarcascade_frontalface_default.xml";
        File faceCascadeFile = new File(faceCascadePath);

        // Проверяем, существует ли файл классификатора
        if (!faceCascadeFile.exists()) {
            System.err.println("Ошибка: файл классификатора не найден: " + faceCascadePath);
            return;
        }

        CascadeClassifier faceCascade = new CascadeClassifier(faceCascadePath);

        // Проверяем, загрузился ли классификатор
        if (faceCascade.empty()) {
            System.err.println("Ошибка: не удалось загрузить классификатор лиц из " + faceCascadePath);
            return;
        }
        System.out.println("Классификатор лиц успешно загружен.");

        // Создаем объект для захвата видео с камеры
        VideoCapture capture = new VideoCapture(0); // 0 - это первая камера
        if (!capture.isOpened()) {
            System.err.println("Ошибка: не удалось открыть камеру!");
            return;
        }
        System.out.println("Камера успешно открыта.");

        Mat frame = new Mat();
        long lastSaveTime = System.currentTimeMillis(); // Время последнего сохранения
        String outputFolder = "captured_faces"; // Папка для сохранения лиц
        File folder = new File(outputFolder);
        if (!folder.exists() && !folder.mkdirs()) {
            System.err.println("Ошибка: не удалось создать папку для сохранения лиц: " + outputFolder);
            capture.release();
            return;
        }
        System.out.println("Папка для сохранения лиц: " + outputFolder);

        while (true) {
            boolean isFrameRead = capture.read(frame);
            if (!isFrameRead || frame.empty()) {
                System.err.println("Ошибка: не удалось захватить кадр!");
                break;
            }

            // Преобразуем изображение в оттенки серого
            Mat gray = new Mat();
            Imgproc.cvtColor(frame, gray, Imgproc.COLOR_BGR2GRAY);

            // Выявляем лица на изображении
            MatOfRect faces = new MatOfRect();
            faceCascade.detectMultiScale(gray, faces);

            // Отображаем найденные лица
            for (Rect rect : faces.toArray()) {
                Imgproc.rectangle(frame, rect.tl(), rect.br(), new Scalar(0, 255, 0), 2);
            }

            // Проверяем, прошло ли 10 секунд с последнего сохранения
            if (System.currentTimeMillis() - lastSaveTime > 10000 && faces.toArray().length > 0) {
                saveFaces(frame, faces, outputFolder);
                lastSaveTime = System.currentTimeMillis();
            }

            // Отображаем результат
            HighGui.imshow("Face Recognition", frame);

            // Выход из программы по клавише 'q'
            if (HighGui.waitKey(1) == 'q') {
                break;
            }
        }

        capture.release(); // Закрытие захвата видео
        HighGui.destroyAllWindows(); // Закрытие всех окон
        System.out.println("Программа завершена.");
    }

    private static void saveFaces(Mat frame, MatOfRect faces, String outputFolder) {
        Rect[] faceArray = faces.toArray();
        int faceCounter = 1;

        for (Rect rect : faceArray) {
            // Извлекаем область с лицом
            Mat face = new Mat(frame, rect);

            // Создаем уникальное имя файла
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String filename = String.format("%s/face_%s_%d.png", outputFolder, timestamp, faceCounter);

            boolean isSaved = Imgcodecs.imwrite(filename, face);
            if (isSaved) {
                System.out.println("Сохранено лицо: " + filename);
            } else {
                System.err.println("Ошибка: не удалось сохранить лицо в " + filename);
            }
            faceCounter++;
        }
    }
}
