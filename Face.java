package mokserver.mokapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.springframework.data.annotation.Id;

//@Entity
public class Face {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
    private String uuid;
    private String filePath;

    public Face() {}
    public Face(String uuid, String filePath) {
        this.uuid = uuid;
        this.filePath = filePath;
    }
    public String getUuid() {
        return uuid;
    }
}