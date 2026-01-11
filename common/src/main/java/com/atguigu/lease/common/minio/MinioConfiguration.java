package com.atguigu.lease.common.minio;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//这个只用于单个的属性注入
//@EnableConfigurationProperties(MinioProperties.class)
//这个可以批量注入
@ConfigurationPropertiesScan("com.atguigu.lease.common.minio")
@ConditionalOnProperty(name = "minio.endpoint")
public class MinioConfiguration {
    @Autowired
    private MinioProperties properties;

//刚刚果然是因为这里没有加@Bean注解，所以没有生成bean对象，导致无法注入
@Bean
    public MinioClient minioClient(){
    return   MinioClient.builder()
                .endpoint(properties.getEndpoint())
                .credentials(properties.getAccessKey(),properties.getSecretKey())
                .build();
    }
}
