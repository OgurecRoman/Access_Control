package com.example.demo.service;
import com.example.demo.model.Camera;
import com.example.demo.repository.CameraRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class CameraService {
    private static final Logger logger = LoggerFactory.getLogger(CameraService.class);
    private final CameraRepository cameraRepository;
    public CameraService(CameraRepository cameraRepository) {
        this.cameraRepository = cameraRepository;
    }
    public List<Camera> getAllCameras() {
        try {
            List<Camera> cameras = cameraRepository.findAll();
            logger.info("Successfully fetched cameras from the database.");
            cameras.forEach(camera -> logger.info("Camera details: {}", camera));
            return cameras;
        } catch (Exception e) {
            logger.error("Error retrieving cameras from the database: {}", e.getMessage(), e);
            throw e;
        }
    }
    public Camera addCamera(Camera camera) {
        try {
            Camera savedCamera = cameraRepository.save(camera);
            logger.info("Camera added: {}", savedCamera);
            return savedCamera;
        } catch (Exception e) {
            logger.error("Error adding camera: {}", e.getMessage(), e);
            throw e;
        }
    }
}