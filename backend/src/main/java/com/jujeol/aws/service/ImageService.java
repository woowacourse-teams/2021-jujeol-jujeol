package com.jujeol.aws.service;

import com.jujeol.aws.infrastructure.S3Uploader;
import com.jujeol.aws.service.ImageResizerImpl.ImageSize;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Service
public class ImageService {

    private final ImageResizer imageResizer;
    private final S3Uploader s3Uploader;

    public EnumMap<ImageSize, String> insert(MultipartFile image) {
        final File file = convertToFile(image);
        EnumMap<ImageSize, File> images = imageResizer.resize(file);
        return images.keySet().stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        imageSize ->
                                s3Uploader.upload(
                                        String.format("%s/", imageSize.getFileNameSuffix()),
                                        images.get(imageSize)),
                        (o1, o2) -> o1,
                        () -> new EnumMap<>(ImageSize.class)
                ));
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

}
