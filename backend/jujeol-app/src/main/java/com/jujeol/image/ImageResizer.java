package com.jujeol.image;

import com.jujeol.commons.exception.JujeolServerException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Slf4j
@Service
public class ImageResizer {

    private static final int ZERO_COORDINATE = 0;

    public File resize(File file, ImageSize imageSize) {
        try {
            BufferedImage image  = ImageIO.read(file);
            BufferedImage bufferedImage = resizeImage(
                image,
                imageSize
            );

            return bufferedImageToFile(
                imageSize,
                file.getName(),
                bufferedImage
            );
        } catch (IOException e) {
            throw new JujeolServerException();
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
            throw new JujeolServerException();
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
