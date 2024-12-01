package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cameras")
public class Camera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String ip;

    @Column(name = "id_of_enterprise", nullable = false)
    private Long idOfEnterprise;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getIp() { return ip; }
    public void setIp(String ip) { this.ip = ip; }

    public Long getIdOfEnterprise() { return idOfEnterprise; }
    public void setIdOfEnterprise(Long idOfEnterprise) { this.idOfEnterprise = idOfEnterprise; }

    @Override
    public String toString() {
        return "Camera{" +
                "id=" + id +
                ", ip='" + ip + '\'' +
                ", idOfEnterprise=" + idOfEnterprise +
                '}';
    }
}