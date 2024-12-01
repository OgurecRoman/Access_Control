package mokserver.mokapi.service;

import mokserver.mokapi.model.Face;
import mokserver.mokapi.model.FaceRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class FaceService {

    private final FaceRepository faceRepository;

    public FaceService(FaceRepository faceRepository) {
        this.faceRepository = faceRepository;
    }

    public void addFace(String uuid, MultipartFile file) throws IOException {
        String filePath = "uploads/" + file.getOriginalFilename();
        file.transferTo(new File(filePath));
        faceRepository.save(new Face(uuid, filePath));
    }

    public boolean removeFace(String uuid) {
        List<Face> faces = faceRepository.findByUuid(uuid);
        if (!faces.isEmpty()) {
            faceRepository.deleteByUuid(uuid);
            return true;
        }
        return false;
    }

    public String checkFace(MultipartFile file) {
        List<Face> faces = faceRepository.findAll();
        if (!faces.isEmpty()) return "null";
        Random random = new Random();
        int min = 10;
        int max = 20;
        int randomNumber = random.nextInt((max - min) + 1) + min;
        if (randomNumber > 10) return null;
        min = 0;
        max = faces.size() - 1;
        randomNumber = random.nextInt((max - min) + 1) + min;
        return faces.get(randomNumber).getUuid(); // Моковая реализация
    }

    public List<String> getFaces() {
        return faceRepository.findAll().stream()
                .map(Face::getUuid)
                .collect(Collectors.toList());
    }
}