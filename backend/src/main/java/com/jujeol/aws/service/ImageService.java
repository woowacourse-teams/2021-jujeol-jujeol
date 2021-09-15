package com.jujeol.aws.service;

import com.jujeol.aws.service.ImageResizer.ImageSize;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Objects;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Service
public class ImageService {

    private final ImageResizer imageResizer;

    public EnumMap<ImageSize, File> insert(MultipartFile image) {
        final File file = convertToFile(image);
        return imageResizer.resize(file);
    }

    private File convertToFile(MultipartFile image) {
        File convertedFile = new File(Objects.requireNonNull(image.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(image.getBytes());
        } catch (IOException e) {
            throw new RuntimeException();
        }
        return convertedFile;
    }
    // 1. 멀티파트 업로드 요청
    // 2. 파일 받아서 이미지 리사이징
    // 3. s3 업로드
    // 4. url 을 db 저장 (drink -> imageUrl 수정)

    // test 짜기
    // 리사이징 로직 작성 , 파일을 3개로 늘려야 함
    // s3에 업로드하고, db에 url로 저장
    // 이미지 url 을 클라이언트에 응답


}
