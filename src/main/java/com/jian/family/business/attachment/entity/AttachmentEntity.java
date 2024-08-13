package com.jian.family.business.attachment.entity;

import com.jian.family.business.attachment.entity.listener.AutoRemoveListener;
import com.jian.family.config.audit.AbstractAudit;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Getter
@Setter
@Entity
@Table(name = "attachment_entity", uniqueConstraints = {
        @UniqueConstraint(name = "uc_attachment_bucket_object", columnNames = {"bucket", "object"})
})
@EntityListeners({AutoRemoveListener.class})
public class AttachmentEntity extends AbstractAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

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
