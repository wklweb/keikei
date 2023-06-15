package com.keikei.common.utils;

import cn.hutool.core.io.FileUtil;
import com.keikei.common.constants.Constants;
import com.keikei.common.constants.MimeTypeConstants;
import com.keikei.common.core.exception.file.FileNameTooLongException;
import com.keikei.common.core.exception.file.FileNotFoundException;
import com.keikei.common.core.exception.file.FileSizeException;
import com.keikei.common.core.exception.file.FileTypeException;
import com.keikei.common.domain.AjaxResult;
import com.keikei.common.domain.model.KeikeiConfig;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class FileUtils {
    /**
     * 检查上传文件类型是否支持
     * @param file
     * @return
     */
    public static boolean checkAllowFileType(MultipartFile file,String[] strings) {
        Resource resource = file.getResource();
        String uploadType = StringUtils.substringAfterLast(resource.getFilename(),".");
        if(ArrayUtils.contains(strings==null?MimeTypeConstants.DEFAULT_ALLOW_TYPE:strings,uploadType)){
            return true;
        }
        return false;
    }

    /**
     * 上传文件
     * @param uploadPath
     * @param file
     */
    public static String uploadFile(String uploadPath, MultipartFile file) throws IOException {
        String encodingName = encodingFileName(file);
        //生成目录
        String filePath = getFileUploadPath(uploadPath,encodingName).getAbsolutePath();
        Path path = Paths.get(filePath);
        file.transferTo(path);
        return getProfileFileName(uploadPath,filePath);
    }

    /**
     * 获取抽象化的文件路径
     * @param uploadPath
     * @param filePath
     * @return
     */
    private static String getProfileFileName(String uploadPath, String filePath) {
        int index = uploadPath.length();
        String afterPath = filePath.substring(index);
        return "\\"+Constants.PROFILE +"\\" + afterPath;
    }
    private static File getFileUploadPath(String uploadPath, String encodingName) {
        String path = uploadPath+File.separator+encodingName;
        File file = new File(path);
        if(!file.exists()){
            if(!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
        }
        return file;

    }

    private static String encodingFileName(MultipartFile file) {
        return StringUtils.format("{}/{}.{}", DateUtils.dataPath(),StringUtils.substringBefore(
                file.getOriginalFilename(),".")
        ,StringUtils.substringAfterLast(file.getOriginalFilename(),"."));
    }
    public static String checkAllowFile(MultipartFile file,String[] strings,String target) throws IOException {
        if(Objects.isNull(file)){
            throw new FileNotFoundException();
        }
        else if(file.getSize()>KeikeiConfig.getFileMaxSize()*1024*1024){
            throw new FileSizeException();
        }
        else if(StringUtils.substringBefore(file.getOriginalFilename(),".").length()>KeikeiConfig.getFileNameLength()){
            throw new FileNameTooLongException();
        }
        else if(!FileUtils.checkAllowFileType(file,strings)){
            throw new FileTypeException();
        }
        String avatarPath = KeikeiConfig.getProfile() + target;
        String avatarUrl = uploadFile(avatarPath,file);

        return avatarUrl;
    }

    public static void checkAllowDown(String fileName) {

    }
}
