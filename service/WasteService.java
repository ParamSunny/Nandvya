package com.ecosystem.backend.service;

import com.ecosystem.backend.entity.Waste;

import java.security.Principal;
import java.util.List;

public interface WasteService {

    Waste save(Waste waste);

    List<Waste> getAll();

    Waste getById(Long id);

    void delete(Long id);
}