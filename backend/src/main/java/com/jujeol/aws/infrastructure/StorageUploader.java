package com.jujeol.aws.infrastructure;

import java.io.File;

public interface StorageUploader {

    String upload(String directory, File image);

    String update(String oldImageUrl, File updateImage);
}
