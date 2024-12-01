package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer age;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @Column(name = "id_of_enterprise", nullable = false)
    private Long idOfEnterprise;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public LocalDateTime getDateTime() { return dateTime; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }

    public Long getIdOfEnterprise() { return idOfEnterprise; }
    public void setIdOfEnterprise(Long idOfEnterprise) { this.idOfEnterprise = idOfEnterprise; }

    // Override toString() for logging purposes
    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", dateTime=" + dateTime +
                ", idOfEnterprise=" + idOfEnterprise +
                '}';
    }
}
