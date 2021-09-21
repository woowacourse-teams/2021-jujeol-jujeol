package com.jujeol.aws.service;

import com.jujeol.aws.exception.ImageFileResizingException;
import com.jujeol.drink.drink.domain.ImageSize;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ImageResizer {

    private static final int ZERO_COORDINATE = 0;

    public File resize(File file, ImageSize imageSize) {
        try {
            BufferedImage image = ImageIO.read(file);
            BufferedImage bufferedImage = resizeImage(
                image,
                imageSize
            );

            return bufferedImageToFile(
                imageSize,
                file.getName(),
                bufferedImage
            );
        } catch (Exception e) {
            throw new ImageFileResizingException();
        }
    }

    private File bufferedImageToFile(ImageSize imageSize,
        String fileName,
        BufferedImage bufferedImage
    ) {
        try {
            File file = new File(
                String.format("%s_%s.%s",
                    FilenameUtils.removeExtension(fileName),
                    imageSize.getFileNameSuffix(),
                    FilenameUtils.getExtension(fileName)
                )
            );
            ImageIO.write(bufferedImage, "jpg", file);
            log.info(file.toString());
            return file;
        } catch (IOException e) {
            throw new ImageFileResizingException();
        }
    }

    private BufferedImage resizeImage(
        BufferedImage originalImage, ImageSize imageSize
    ) {
        final Image originalImageScaled = resizing(originalImage, imageSize);

        BufferedImage bufferedImage = createBufferedImageWithSize(imageSize);

        drawImage(originalImageScaled, bufferedImage);

        return bufferedImage;
    }

    private Image resizing(BufferedImage originalImage, ImageSize imageSize) {
        return originalImage
            .getScaledInstance(imageSize.getPixelSize(),
                imageSize.getPixelSize(), Image.SCALE_SMOOTH);
    }

    private BufferedImage createBufferedImageWithSize(ImageSize imageSize) {
        return new BufferedImage(
            imageSize.getPixelSize(),
            imageSize.getPixelSize(),
            BufferedImage.TYPE_INT_RGB
        );
    }

    private void drawImage(Image originalImageScaled, BufferedImage bufferedImage) {
        bufferedImage.getGraphics().drawImage(
            originalImageScaled,
            ZERO_COORDINATE, ZERO_COORDINATE,
            Color.WHITE,
            null
        );
    }

}
