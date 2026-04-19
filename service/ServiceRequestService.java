package com.ecosystem.backend.service;

import com.ecosystem.backend.entity.*;

import java.util.List;

public interface ServiceRequestService {

    ServiceRequest save(ServiceRequest s);

    List<ServiceRequest> getAll();

    ServiceRequest getById(Long id);

    void delete(Long id);

    List<ServiceRequest> getByUserId(Long userId);

    List<ServiceRequest> getByStatus(
            ServiceStatus status
    );

    List<ServiceRequest> getActiveForService();

    List<ServiceRequest> getByAcceptedUser(Long userId);

    List<ServiceRequest> getByAcceptedUserAndStatus(
            Long userId,
            ServiceStatus status
    );

}