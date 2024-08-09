package com.jian.family.business.attachment.entity;

import com.jian.family.business.user.entity.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.domain.AbstractAuditable;

@Getter
@Setter
@Entity
@Table(name = "AttachmentEntity", uniqueConstraints = {
        @UniqueConstraint(name = "uc_attachment_bucket_object", columnNames = {"bucket", "object"})
})
public class AttachmentEntity extends AbstractAuditable<UserEntity, Long> {

    @Comment("文件名")
    @Column(name = "name", nullable = false)
    private String name;

    @Comment("桶名")
    @Column(name = "bucket", nullable = false)
    private String bucket;

    @Comment("对象名")
    @Column(name = "object", nullable = false)
    private String object;

}
