package com.jian.family.business.attachment.repository;

import com.jian.family.business.attachment.entity.AttachmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentRepository extends JpaRepository<AttachmentEntity, Long> {


}
