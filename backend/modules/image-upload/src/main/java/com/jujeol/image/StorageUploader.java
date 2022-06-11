package com.jujeol.image;

import java.io.File;

public interface StorageUploader {

    String upload(String directory, File image);

    void delete(String imageUrl);
}
