package com.jian.family.business.attachment.mapper;

import com.jian.family.business.attachment.dto.AttachmentDto;
import com.jian.family.business.attachment.entity.AttachmentEntity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AttachmentEntityMapper {
    AttachmentEntity toEntity(AttachmentDto attachmentDto);

    AttachmentDto toDto(AttachmentEntity attachmentEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    AttachmentEntity partialUpdate(AttachmentDto attachmentDto, @MappingTarget AttachmentEntity attachmentEntity);
}