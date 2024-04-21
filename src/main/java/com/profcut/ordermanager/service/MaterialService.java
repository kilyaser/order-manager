package com.profcut.ordermanager.service;

import com.profcut.ordermanager.domain.dto.material.CreateMaterialRequest;
import com.profcut.ordermanager.domain.entities.MaterialEntity;

import java.util.UUID;

public interface MaterialService {

    MaterialEntity createMaterial(CreateMaterialRequest request);

    MaterialEntity findById(UUID materialId);
}
