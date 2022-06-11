package com.jujeol.image.service;

import com.jujeol.commons.exception.JujeolServerException;
import com.jujeol.drink.domain.model.ImageFilePath;
import com.jujeol.image.ImageConverter;
import com.jujeol.image.ImageResizer;
import com.jujeol.image.ImageSize;
import com.jujeol.image.StorageUploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

import static com.jujeol.image.ImageSize.*;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageResizer imageResizer;
    private final List<ImageConverter> imageConverters;
    private final StorageUploader storageUploader;

    public ImageFilePath save(MultipartFile image) {
        File file = toFile(image);
        convertImageFile(file);

        ImageFilePath imageFilePath = createImageFilePath(file);

        deleteOriginalFile(file);
        return imageFilePath;
    }

    public ImageFilePath update(ImageFilePath images, MultipartFile file) {
        storageUploader.delete(images.getSmallImageFilePath());
        storageUploader.delete(images.getMediumImageFilePath());
        storageUploader.delete(images.getLargeImageFilePath());
        return save(file);
    }

    private ImageFilePath createImageFilePath(File file) {
        return ImageFilePath.create(
            uploadResizedImage(file, SMALL),
            uploadResizedImage(file, MEDIUM),
            uploadResizedImage(file, LARGE)
        );
    }

    private String uploadResizedImage(File file, ImageSize imageSize) {
        File imageFile = imageResizer.resize(file, imageSize);
        return storageUploader.upload(
            imageSize.getFilePath(),
            imageFile
        );
    }

    private void deleteOriginalFile(File file) {
        try {
            Files.delete(Paths.get(file.getPath()));
        } catch (IOException e) {
            throw new JujeolServerException();
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
            throw new JujeolServerException();
        }
        return convertedFile;
    }
}
