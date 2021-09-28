package com.jujeol.aws.infrastructure;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.jujeol.aws.exception.AmazonClientException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Profile({"dev", "prod"})
@Slf4j
@Service
public class S3Uploader implements StorageUploader {

    @Value("${application.bucket.name:empty-bucket}")
    private String bucketName;

    @Value("${application.cloudfront.url:empty-bucket}")
    private String cloudfrontUrl;

    private final AmazonS3 amazonS3Client;

    @Override
    public String upload(String directory, File image) {
        String fileName = String.format("%s%s", directory, image.getName());
        try {
            amazonS3Client.putObject(new PutObjectRequest(bucketName, fileName, image));
        } catch (RuntimeException e) {
            throw new AmazonClientException();
        }

        try {
            Files.delete(Paths.get(image.getPath()));
        } catch (Exception e) {
            return cloudfrontUrl + fileName;
        }
        return cloudfrontUrl + fileName;
    }

    @Override
    public void delete(String imageUrl) {
        String imageName = imageUrl.replace(cloudfrontUrl, "");
        try {
            if (amazonS3Client.doesObjectExist(bucketName, imageName)) {
                amazonS3Client.deleteObject(new DeleteObjectRequest(bucketName, imageName));
            } else {
                log.warn("해당 이미지의 파일이 없습니다. url : {}", imageName);
            }
        } catch (RuntimeException e) {
            throw new AmazonClientException();
        }
    }
}
