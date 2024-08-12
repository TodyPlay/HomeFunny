package com.jian.family.business.attachment.repository;

import com.jian.family.business.attachment.entity.AttachmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttachmentRepository extends JpaRepository<AttachmentEntity, Long> {

    @Query("select att from AttachmentEntity att where att.lastModifiedDate <= ?1")
    List<AttachmentEntity> findExpired(LocalDate expired);

}
