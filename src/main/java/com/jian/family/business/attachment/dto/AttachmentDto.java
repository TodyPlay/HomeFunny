package com.jian.family.business.attachment.dto;

import com.jian.family.business.attachment.entity.AttachmentEntity;

import java.io.Serializable;

/**
 * DTO for {@link AttachmentEntity}
 */
public record AttachmentDto(Long id, String name, String bucket, String object) implements Serializable {
}