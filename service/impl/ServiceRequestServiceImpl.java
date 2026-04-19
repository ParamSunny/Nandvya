package com.ecosystem.backend.service.impl;

import com.ecosystem.backend.entity.*;
import com.ecosystem.backend.repository.ServiceRequestRepository;
import com.ecosystem.backend.service.ServiceRequestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Arrays;

@Service
public class ServiceRequestServiceImpl
        implements ServiceRequestService {

    @Autowired
    ServiceRequestRepository repository;


    @Override
    public ServiceRequest save(ServiceRequest s) {
        return repository.save(s);
    }


    @Override
    public List<ServiceRequest> getAll() {
        return repository.findAll();
    }


    @Override
    public ServiceRequest getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Service not found"
                        ));
    }


    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }


    @Override
    public List<ServiceRequest> getByUserId(
            Long userId
    ) {
        return repository.findByUserId(userId);
    }


    @Override
    public List<ServiceRequest> getByStatus(
            ServiceStatus status
    ) {
        return repository.findByStatus(status);
    }


    @Override
    public List<ServiceRequest> getActiveForService() {

        return repository.findByStatusIn(
                Arrays.asList(
                        ServiceStatus.PENDING,
                        ServiceStatus.ACCEPTED,
                        ServiceStatus.IN_PROGRESS
                )
        );
    }

    @Override
    public List<ServiceRequest> getByAcceptedUser(Long userId) {
        return repository.findByAcceptedByUserId(userId);
    }

    @Override
    public List<ServiceRequest> getByAcceptedUserAndStatus(
            Long userId,
            ServiceStatus status
    ) {
        return repository.findByAcceptedByUserIdAndStatus(userId, status);
    }

}