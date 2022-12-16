package com.example.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.StrUtil;
import com.example.common.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件上传
 */
@RestController
@RequestMapping("/files")
public class FileController {

    @Value("${serverPath}")
    public String serverPath;

    /**
     * 单文件上传
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file, HttpServletRequest req) {
        synchronized (FileController.class) {
            //首先校验图片格式
            List<String> imgTypes = new ArrayList<>();
            String rootPath = System.getProperty("user.dir") + serverPath;
            //"jpg", "jpeg", "png", "bmp", "gif"
            imgTypes.add("jpg");
            imgTypes.add("jpeg");
            imgTypes.add("png");
            imgTypes.add("bmp");
            imgTypes.add("gif");

            // 获取文件名，带后缀
            String originalFilename = file.getOriginalFilename();
            // 获取文件的后缀格式
            String fileSuffix = FileNameUtil.extName(originalFilename);
            String fileName = System.currentTimeMillis() + "." + fileSuffix;
            String filePath = rootPath + fileName;

            if (imgTypes.contains(fileSuffix)) {
                File destFile = new File(filePath);
                if (!destFile.getParentFile().exists()) {
                    destFile.getParentFile().mkdirs();
                }

                //如果文件存在，删除原有文件
                if (destFile.exists() && destFile.isFile()) {
                    destFile.delete();
                }
                String url = "";
                try {
                    file.transferTo(destFile);
                    //生成返回给前端的url
                    url = req.getScheme() + "://" + req.getServerName() + ":" +
                            req.getServerPort() + "/files/" + fileName;
                    System.out.println(filePath + ",url:" + url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return Result.success(url);
            }
            return Result.error("500","图片上传失败");
        }
    }


    /**
     * 获取文件
     *
     * @param flag
     * @param response
     */
    @GetMapping("/{flag}")
    public void avatarPath(@PathVariable String flag, HttpServletResponse response) {
        OutputStream os;
        String basePath = System.getProperty("user.dir") + "/src/main/resources/static/file/";
        List<String> fileNames = FileUtil.listFileNames(basePath);
        String avatar = fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");
        try {
            if (StrUtil.isNotEmpty(avatar)) {
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(avatar, "UTF-8"));
                response.setContentType("application/octet-stream");
                byte[] bytes = FileUtil.readBytes(basePath + avatar);
                os = response.getOutputStream();
                os.write(bytes);
                os.flush();
                os.close();
            }
        } catch (Exception e) {
            System.out.println("文件下载失败");
        }
    }

    /**
     * 删除文件
     *
     * @param flag
     */
    @DeleteMapping("/{flag}")
    public void delFile(@PathVariable String flag) {
        String basePath = System.getProperty("user.dir") + "/src/main/resources/static/file/";
        List<String> fileNames = FileUtil.listFileNames(basePath);
        String filename = fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");
        FileUtil.del(basePath + filename);
        System.out.println("删除文件" + filename + "成功");
    }

}
