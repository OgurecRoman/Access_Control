package main.java.com.example.demo.employee_structure;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/* JPA определяет, как объекты Java могут быть связаны
с реляционными базами данных, а также как выполнять
операции с этими данными, такие как создание, чтение,
обновление и удаление. */

@Entity // Указывает, что класс Employee является сущностью, которая будет отображаться в базе данных.
public class Employee {
    @Id // Указывает, что поле id является уникальным идентификатором для этой сущности.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /* Указывает, что значение поля id будет автоматически генерироваться
    базой данных (например, с использованием автоинкремента). */
    private Long id; // Поле для хранения уникального идентификатора сущности.
    private String uuid; // Поле для хранения UUID (уникального идентификатора) объекта.
    private String filePath; // Поле для хранения фото

    public Employee() {} // Пустой конструктор, необходимый для JPA.
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
