package mock_server.employee_structure;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
  
    private Long id;
    private String uuid; 
    private String filePath;

    public Employee() {}
    public Employee(String uuid, String filePath) {
        this.uuid = uuid;
        this.filePath = filePath;
    }
    public String getUuid() {
        return uuid;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
