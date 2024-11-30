package com.example.demo2;

import com.example.demo2.service.FaceRecognitionService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
@EnableScheduling
public class EmployeeTrackingApplication {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeTrackingApplication.class);

	@Autowired
	private FaceRecognitionService faceRecognitionService;

	public static void main(String[] args) {
		SpringApplication.run(EmployeeTrackingApplication.class, args);
	}

	@Scheduled(fixedRate = 1000)
	public void startFaceRecognition() {
		logger.info("Starting face recognition process");
		faceRecognitionService.processVideoStreams();
	}
}