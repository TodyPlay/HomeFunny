package com.jian.family.config.minio;

import lombok.Getter;

@Getter
public enum Bucket {

    /**
     * 聊天室临时文件包
     */
    CHART_ROOM("chat-room");

    private final String bucket;

    Bucket(String bucket) {
        this.bucket = bucket;
    }
}
