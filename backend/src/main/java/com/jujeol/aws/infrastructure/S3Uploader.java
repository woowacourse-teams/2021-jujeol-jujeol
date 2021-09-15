package com.jujeol.aws.infrastructure;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Component
public class S3Uploader {

    @Value("${application.bucket.name}")
    private String bucketName;

    @Value("${application.cloudfront.url}")
    private String cloudfrontUrl;

    private final AmazonS3 amazonS3Client;

    public String upload(String directory, File image) {
        String fileName = String.format("%s%s", directory, image.getName());
        amazonS3Client.putObject(new PutObjectRequest(bucketName, fileName, image));
        try {
            Files.delete(Paths.get(image.getPath()));
        } catch (Exception e) {
            return cloudfrontUrl + fileName;
        }
        return cloudfrontUrl + fileName;
    }

    public String update(String oldImageUrl, File updateImage) {
        String imageName = oldImageUrl.replace(cloudfrontUrl, "");
        if (amazonS3Client.doesObjectExist(bucketName, imageName)) {
            amazonS3Client.deleteObject(new DeleteObjectRequest(bucketName, imageName));
        }
        return upload("", updateImage);
    }
}
