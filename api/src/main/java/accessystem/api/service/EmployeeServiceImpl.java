package accessystem.api.service;

import accessystem.api.model.Employee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private static final Map<Integer, Employee> CLIENT_REPOSITORY_MAP = new HashMap<>();


    private static final AtomicInteger CLIENT_ID_HOLDER = new AtomicInteger();

    @Override
    public void create(Employee employee) {
        final int clientId = CLIENT_ID_HOLDER.incrementAndGet();
        employee.setId(clientId);
        CLIENT_REPOSITORY_MAP.put(clientId, employee);
    }

    @Override
    public List<Employee> readAll() {
        return new ArrayList<>(CLIENT_REPOSITORY_MAP.values());
    }

    @Override
    public Employee read(int id) {
        return CLIENT_REPOSITORY_MAP.get(id);
    }

    @Override
    public boolean update(Employee employee, int id) {
        if (CLIENT_REPOSITORY_MAP.containsKey(id)) {
            employee.setId(id);
            CLIENT_REPOSITORY_MAP.put(id, employee);
            return true;
        }

        return false;
    }

    @Override
    public boolean delete(int id) {
        return CLIENT_REPOSITORY_MAP.remove(id) != null;
    }
}