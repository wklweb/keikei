package com.keikei.web.base;

import com.keikei.common.domain.AjaxResult;
import com.keikei.common.domain.model.KeikeiConfig;
import com.keikei.common.utils.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@RestController
public class SysFilterController extends BaseController{
    private static String uploadPath = "/upload";
    /**
     * 文件上传
     */
    @PostMapping("/file/upload")
    public AjaxResult ajaxResult(@RequestParam("file") MultipartFile file){
        try {
            String profileUrl = FileUtils.checkAllowFile(file,null,uploadPath);
            AjaxResult ajaxResult = AjaxResult.success("上传成功");
            ajaxResult.put("url",profileUrl);
            ajaxResult.put("fileName",file.getOriginalFilename());
            return ajaxResult;
        }
        catch (Exception e){
            return AjaxResult.error(e.getMessage());
        }
    }
    @PostMapping("/file/download/{fileName}")
    public AjaxResult download(@PathVariable String fileName, HttpServletResponse response){
        try{
            FileUtils.checkAllowDown(fileName);
        }
        catch (Exception e){
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success();
    }


}
