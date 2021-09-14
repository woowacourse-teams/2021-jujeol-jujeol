package com.jujeol.aws.infrastructure;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.jujeol.aws.service.ImageResizer;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Transactional
@RequiredArgsConstructor
public class S3Uploader {
    public static final String FILENAME_EXTENSION_DOT = ".";

    @Value("${application.bucket.name}")
    private String bucketName;

    @Value("${application.cloudfront.url}")
    private String cloudfrontUrl;

    private final AmazonS3 amazonS3Client;
    private final ImageResizer imageResizer;

    public String upload(MultipartFile multipartFile) {
        if (isEmpty(multipartFile)) {
            return cloudfrontUrl ;
        }
        File file = convertToFile(multipartFile);
        String fileName = getFileName(file);
        File resizedFile = imageResizer.resize(file, fileName);
        amazonS3Client.putObject(new PutObjectRequest(bucketName, fileName, resizedFile));
        try {
            Files.delete(Paths.get(file.getPath()));
            Files.delete(Paths.get(resizedFile.getPath()));
        } catch (Exception e) {
            return cloudfrontUrl + fileName;
        }
        return cloudfrontUrl + fileName;
    }

    public boolean isEmpty(MultipartFile multipartFile) {
        return Objects.isNull(multipartFile) || multipartFile.isEmpty();
    }

    private File convertToFile(MultipartFile multipartFile) {
        File convertedFile = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(multipartFile.getBytes());
        } catch (IOException e) {
            //todo : 사용자 예외 정의
            throw new RuntimeException();
        }
        return convertedFile;
    }

    private String getFileName(File file) {
        String fileOriginName = file.getName();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String extension = FilenameUtils.getExtension(fileOriginName);
        return uuid + FILENAME_EXTENSION_DOT + extension;
    }

    public String update(String oldImageUrl, MultipartFile updateImage) {
        String imageName = oldImageUrl.replace(cloudfrontUrl, "");
        if (amazonS3Client.doesObjectExist(bucketName, imageName)) {
            amazonS3Client.deleteObject(new DeleteObjectRequest(bucketName, imageName));
        }
        return upload(updateImage);
    }
}
