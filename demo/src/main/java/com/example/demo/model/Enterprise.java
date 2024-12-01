package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "enterprises")
public class Enterprise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return "Enterprise{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}