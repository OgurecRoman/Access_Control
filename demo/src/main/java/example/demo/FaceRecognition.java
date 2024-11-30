package main.java.example.demo;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import org.opencv.highgui.HighGui;

public class FaceRecognition {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        // Путь к xml-файлу с классификатором лиц
        String faceCascadePath = "src/main/resources/haarcascade_frontalface_default.xml";
        CascadeClassifier faceCascade = new CascadeClassifier(faceCascadePath);

        // Создаем объект для захвата видео с камеры
        VideoCapture capture = new VideoCapture(0); // 0 - это первая камера
        if (!capture.isOpened()) {
            System.out.println("Ошибка при открытии камеры!");
            return;
        }

        Mat frame = new Mat();
        while (true) {
            capture.read(frame);  // Чтение кадра с камеры
            if (frame.empty()) {
                System.out.println("Не удалось захватить кадр!");
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

            // Отображаем результат
            HighGui.imshow("Face Recognition", frame);

            // Выход из программы по клавише 'q'
            if (HighGui.waitKey(1) == 'q') {
                break;
            }
        }

        capture.release(); // Закрытие захвата видео
        HighGui.destroyAllWindows(); // Закрытие всех окон
    }
}


