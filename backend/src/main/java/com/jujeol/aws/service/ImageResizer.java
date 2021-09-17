package com.jujeol.aws.service;

import com.jujeol.aws.service.ImageResizerImpl.ImageSize;
import java.io.File;
import java.util.EnumMap;

public interface ImageResizer {

    EnumMap<ImageSize, File> resize(File file);
}
