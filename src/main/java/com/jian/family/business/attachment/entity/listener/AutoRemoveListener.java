package com.jian.family.business.attachment.entity.listener;

import com.jian.family.business.attachment.entity.AttachmentEntity;
import io.minio.MinioAsyncClient;
import io.minio.RemoveObjectArgs;
import jakarta.persistence.PostRemove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AutoRemoveListener {

    @Autowired
    private MinioAsyncClient minioAsyncClient;

    @PostRemove
    public void remove(AttachmentEntity entity) throws Exception {
        minioAsyncClient.removeObject(RemoveObjectArgs.builder().bucket(entity.getBucket()).object(entity.getObject()).build());
    }
}
