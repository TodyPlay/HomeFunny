package com.jian.family.config.minio;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioAsyncClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class BucketCreator implements InitializingBean {

    @Autowired
    private MinioAsyncClient minioAsyncClient;

    @Override
    public void afterPropertiesSet() throws Exception {
        for (Bucket bucket : Bucket.values()) {
            minioAsyncClient.bucketExists(BucketExistsArgs.builder().bucket(bucket.getBucket()).build())
                    .thenCompose(exists -> {

                        if (exists) {
                            return CompletableFuture.completedFuture(null);
                        }

                        try {
                            return minioAsyncClient.makeBucket(MakeBucketArgs.builder().bucket(bucket.getBucket()).build());
                        } catch (Exception e) {
                            return CompletableFuture.failedFuture(e);
                        }
                    }).exceptionally(ex -> {
                        log.debug("创建bucket错误", ex);
                        return Void.TYPE.cast(null);
                    });

        }
    }
}
