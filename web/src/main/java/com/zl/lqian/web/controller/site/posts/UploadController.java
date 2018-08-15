/*
+--------------------------------------------------------------------------
|   WeCMS [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.zl.lqian.web.controller.site.posts;

import com.zl.lqian.base.utils.FileKit;
import com.zl.lqian.web.controller.BaseController;
import com.zl.lqian.web.utils.Enums;
import com.zl.lqian.web.utils.OssUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;

/**
 * Ueditor 文件上传
 *
 * @author zl
 */
@Controller
@RequestMapping("/post")
public class UploadController extends BaseController {

    @Value("${site.store.size:2}")
    private String storeSize;

    @PostMapping("/upload")
    @ResponseBody
    public UploadResult upload(@RequestParam(value = "file", required = false) MultipartFile file,
                               HttpServletRequest request) throws IOException {
        UploadResult result = new UploadResult();
        int crop = ServletRequestUtils.getIntParameter(request, "crop", 0);
        int size = ServletRequestUtils.getIntParameter(request, "size", 800);

        // 检查空
        if (null == file || file.isEmpty()) {
            return result.error(Enums.uploadEnums.UPLOAD_NOFILE.getMsg());
        }
        String fileName = file.getOriginalFilename();

        // 检查类型
        if (!FileKit.checkFileType(fileName)) {
            return result.error(Enums.uploadEnums.UPLOAD_TYPE.getMsg());
        }
        // 保存图片
        try {
            String path;
            /*if (crop == 1) {
                int width = ServletRequestUtils.getIntParameter(request, "width", 364);
                int height = ServletRequestUtils.getIntParameter(request, "height", 200);
                path = fileRepo.storeScale(file, appContext.getThumbsDir(), width, height);
            } else {
                path = fileRepo.storeScale(file, appContext.getThumbsDir(), size);
            }*/

            path  = OssUtils.uploadImag(fileName,file);
            result.ok(Enums.uploadEnums.UPLOAD_SUCCESS.getMsg());
            result.setName(fileName);
            result.setType(getSuffix(fileName));
            result.setPath(path);
            result.setSize(file.getSize());

        } catch (Exception e) {
            result.error(Enums.uploadEnums.UPLOAD_UNKNOWN.getMsg());
            e.printStackTrace();
        }

        return result;
    }

    static class UploadResult {
        public static int OK = 200;
        public static int ERROR = 400;

        /**
         * 上传状态
         */
        private int status;

        /**
         * 提示文字
         */
        private String message;

        /**
         * 文件名
         */
        private String name;

        /**
         * 文件大小
         */
        private long size;

        /**
         * 文件类型
         */
        private String type;

        /**
         * 文件存放路径
         */
        private String path;

        public UploadResult ok(String message) {
            this.status = OK;
            this.message = message;
            return this;
        }

        public UploadResult error(String message) {
            this.status = ERROR;
            this.message = message;
            return this;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getSize() {
            return size;
        }

        public void setSize(long size) {
            this.size = size;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

    }
}
