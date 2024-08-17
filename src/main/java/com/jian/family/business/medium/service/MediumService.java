package com.jian.family.business.medium.service;

import com.jian.family.business.medium.converter.MediumMapper;
import com.jian.family.business.medium.dto.MediumDto;
import com.jian.family.business.medium.dto.MediumListQuery;
import com.jian.family.business.medium.entity.MediumEntity;
import com.jian.family.business.medium.repository.MediumRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MediumService {

    private final MediumMapper mediumMapper;
    private final MediumRepository mediumRepository;

    public MediumService(MediumMapper mediumMapper,
                         MediumRepository mediumRepository) {
        this.mediumMapper = mediumMapper;
        this.mediumRepository = mediumRepository;
    }

    @Transactional
    public MediumDto save(MediumDto request) {

        MediumEntity entity = mediumMapper.toEntity(request);
        MediumEntity saved = mediumRepository.save(entity);
        return mediumMapper.toDto(saved);
    }

    public Page<MediumDto> list(MediumListQuery request, Pageable pageable) {

        Page<MediumEntity> byNameLike = mediumRepository.findByNameLike(request.name(), pageable);

        return byNameLike.map(mediumMapper::toDto);
    }

    public Optional<MediumDto> findById(Long id) {
        Optional<MediumEntity> entity = mediumRepository.findById(id);

        return entity.map(mediumMapper::toDto);
    }

    public void remove(Long id) {
        mediumRepository.deleteById(id);
    }
}
