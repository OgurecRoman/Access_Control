package main.java.com.example.demo.employee_structure;

import org.springframework.data.jpa.repository.JpaRepository;
/* Интерфейс, предоставляемый Spring Data JPA,
который содержит методы для выполнения операций CRUD
и другие операции с сущностями. */
import org.springframework.stereotype.Repository;
/* Аннотация, которая указывает, что интерфейс
является репозиторием, что позволяет Spring
автоматически обнаруживать его и создавать реализацию. */

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByUuid(String uuid);
    /* Метод автоматически генерируется Spring Data JPA. Он
    позволяет находить все объекты Face, у которых поле uuid
    соответствует переданному значению. Возвращает список
    найденных объектов. */
    void deleteByUuid(String uuid);
    /* Метод также автоматически генерируется.
    Он позволяет удалять все объекты Face,
    у которых поле uuid соответствует переданному значению. */
}
