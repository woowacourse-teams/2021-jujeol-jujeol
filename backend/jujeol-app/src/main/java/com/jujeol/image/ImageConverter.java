package com.jujeol.image;

import java.io.File;

public interface ImageConverter {

    boolean isSupport(File file);

    File convertImage(File file);
}
