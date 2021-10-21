package com.jujeol.aws.fixture;

import com.jujeol.aws.infrastructure.StorageUploader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("test")
@Service
public class TestS3Uploader implements StorageUploader {

    @Override
    public String upload(String directory, File image) {
        try {
            Files.delete(Paths.get(image.getPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return directory + image.getName();
    }

    @Override
    public void delete(String imageUrl) {
        try {
            Files.delete(Paths.get(imageUrl));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
