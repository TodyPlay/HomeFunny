package com.jian.family.business.attachment.service;

import com.jian.family.business.attachment.dto.AttachmentEntityDto;
import com.jian.family.business.attachment.dto.AttachmentListQuery;
import com.jian.family.business.attachment.entity.AttachmentEntity;
import com.jian.family.business.attachment.repository.AttachmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AttachmentService {

    @Autowired
    private AttachmentRepository repository;


    public AttachmentEntity save(String name, String bucket, String object) {
        AttachmentEntity entity = new AttachmentEntity();
        entity.setName(name);
        entity.setBucket(bucket);
        entity.setObject(object);
        return repository.save(entity);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public Optional<AttachmentEntity> findById(Long id) {
        return repository.findById(id);
    }

    public void removeExpired() {
        List<AttachmentEntity> expired = repository.findByUpdateTimeLessThanEqual(LocalDateTime.now().plusDays(-7));
        repository.deleteAll(expired);
    }

    public List<AttachmentEntityDto> findAllByCondition(AttachmentListQuery request, Pageable pageable) {
        List<AttachmentEntity> byNameLike = repository.findByNameLike(request.getName(), pageable);

        return byNameLike.stream().map(ett -> new AttachmentEntityDto(ett.getId(), ett.getName(), ett.getBucket(), ett.getObject())).toList();
    }
}
