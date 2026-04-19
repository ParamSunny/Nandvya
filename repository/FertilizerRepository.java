package com.ecosystem.backend.repository;

import com.ecosystem.backend.entity.Fertilizer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FertilizerRepository
        extends JpaRepository<Fertilizer, Long> {
}