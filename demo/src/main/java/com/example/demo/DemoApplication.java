package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

		// Запуск FaceRecognition в отдельном потоке
		new Thread(() -> FaceRecognition.main(null)).start();
	}
}
