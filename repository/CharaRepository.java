package com.ecosystem.backend.repository;

import com.ecosystem.backend.entity.Chara;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharaRepository
        extends JpaRepository<Chara, Long> {
}