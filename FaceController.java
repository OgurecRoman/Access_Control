package mokserver.mokapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import mokserver.mokapi.service.FaceService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/model")
public class FaceController {
    private final FaceService faceService;

    public FaceController(FaceService faceService) {
        this.faceService = faceService;
    }

    @PostMapping("/add_face")
    public ResponseEntity<Map<String, Object>> addFace(
            @RequestParam("data") String data,
            @RequestParam("file") MultipartFile file) {
        try {
            Map<String, Object> json = new ObjectMapper().readValue(data, Map.class);
            String uuid = (String) json.get("uuid");
            faceService.addFace(uuid, file);
            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "message", "Face added successfully.",
                    "uuid", uuid
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "error",
                    "message", e.getMessage()
            ));
        }
    }

    @DeleteMapping("/remove_face")
    public ResponseEntity<Map<String, Object>> removeFace(@RequestBody Map<String, String> body) {
        String uuid = body.get("uuid");
        boolean removed = faceService.removeFace(uuid);
        if (removed) {
            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "message", "Face removed successfully."
            ));
        }
        return ResponseEntity.badRequest().body(Map.of(
                "status", "error",
                "message", "Face not found."
        ));
    }

    @PostMapping("/check_face")
    public ResponseEntity<Map<String, Object>> checkFace(@RequestParam("file") MultipartFile file) {
        String uuid = faceService.checkFace(file);
        if (uuid != null) {
            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "message", "Face recognized.",
                    "uuid", uuid
            ));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                "status", "error",
                "message", "Face not recognized."
        ));
    }

    @GetMapping("/get_faces")
    public ResponseEntity<Map<String, Object>> getFaces() {
        List<String> faces = faceService.getFaces();
        return ResponseEntity.ok(Map.of(
                "status", "success",
                "faces", faces.stream().map(uuid -> Map.of("uuid", uuid)).collect(Collectors.toList())
        ));
    }
}