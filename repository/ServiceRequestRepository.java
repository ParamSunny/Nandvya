package com.ecosystem.backend.repository;

import com.ecosystem.backend.entity.ServiceRequest;
import com.ecosystem.backend.entity.ServiceStatus;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceRequestRepository
        extends JpaRepository<ServiceRequest, Long> {


    List<ServiceRequest> findByUserId(Long userId);

    List<ServiceRequest> findByAcceptedByUserId(Long userId);

    // combined filter
    List<ServiceRequest> findByAcceptedByUserIdAndStatus(
            Long userId,
            ServiceStatus status
    );

    List<ServiceRequest> findByStatus(
            ServiceStatus status
    );


    List<ServiceRequest> findByStatusIn(
            List<ServiceStatus> status
    );

    List<ServiceRequest> findByUserIdAndStatus(
            Long userId,
            ServiceStatus status
    );


    List<ServiceRequest> findByStatusAndUserId(
            ServiceStatus status,
            Long userId
    );

}