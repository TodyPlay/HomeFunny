package com.jian.family.business.medium.converter;

import com.jian.family.business.medium.dto.MediumDetailDto;
import com.jian.family.business.medium.entity.MediumDetailEntity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface MediumDetailMapper {
    MediumDetailEntity toEntity(MediumDetailDto mediumDetailDto);

    MediumDetailDto toDto(MediumDetailEntity mediumDetailEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    MediumDetailEntity partialUpdate(MediumDetailDto mediumDetailDto, @MappingTarget MediumDetailEntity mediumDetailEntity);
}