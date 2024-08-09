package com.jian.family.business.chat.dto;

import java.time.LocalDateTime;

public record Message<T>(MessageType type,
                         Long sender,
                         LocalDateTime sendTime,
                         T content) {

}
