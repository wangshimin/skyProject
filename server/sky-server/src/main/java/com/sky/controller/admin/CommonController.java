package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 通用接口
 */
@Slf4j
@RestController
@RequestMapping("/admin/common")
@Api(tags = "通用接口")
public class CommonController {

    @Autowired
    private AliOssUtil aliOssUtil;

    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping("/upload")
    @ApiOperation("文件上传")
    public Result<String> upload(MultipartFile file){
        log.info("文件上传：{}",file);
        String filePath = "https://bpic.588ku.com/back_origin_min_pic/21/03/30/08b5c525d7ead8dfae119bbdffa9bc8c.jpg";
        return Result.success(filePath);
//        try {
//            // 原始文件名
//            String originalFilename = file.getOriginalFilename();
//            // 截取原始文件名的后缀
//            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
//            // 生成随机文件名
//            String objectName = UUID.randomUUID().toString() + extension;
//            // 调用阿里云OSS文件上传方法, 返回文件的请求路径
//            String filePath = aliOssUtil.upload(file.getBytes(), objectName);
//            return Result.success(filePath);
//        } catch (IOException e) {
//            log.info("文件上传失败，{}", e.getMessage());
//        }
//        return Result.error(MessageConstant.UPLOAD_FAILED);
    }
}
