package com.profcut.ordermanager.service.impl;

import com.profcut.ordermanager.controllers.rest.mapper.MaterialCreateMapper;
import com.profcut.ordermanager.domain.dto.material.CreateMaterialRequest;
import com.profcut.ordermanager.domain.entities.MaterialEntity;
import com.profcut.ordermanager.domain.exceptions.MaterialNotFoundException;
import com.profcut.ordermanager.domain.repository.MaterialRepository;
import com.profcut.ordermanager.service.MaterialService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MaterialServiceImpl implements MaterialService {

    private final MaterialRepository materialRepository;
    private final MaterialCreateMapper materialCreateMapper;

    @Override
    @Transactional
    public MaterialEntity createMaterial(CreateMaterialRequest request) {
        log.info("invoke 'MaterialServiceImpl#createMaterial' with request: {}", request);
        var material = materialCreateMapper.apply(request);
        return materialRepository.save(material);
    }

    @Override
    @Transactional(readOnly = true)
    public MaterialEntity findById(UUID materialId) {
        return materialRepository.findById(materialId).orElseThrow(() -> MaterialNotFoundException.byMaterialId(materialId));
    }
}
