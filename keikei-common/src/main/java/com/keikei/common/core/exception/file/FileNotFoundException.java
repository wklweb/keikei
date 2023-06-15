package com.keikei.common.core.exception.file;

public class FileNotFoundException extends FileException{

    public FileNotFoundException() {
        super(null, "file.error.notFound");
    }
}
