package com.jujeol.image.config;

import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.jujeol.image.LoggingStorageUploader;
import com.jujeol.image.StorageUploader;
import com.jujeol.image.s3.S3Uploader;
import org.apache.tika.Tika;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;

@Configuration
public class ImageConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public StorageUploader storageUploader(Environment environment, AmazonS3 amazonS3Client) {
        return environment.acceptsProfiles(Profiles.of("prod", "dev")) ? new S3Uploader(amazonS3Client)
            : new LoggingStorageUploader();
    }

    @Bean
    public AmazonS3 amazonS3() {
        return AmazonS3ClientBuilder
            .standard()
            .withCredentials(InstanceProfileCredentialsProvider.getInstance())
            .withRegion(Regions.AP_NORTHEAST_2)
            .build();
    }

    @Bean
    public Tika mimeTypeParser() {
        return new Tika();
    }
}
