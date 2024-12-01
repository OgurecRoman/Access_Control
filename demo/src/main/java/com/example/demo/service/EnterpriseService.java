package com.example.demo.service;

import com.example.demo.model.Enterprise;
import com.example.demo.repository.EnterpriseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EnterpriseService {
    private static final Logger logger = LoggerFactory.getLogger(EnterpriseService.class);
    private final EnterpriseRepository enterpriseRepository;

    public EnterpriseService(EnterpriseRepository enterpriseRepository) {
        this.enterpriseRepository = enterpriseRepository;
    }

    public List<Enterprise> getAllEnterprises() {
        try {
            List<Enterprise> enterprises = enterpriseRepository.findAll();
            logger.info("Successfully fetched enterprises from the database.");
            enterprises.forEach(enterprise -> logger.info("Enterprise details: {}", enterprise));
            return enterprises;
        } catch (Exception e) {
            logger.error("Error retrieving enterprises from the database: {}", e.getMessage(), e);
            throw e;
        }
    }

    public Enterprise addEnterprise(Enterprise enterprise) {
        try {
            Enterprise savedEnterprise = enterpriseRepository.save(enterprise);
            logger.info("Enterprise added: {}", savedEnterprise);
            return savedEnterprise;
        } catch (Exception e) {
            logger.error("Error adding enterprise: {}", e.getMessage(), e);
            throw e;
        }
    }
}