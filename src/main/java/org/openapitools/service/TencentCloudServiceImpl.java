package org.openapitools.service;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.*;
import com.qcloud.cos.model.ciModel.auditing.AudioAuditingRequest;
import com.qcloud.cos.model.ciModel.auditing.AudioAuditingResponse;
import com.qcloud.cos.model.ciModel.auditing.ImageAuditingRequest;
import com.qcloud.cos.model.ciModel.auditing.ImageAuditingResponse;
import org.openapitools.exception.ExpectedException400;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.URL;

@Service
public class TencentCloudServiceImpl implements TencentCloudService {

    @Autowired
    COSClient cosClient;

    @Override
    public String uploadSceneCover(File jpgFile, long sceneId) throws Exception {
        String bucketName = "demo-bucket-1325569882";
        //上传对象，注意重复上传会覆盖原文件
        //先上传到/cover.jpg，如果审核失败，再删除
        String uploadKey = "scene/" + sceneId + "/cover.jpg";
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, uploadKey, jpgFile);
        //PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        cosClient.putObject(putObjectRequest);

        ImageAuditingRequest imageAuditingRequest = new ImageAuditingRequest();
        imageAuditingRequest.setBucketName(bucketName);
        imageAuditingRequest.setDetectType("porn,ads");
        imageAuditingRequest.setObjectKey(uploadKey);
        ImageAuditingResponse response = cosClient.imageAuditing(imageAuditingRequest);
        System.err.println(response);
        //response.getResult()返回1为违规敏感文件，返回2表示疑似敏感文件
        //response.getScore()越高,表明该图片越有可能是违规内容,通过设置illegalThreshold来控制审核严格程度
        int score = Integer.parseInt(response.getScore());
        int illegalThreshold = 75;//已上调的非法阈值
        if (!response.getResult().equals("0")) {
            cosClient.deleteObject(bucketName, uploadKey);
            switch (response.getLabel()) {
                case "Porn":
                    throw new ExpectedException400("图片包含色情内容");
                case "Ads":
                    throw new ExpectedException400("图片包含广告内容");
                default:
                    throw new ExpectedException400("图片其他不良导向内容");
            }
        } else if (score > illegalThreshold) {
            cosClient.deleteObject(bucketName, uploadKey);
            throw new ExpectedException400("疑似违规内容");
        }

        //如果过审，再创建/currentCover.jpg，表示可以合法使用的封面，数据库中存储该路径
        uploadKey = "scene/" + sceneId + "/currentCover.jpg";
        AccessControlList acl = new AccessControlList();
        //设置公有读，方便前端使用
        acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
        putObjectRequest = new PutObjectRequest(bucketName, uploadKey, jpgFile);
        putObjectRequest.setAccessControlList(acl);
        cosClient.putObject(putObjectRequest);

        // 生成目标对象currentCover.jpg的URL地址，不设置过期时间
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, uploadKey);
        URL objectUrl = cosClient.generatePresignedUrl(generatePresignedUrlRequest);
        String tempUrl = objectUrl.toString();
        //数据库不保存"？"后面的参数
        String finalUrl = tempUrl.substring(0, tempUrl.indexOf("?"));
        return finalUrl;
    }

    @Override
    public String uploadUserAvatar(File jpgFile, long userId) throws Exception {
        String bucketName = "demo-bucket-1325569882";
        //上传对象，注意重复上传会覆盖原文件
        //先上传到/cover.jpg，如果审核失败，再删除
        String uploadKey = "user/" + userId + "/avatar.jpg";
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, uploadKey, jpgFile);
        //PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        cosClient.putObject(putObjectRequest);

        ImageAuditingRequest imageAuditingRequest = new ImageAuditingRequest();
        imageAuditingRequest.setBucketName(bucketName);
        imageAuditingRequest.setDetectType("porn,ads");
        imageAuditingRequest.setObjectKey(uploadKey);
        ImageAuditingResponse response = cosClient.imageAuditing(imageAuditingRequest);
        System.err.println(response);
        //response.getResult()返回1为违规敏感文件，返回2表示疑似敏感文件
        //response.getScore()越高,表明该图片越有可能是违规内容,通过设置illegalThreshold来控制审核严格程度
        int score = Integer.parseInt(response.getScore());
        int illegalThreshold = 50;
        if (!response.getResult().equals("0")) {
            cosClient.deleteObject(bucketName, uploadKey);
            switch (response.getLabel()) {
                case "Porn":
                    throw new ExpectedException400("图片包含色情内容");
                case "Ads":
                    throw new ExpectedException400("图片包含广告内容");
                default:
                    throw new ExpectedException400("图片其他不良导向内容");
            }
        } else if (score > illegalThreshold) {
            cosClient.deleteObject(bucketName, uploadKey);
            throw new ExpectedException400("疑似违规内容");
        }

        //如果过审，再创建/currentCover.jpg，表示可以合法使用的封面，数据库中存储该路径
        uploadKey = "user/" + userId + "/currentAvatar.jpg";
        AccessControlList acl = new AccessControlList();
        //设置公有读，方便前端使用
        putObjectRequest = new PutObjectRequest(bucketName, uploadKey, jpgFile);
        putObjectRequest.setAccessControlList(acl);
        cosClient.putObject(putObjectRequest);

        // 生成目标对象currentCover.jpg的URL地址，不设置过期时间
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, uploadKey);
        URL objectUrl = cosClient.generatePresignedUrl(generatePresignedUrlRequest);
        String tempUrl = objectUrl.toString();
        //数据库不保存"？"后面的参数
        String finalUrl = tempUrl.substring(0, tempUrl.indexOf("?"));
        return finalUrl;
    }
    @Override
    public String sceneUploadAudio(File audioFile, long sceneId) throws Exception {
        String bucketName = "demo-bucket-1325569882";
        String uploadKey = "scene/audio_url/" + sceneId + "/sceneAudio.mp3";

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, uploadKey, audioFile);
        cosClient.putObject(putObjectRequest);

        //公开权限
        AccessControlList acl = new AccessControlList();
        acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, uploadKey);

        URL objectUrl = cosClient.generatePresignedUrl(generatePresignedUrlRequest);
        String tempUrl = objectUrl.toString();
        String finalUrl = tempUrl.substring(0, tempUrl.indexOf("?"));
        return finalUrl;
    }

    @Override
    public String uploadAudio(File audioFile, long annotationId) throws Exception {
        String bucketName = "demo-bucket-1325569882";

        //上传对象，注意重复上传会覆盖原文件
        //先上传到/introAudio.mp3，如果审核失败，再删除
        String uploadKey = "introduction/audio/" + annotationId + "/introAudio.mp3";

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, uploadKey, audioFile);
        cosClient.putObject(putObjectRequest);

        //公开权限
        AccessControlList acl = new AccessControlList();
        acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, uploadKey);

        URL objectUrl = cosClient.generatePresignedUrl(generatePresignedUrlRequest);
        String tempUrl = objectUrl.toString();
        String finalUrl = tempUrl.substring(0, tempUrl.indexOf("?"));
        return finalUrl;
    }
    @Override
    public String uploadImageToCOS(File jpgFile, long annotationId) throws Exception {
        String bucketName = "demo-bucket-1325569882";
        String uploadKey = "introduction/image/" + annotationId + "/image.jpg";
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, uploadKey, jpgFile);
        cosClient.putObject(putObjectRequest);

        AccessControlList acl = new AccessControlList();
        acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, uploadKey);

        URL objectUrl = cosClient.generatePresignedUrl(generatePresignedUrlRequest);
        String tempUrl = objectUrl.toString();
        String finalUrl = tempUrl.substring(0, tempUrl.indexOf("?"));
        return finalUrl;
    }

    @Override
    public String uploadVideoToCOS(File videoFile, long annotationId) throws Exception {
        String bucketName = "demo-bucket-1325569882";
        String uploadKey = "introduction/video/" + annotationId + "/video.mp4";
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, uploadKey, videoFile);
        cosClient.putObject(putObjectRequest);

        AccessControlList acl = new AccessControlList();
        acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, uploadKey);

        URL objectUrl = cosClient.generatePresignedUrl(generatePresignedUrlRequest);
        String tempUrl = objectUrl.toString();
        String finalUrl = tempUrl.substring(0, tempUrl.indexOf("?"));
        return finalUrl;
    }
}
