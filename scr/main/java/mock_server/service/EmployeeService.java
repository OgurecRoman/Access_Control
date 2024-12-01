package mock_server.service;

import mock_server.employee_structure.Employee;
import mock_server.employee_structure.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
// Интерфейс для работы с загружаемыми файлами.

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
// Аннотация, указывающая, что этот класс является сервисом в Spring.
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public void addEmployee(String uuid, MultipartFile file) throws IOException {
        String filePath = "uploads/" + file.getOriginalFilename();
        file.transferTo(new File(filePath));
        employeeRepository.save(new Employee(uuid, filePath));
    }

    public boolean removeEmployee(String uuid) {
        List<Employee> employees = employeeRepository.findByUuid(uuid);
        if (!employees.isEmpty()) {
            employeeRepository.deleteByUuid(uuid);
            return true;
        }
        return false;
    }

    // Мок-проверка
    public String checkEmployee(MultipartFile file) {
        List<Employee> employees = employeeRepository.findAll();
        if (!employees.isEmpty()) return "null";
        Random random = new Random();
        int min = 10;
        int max = 20;
        int randomNumber = random.nextInt((max - min) + 1) + min;
        if (randomNumber > 10) return null;
        min = 0;
        max = employees.size() - 1;
        randomNumber = random.nextInt((max - min) + 1) + min;
        return employees.get(randomNumber).getUuid();
    }

    public List<String> getEmployees() {
        return employeeRepository.findAll().stream()
                .map(Employee::getUuid)
                .collect(Collectors.toList());
    }
}
