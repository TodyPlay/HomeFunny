package com.jian.family.business.medium.dto;

import com.jian.family.business.attachment.dto.AttachmentDto;

import java.io.Serializable;

/**
 * DTO for {@link com.jian.family.business.medium.entity.MediumDetailEntity}
 */
public record MediumDetailDto(Long id, String title,
                              AttachmentDto attachment) implements Serializable {
}