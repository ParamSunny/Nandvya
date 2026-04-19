package com.ecosystem.backend.repository;

import com.ecosystem.backend.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository
        extends JpaRepository<Orders, Long> {
}