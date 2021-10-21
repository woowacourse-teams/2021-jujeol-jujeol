package com.jujeol.aws.service;

import com.jujeol.aws.exception.ImageFileIOException;
import com.jujeol.aws.exception.ImageFileResizingException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProgressiveImageConverter implements ImageConverter {

    private final Tika tika;

    @Override
    public boolean isSupport(File file) {
        try {
            String fileType = tika.detect(file);
            return fileType.equals("image/jpeg");
        } catch (IOException e) {
            throw new ImageFileIOException();
        }
    }

    @Override
    public File convertImage(File file) {
        try (ImageOutputStream iops = new FileImageOutputStream(file)) {
            ImageWriter imageWriter = ImageIO.getImageWritersByMIMEType(tika.detect(file)).next();
            imageWriter.setOutput(iops);

            final BufferedImage image = ImageIO.read(file);
            IIOImage iioImage = new IIOImage(image, null, null);

            imageWriter.write(null, iioImage, createImageWriterWithProgressiveMode());
            imageWriter.dispose();
            return file;
        } catch (IOException e) {
            throw new ImageFileResizingException();
        }
    }

    private ImageWriteParam createImageWriterWithProgressiveMode() {
        JPEGImageWriteParam imageWriteParam = new JPEGImageWriteParam(Locale.KOREA);
        imageWriteParam.setProgressiveMode(ImageWriteParam.MODE_DEFAULT);
        return imageWriteParam;
    }
}
