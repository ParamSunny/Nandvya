package com.ecosystem.backend.repository;
import java.util.List;

import com.ecosystem.backend.entity.User;
import com.ecosystem.backend.entity.Waste;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WasteRepository
        extends JpaRepository<Waste, Long> {

    List<Waste> findByUser(User user);
}