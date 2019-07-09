package com.atguigu.gmall.manage.util;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;

public class MyUploadUtil {
    public static String uploadImg(MultipartFile file) {
        //得到tracker文件的绝对路径
        String fileUrl=MyUploadUtil.class.getResource("/tracker.conf").getPath();
        //保存到数据库的前缀
        String imgUrl="http://192.168.6.100";
        try {
            //初始化ClientGlobal
            ClientGlobal.init(fileUrl);
            //得到一个trackerClient对象
            TrackerClient trackerClient=new TrackerClient();
            //根据trackerClient.getConnection得到TrackerServer
            TrackerServer trackerServer = trackerClient.getConnection();
            //new一个仓库StorageClient,把trackerServer信息作为参数传入
            StorageClient storageClient=new StorageClient(trackerServer,null);
            //得到原始的文件名称
            String originalFilename = file.getOriginalFilename();
            int i = originalFilename.lastIndexOf(".");
            //获得后缀名
            String substring = originalFilename.substring(i + 1);
            //调用upload_file方法上传文件到服务器,file.getBytes()传入的是二进制文件
            String[] pathS=storageClient.upload_file(file.getBytes(), substring, null);
            for (String path : pathS) {
                //拼接上传到服务器的路径
                imgUrl+="/"+path;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //返回上传路径,可以通过浏览器直接访问
        return imgUrl;
    }


}
