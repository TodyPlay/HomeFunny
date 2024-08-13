package com.jian.family.business.attachment.repository;

import com.jian.family.business.attachment.entity.AttachmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AttachmentRepository extends JpaRepository<AttachmentEntity, Long> {

    List<AttachmentEntity> findByUpdateTimeLessThanEqual(LocalDateTime updateTime);


}
