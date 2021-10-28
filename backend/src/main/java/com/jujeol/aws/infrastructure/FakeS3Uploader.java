package com.jujeol.aws.infrastructure;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Profile({"local", "dummy"})
@Slf4j
@Service
public class FakeS3Uploader implements StorageUploader {

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
