package com.jujeol.aws.service;

import static com.jujeol.drink.drink.domain.ImageSize.LARGE;
import static com.jujeol.drink.drink.domain.ImageSize.MEDIUM;
import static com.jujeol.drink.drink.domain.ImageSize.SMALL;

import com.jujeol.aws.exception.ImageFileIOException;
import com.jujeol.aws.infrastructure.StorageUploader;
import com.jujeol.drink.drink.application.dto.ImageFilePathDto;
import com.jujeol.drink.drink.domain.ImageSize;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Service
public class ImageService {

    private final ImageResizer imageResizer;
    private final List<ImageConverter> imageConverters;
    private final StorageUploader storageUploader;

    public ImageFilePathDto insert(MultipartFile image) {
        File file = toFile(image);
        convertImageFile(file);

        ImageFilePathDto imageFilePathDto = createImageFilePathDto(file);

        deleteOriginalFile(file);
        return imageFilePathDto;
    }

    private ImageFilePathDto createImageFilePathDto(File file) {
        return new ImageFilePathDto(
            uploadResizedImage(file, SMALL),
            uploadResizedImage(file, MEDIUM),
            uploadResizedImage(file, LARGE)
        );
    }

    private String uploadResizedImage(File file, ImageSize imageSize) {
        File imageFilePath = imageResizer.resize(file, imageSize);
        return storageUploader.upload(
            imageSize.getFilePath(),
            imageFilePath
        );
    }

    private void deleteOriginalFile(File file) {
        try {
            Files.delete(Paths.get(file.getPath()));
        } catch (IOException e) {
            throw new ImageFileIOException();
        }
    }

    private void convertImageFile(File file) {
        for (ImageConverter imageConverter : imageConverters) {
            if (imageConverter.isSupport(file)) {
                file = imageConverter.convertImage(file);
            }
        }
    }

    private File toFile(MultipartFile image) {
        File convertedFile = new File(Objects.requireNonNull(image.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(image.getBytes());
        } catch (IOException e) {
            throw new ImageFileIOException();
        }
        return convertedFile;
    }

}
