package com.jian.family.business.attachment.entity;

import com.jian.family.business.attachment.entity.listener.AutoRemoveListener;
import com.jian.family.business.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@Entity
@Table(name = "AttachmentEntity", uniqueConstraints = {
        @UniqueConstraint(name = "uc_attachment_bucket_object", columnNames = {"bucket", "object"})
})
@EntityListeners({AuditingEntityListener.class, AutoRemoveListener.class})
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
