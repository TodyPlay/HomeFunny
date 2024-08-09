package com.jian.family.business.attachment.service;

import com.jian.family.business.attachment.entity.AttachmentEntity;
import com.jian.family.business.attachment.repository.AttachmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    public Optional<AttachmentEntity> findById(Long id) {
        return repository.findById(id);
    }
}
