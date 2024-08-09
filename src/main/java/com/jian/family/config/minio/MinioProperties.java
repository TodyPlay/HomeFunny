package com.jian.family.config.minio;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URL;

@Getter
@Setter
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {

    private URL entrypoint;

    private String accessKey;

    private String secretKey;
}
