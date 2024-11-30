package com.example.demo2.repository;

import com.example.demo2.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByFirstNameAndLastName(String firstName, String lastName);
}