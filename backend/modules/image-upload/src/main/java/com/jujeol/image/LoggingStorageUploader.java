package com.jujeol.image;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
public class LoggingStorageUploader implements StorageUploader {

    @Override
    public String upload(String directory, File image) {
        log.debug("등록 요청 받은 이미지 이름 : {}", image.getName());
        String fileName = String.format("%s%s", directory, image.getName());
        try {
            Files.delete(Paths.get(image.getPath()));
        } catch (Exception e) {
            return fileName;
        }
        return fileName;
    }

    @Override
    public void delete(String imageUrl) {
        log.debug("삭제 요청 받은 이미지 이름 : {}", imageUrl);
    }
}
