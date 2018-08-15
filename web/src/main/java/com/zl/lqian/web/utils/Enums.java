package com.zl.lqian.web.utils;

public interface Enums {

    enum uploadEnums{

        UPLOAD_SUCCESS("SUCCESS", "SUCCESS"), //默认成功
        UPLOAD_NOFILE("NOFILE", "未包含文件上传域"),
        UPLOAD_TYPE("TYPE", "不允许的文件格式"),
        UPLOAD_SIZE("SIZE", "文件大小超出限制，最大支持2Mb"),
        UPLOAD_ENTYPE("ENTYPE", "请求类型ENTYPE错误"),
        UPLOAD_REQUEST("REQUEST", "上传请求异常"),
        UPLOAD_IO("IO", "IO异常"),
        UPLOAD_DIR("DIR", "目录创建失败"),
        UPLOAD_UNKNOWN("UNKNOWN", "未知错误"),

        ;


        private String desc;

        private String msg;

        uploadEnums(String desc, String msg) {
            this.desc = desc;
            this.msg = msg;
        }
        public String getCode() {
            return desc;
        }
        public String getMsg() {
            return msg;
        }
    }
}
