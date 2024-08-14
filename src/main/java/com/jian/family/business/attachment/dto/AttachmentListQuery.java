package com.jian.family.business.attachment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AttachmentListQuery {

    @NotNull
    private String name;
}
