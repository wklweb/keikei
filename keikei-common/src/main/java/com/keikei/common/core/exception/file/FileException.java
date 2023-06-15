package com.keikei.common.core.exception.file;

import com.keikei.common.core.exception.user.BaseException;

public class FileException extends BaseException {

    public FileException(Object[] params,String code) {
        super(params, "file", null, code);
    }
}
