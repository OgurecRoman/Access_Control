package com.example.demo2;
import com.example.demo2.service.FaceRecognitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
@SpringBootApplication
@EntityScan(basePackages = "com.example.demo2.model")
@EnableJpaRepositories(basePackages = "com.example.demo2.repository")
@EnableScheduling
public class EmployeeTrackingApplication {
	@Autowired
	private FaceRecognitionService faceRecognitionService;
	public static void main(String[] args) {
		SpringApplication.run(EmployeeTrackingApplication.class, args);
	}
	@Scheduled(fixedRate = 10000)
	public void startFaceRecognition() {
		faceRecognitionService.processVideoStreams();
	}
}
