package com.jujeol.aws.service;

import com.jujeol.aws.exception.ImageFileResizingException;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Slf4j
@Profile("!test")
@Service
public class ImageResizerImpl implements ImageResizer {

    @Override
    public EnumMap<ImageSize, File> resize(File file) {
        try {
            BufferedImage image = ImageIO.read(file);
            EnumMap<ImageSize, BufferedImage> resizedImages = resizeImageByTripleTypes(image);

            EnumMap<ImageSize, File> resizedFiles = new EnumMap<>(ImageSize.class);
            for (ImageSize imageSize : ImageSize.list()) {
                File resizedFile = bufferedImageToFile(imageSize, file.getName(),
                    resizedImages.get(imageSize));
                resizedFiles.put(imageSize, convertProgressive(resizedFile));
            }
            Files.delete(Paths.get(file.getPath()));
            return resizedFiles;
        } catch (Exception e) {
            throw new ImageFileResizingException();
        }
    }

    private File bufferedImageToFile(ImageSize imageSize, String fileName,
        BufferedImage bufferedImage) {
        File file = null;
        try {
            file = new File(
                String.format("%s_%s.%s",
                    FilenameUtils.removeExtension(fileName),
                    imageSize.getFileNameSuffix(),
                    "jpeg"
                )
            );
            ImageIO.write(bufferedImage, "jpg", file);
            log.info(file.toString());
        } catch (IOException e) {
            throw new ImageFileResizingException();
        }
        return file;
    }

    private EnumMap<ImageSize, BufferedImage> resizeImageByTripleTypes(
        BufferedImage originalImage) {
        final EnumMap<ImageSize, BufferedImage> resizedImage = ImageSize.list().stream()
            .collect(
                Collectors.toMap(
                    Function.identity(),
                    (imageSize) -> {
                        final Image originalImageScaled = originalImage
                            .getScaledInstance(imageSize.getPixelSize(),
                                imageSize.getPixelSize(), Image.SCALE_SMOOTH);
                        BufferedImage bufferedImage = new BufferedImage(
                            imageSize.getPixelSize(), imageSize.getPixelSize(),
                            BufferedImage.TYPE_INT_RGB);
                        bufferedImage.getGraphics()
                            .drawImage(originalImageScaled, 0, 0, Color.WHITE,
                                null);
                        return bufferedImage;
                    },
                    (o1, o2) -> o1,
                    () -> new EnumMap<>(ImageSize.class)
                )
            );
        return resizedImage;
    }

    private File convertProgressive(File file) {
        try (ImageOutputStream iops = new FileImageOutputStream(file)) {
            ImageWriter imageWriter = ImageIO.getImageWritersByFormatName("jpg").next();
            JPEGImageWriteParam imageWriteParam = new JPEGImageWriteParam(Locale.KOREA);

            imageWriteParam.setProgressiveMode(ImageWriteParam.MODE_DEFAULT);
            imageWriter.setOutput(iops);

            final BufferedImage image = ImageIO.read(file);
            IIOImage iioImage = new IIOImage(image, null, null);

            imageWriter.write(null, iioImage, imageWriteParam);
            imageWriter.dispose();
        } catch (IOException e) {
            throw new ImageFileResizingException();
        }

        return file;
    }

    public enum ImageSize {
        SMALL(200, "w200"),
        MEDIUM(400, "w400"),
        LARGE(600, "w600");

        ImageSize(int pixelSize, String fileNameSuffix) {
            this.pixelSize = pixelSize;
            this.fileNameSuffix = fileNameSuffix;
        }

        private final int pixelSize;
        private final String fileNameSuffix;

        public static List<ImageSize> list() {
            return Arrays.asList(ImageSize.values());
        }

        public int getPixelSize() {
            return pixelSize;
        }

        public String getFileNameSuffix() {
            return fileNameSuffix;
        }

    }
}
