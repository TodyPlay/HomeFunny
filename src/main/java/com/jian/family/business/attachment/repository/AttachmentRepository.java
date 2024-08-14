package com.jian.family.business.attachment.repository;

import com.jian.family.business.attachment.entity.AttachmentEntity;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AttachmentRepository extends JpaRepository<AttachmentEntity, Long> {

    List<AttachmentEntity> findByUpdateTimeLessThanEqual(LocalDateTime updateTime);

    @Query("select at from AttachmentEntity at where at.name like %?1%")
    Page<AttachmentEntity> findByNameLike(@Nullable String name, Pageable pageable);

}
