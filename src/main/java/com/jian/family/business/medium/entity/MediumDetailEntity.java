package com.jian.family.business.medium.entity;

import com.jian.family.business.attachment.entity.AttachmentEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "medium_detail_entity")
public class MediumDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", length = 64)
    private String title;

    @OneToOne(orphanRemoval = true, cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "attachment_id")
    private AttachmentEntity attachment;

    @ManyToOne
    @JoinColumn(name = "medium_id")
    private MediumEntity medium;

}