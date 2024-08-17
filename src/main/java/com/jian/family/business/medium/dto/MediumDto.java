package com.jian.family.business.medium.dto;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link com.jian.family.business.medium.entity.MediumEntity}
 */
public record MediumDto(Long id, String name,
                        List<MediumDetailDto> mediumDetails) implements Serializable {
}