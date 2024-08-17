package com.jian.family.business.medium.converter;

import com.jian.family.business.medium.dto.MediumDto;
import com.jian.family.business.medium.entity.MediumEntity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {MediumDetailMapper.class})
public interface MediumMapper {
    MediumEntity toEntity(MediumDto mediumDto);

    @AfterMapping
    default void linkMediumDetailEntities(@MappingTarget MediumEntity mediumEntity) {
        mediumEntity.getMediumDetails().forEach(mediumDetailEntity -> mediumDetailEntity.setMedium(mediumEntity));
    }

    MediumDto toDto(MediumEntity mediumEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    MediumEntity partialUpdate(MediumDto mediumDto, @MappingTarget MediumEntity mediumEntity);
}