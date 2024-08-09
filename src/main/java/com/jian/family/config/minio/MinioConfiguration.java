package com.jian.family.config.minio;

import io.minio.MinioAsyncClient;
import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfiguration {

    @Bean
    public MinioClient minioClient(MinioProperties properties) {
        return MinioClient.builder()
                .endpoint(properties.getEntrypoint())
                .credentials(properties.getAccessKey(), properties.getSecretKey())
                .build();
    }


    @Bean
    public MinioAsyncClient minioAsyncClient(MinioProperties properties) {
        return MinioAsyncClient.builder()
                .endpoint(properties.getEntrypoint())
                .credentials(properties.getAccessKey(), properties.getSecretKey())
                .build();
    }
}
