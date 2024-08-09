package com.jian.family.business.attachment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AttachmentResponse {

    private String id;

    private Boolean success;

    private String name;

    private String exceptionMessage;


}
