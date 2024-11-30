package accessystem.api.control;

import accessystem.api.model.Employee;
import accessystem.api.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController (EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    // Добавление нового сотрудника
    @PostMapping(value = "/addemployee")
    public ResponseEntity<?> create(@RequestBody Employee employee) {
        employeeService.create(employee);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // Получение списка всех сотрудников
    @GetMapping(value = "/employees")
    public ResponseEntity<List<Employee>> read() {
        final List<Employee> clients = employeeService.readAll();

        return clients != null &&  !clients.isEmpty()
                ? new ResponseEntity<>(clients, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Получение конкретного сотрудника по id
    @GetMapping(value = "/employees/{id}")
    public ResponseEntity<Employee> read(@PathVariable(name = "id") int id) {
        final Employee employee = employeeService.read(id);
        return employee != null
                ? new ResponseEntity<>(employee, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    // Изменение данных сотрудника
    @PutMapping(value = "/employees/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") int id, @RequestBody Employee employee) {
        final boolean updated = employeeService.update(employee, id);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    // Изменение состояние нахождения сотрудника в здании
    @GetMapping(value = "/changestatus/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable(name = "id") int id) {
        final Employee employee = employeeService.read(id);
        employee.setStatus(!employee.getStatus());
        final boolean updated = employeeService.update(employee, id);
        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    // Удаление сотрудника
    @DeleteMapping(value = "/employees/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        final boolean deleted = employeeService.delete(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}