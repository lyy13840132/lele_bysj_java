package com.university.lele.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.university.lele.data.model.FileUserModel;
import com.university.lele.global.MyKEY;
import com.university.lele.global.Result;
import com.university.lele.score.model.RecordModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.util.*;


@Component
public class AliyunOssUtil {
    private Logger logger = LoggerFactory.getLogger(AliyunOssUtil.class);

    /**
     * 获取阿里云OSS客户端对象
     *
     * @return ossClient
     */
    public OSSClient getOSSClient() {
        return new OSSClient(MyKEY.OSS_ENDPOINT, MyKEY.OSS_ACCESS_KEY_ID, MyKEY.OSS_ACCESS_KEY_SECRE);
    }

    /**
     * 查询文件是否参在
     * @param filePathName：文件再阿里云中的完整路径
     * @return
     */
    public boolean isOSSExistFile(String filePathName){
        boolean found = getOSSClient().doesObjectExist(MyKEY.OSS_BACKET_NAME,filePathName);
   // 关闭OSSClient。
        getOSSClient().shutdown();
        return found;
    }

    /**

     */
    public void deleteFile(String fileName) {

// 删除文件或目录。如果要删除目录，目录必须为空。
        getOSSClient().deleteObject(MyKEY.OSS_BACKET_NAME, fileName);

// 关闭OSSClient。
        getOSSClient().shutdown();
    }

    /**
     * 上传图片至OSS
     *
     * @param ossClient  oss连接
     * @param file       上传文件（文件全路径如：D:\\image\\cake.jpg）
     * @param bucketName 存储空间
     * @param folder     模拟文件夹名 如"qj_nanjing/"
     * @return String 返回的唯一MD5数字签名
     */
    public String uploadObject2OSS(OSSClient ossClient, MultipartFile file, String bucketName, String folder) throws Exception {
        String resultStr = null;
        try {
            //以输入流的形式上传文件
            InputStream is = file.getInputStream();
            //文件名
            String fileName = file.getOriginalFilename();
            //文件大小
            //创建上传Object的Metadata
            ObjectMetadata metadata = new ObjectMetadata();
            //上传的文件的长度
            metadata.setContentLength(is.available());
            //指定该Object被下载时的网页的缓存行为
            metadata.setCacheControl("no-cache");
            //指定该Object下设置Header
            metadata.setHeader("Pragma", "no-cache");
            //指定该Object被下载时的内容编码格式
            metadata.setContentEncoding("utf-8");
             ossClient.putObject(bucketName, folder + fileName, is, metadata);
             return  "https://"+MyKEY.OSS_BACKET_NAME+"."+MyKEY.OSS_ENDPOINT+"/"+folder+file.getOriginalFilename();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("上传阿里云OSS服务器异常." + e.getMessage(), e);
        }
        return resultStr;
    }


    /**
     * 上传图片
     * @param inputStream
     * @param userID
     * @return
     * @throws Exception
     */
    public String upLoadImageToOSS(InputStream inputStream, String ossPath,String file_folder, String userID) throws Exception {
        //String resultStr = null;
        try {
             getOSSClient().putObject(MyKEY.OSS_BACKET_NAME, file_folder + userID + ".png", inputStream);
             return ossPath+userID+".png";
        } catch (OSSException oe) {
            return oe.getMessage();
        } catch (ClientException ce) {
            return ce.getMessage();
        } finally{
            if (getOSSClient() != null){
                getOSSClient().shutdown();
            }
        }
        
    }



    public String upLoadFileToOss(FileUserModel model) {

        String resultStr = null;
        try {
            //以输入流的形式上传文件
           // model.getFile().
            InputStream is = model.getFile().getInputStream();
            //文件名
            //String fileName = file.getOriginalFilename();
            //文件大小
            //创建上传Object的Metadata
            ObjectMetadata metadata = new ObjectMetadata();
            //上传的文件的长度
            metadata.setContentLength(is.available());
            //指定该Object被下载时的网页的缓存行为
            metadata.setCacheControl("no-cache");
            //指定该Object下设置Header
            metadata.setHeader("Pragma", "no-cache");
            //指定该Object被下载时的内容编码格式
            metadata.setContentEncoding("utf-8");


            getOSSClient().putObject(MyKEY.OSS_BACKET_NAME, MyKEY.
                    OSS_DATAFILE_FOLDER+model.getFile().getOriginalFilename(), is, metadata);
            resultStr= "https://"+MyKEY.OSS_BACKET_NAME+"."+MyKEY.OSS_ENDPOINT+"/"+MyKEY.
                    OSS_DATAFILE_FOLDER+model.getFile().getOriginalFilename();
            return resultStr;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("上传阿里云OSS服务器异常." + e.getMessage(), e);
            return resultStr;
        }
    }


    public InputStream downLoadFile(String fileName){
            try {
                OSSObject ossObject = getOSSClient().getObject(MyKEY.OSS_BACKET_NAME,
                        MyKEY.OSS_DATAFILE_FOLDER+fileName);
                InputStream objectContent = ossObject.getObjectContent();

                // 读取文件内容。
                BufferedReader reader = new BufferedReader(new InputStreamReader(ossObject.getObjectContent()));
                while (true) {
                    String line = reader.readLine();
                    if (line == null) break;
                    System.out.println("\n" + line);
                }
                // 数据读取完成后，获取的流必须关闭，否则会造成连接泄漏，导致请求无连接可用，程序无法正常工作。
                //reader.close();

                // ossObject对象使用完毕后必须关闭，否则会造成连接泄漏，导致请求无连接可用，程序无法正常工作。
                ossObject.close();
                return objectContent;
            } catch (OSSException oe) {
                System.out.println("Caught an OSSException, which means your request made it to OSS, "
                        + "but was rejected with an error response for some reason.");
            } catch (Throwable ce) {
                System.out.println("Caught an ClientException, which means the client encountered "
                        + "a serious internal problem while trying to communicate with OSS, "
                        + "such as not being able to access the network.");
            } finally {
                if (getOSSClient() != null) {
                    getOSSClient().shutdown();
                }
            }
            return null;
        }



}
