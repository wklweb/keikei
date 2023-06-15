package com.keikei.common.core.exception.file;

public class FileNameTooLongException extends FileException{
    public FileNameTooLongException() {
        super(null, "file.error.nameLength");
    }
}
