package com.example.demo.repository;
import com.example.demo.model.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface EnterpriseRepository extends JpaRepository<Enterprise, Long> {
}