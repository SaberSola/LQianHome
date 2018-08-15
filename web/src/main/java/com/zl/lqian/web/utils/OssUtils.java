package com.zl.lqian.web.utils;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.BucketInfo;
import com.aliyun.oss.model.PutObjectRequest;
import com.zl.lqian.base.data.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * oss tool
 */
@Component
public class OssUtils {

    static Logger logger = LoggerFactory.getLogger(OssUtils.class);

    private static String endpoint = "https://oss-cn-shanghai.aliyuncs.com";

    private static String accessKeyId="LTAI39c8oZPqVZWt";

    private static String accessKeySecret="b7ZB4op2ti93RGdOttS03dqvSDKzmP";

    private static String bucketName="wangq";

    private static String aliyunOssUrl="https://wangq.oss-cn-shanghai.aliyuncs.com/";

    private static final   SimpleDateFormat SDF_YMD = new SimpleDateFormat("yyyyMMdd");
    private static OSSClient init() {
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        BucketInfo f = ossClient.getBucketInfo(bucketName);
        return ossClient;
    }

    public static String uploadImag(String fileName, MultipartFile file) {
        OSSClient ossClient = null;
        InputStream ins = null;
        try {
            ossClient = init();
            Date date = new Date();
            ins = file.getInputStream();
            String prefix = dateForMate(date) + "/";
            String keyUrl = prefix+ date.getTime();
            String suffix ="."+ fileName.substring(fileName.lastIndexOf(".") + 1);

            logger.info("start upload!");
            ossClient.putObject(new PutObjectRequest(bucketName, keyUrl + suffix, ins));
            logger.info("End upload!");
            String picUrl = aliyunOssUrl + keyUrl + suffix;

            return picUrl;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("OSS服务异常");
        } finally {
            if (null != ossClient)
                ossClient.shutdown();
            try {
                if (null != ins){
                    ins.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }

        }
    }

    private static String dateForMate(Date date){

        return SDF_YMD.format(date);
    }

    public static void  main(String[] args) throws Exception{
        Date date = new Date();
        SimpleDateFormat SDF_YMD = new SimpleDateFormat("yyyyMMdd");
        System.out.println(SDF_YMD.format(date));
    }
}
