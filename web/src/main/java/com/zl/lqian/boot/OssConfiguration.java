package com.zl.lqian.boot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
public class OssConfiguration {

    public static String endpoint;

    public static String accessKeyId;

    public static String accessKeySecret;

    public static  String bucketName;

    public static  String url;

    @Value("${aliyun.oss.endpoint}")
    public  void setEndpoint(String endpoint) {
        OssConfiguration.endpoint = endpoint;
    }

    @Value("${aliyun.oss.accessKeyId}")
    public  void setAccessKeyId(String accessKeyId) {
        OssConfiguration.accessKeyId = accessKeyId;
    }

    @Value("${aliyun.oss.accessKeySecret}")
    public  void setAccessKeySecret(String accessKeySecret) {
        OssConfiguration.accessKeySecret = accessKeySecret;
    }

    @Value("${aliyun.oss.bucketName}")
    public  void setBucketName(String bucketName) {
        OssConfiguration.bucketName = bucketName;
    }

    @Value("${aliyun.oss.url}")
    public  void setUrl(String url) {
        OssConfiguration.url = url;
    }
}
