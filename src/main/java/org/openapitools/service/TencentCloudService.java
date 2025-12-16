package org.openapitools.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service

public interface TencentCloudService {
    //上传场景封面，返回结果url
    String uploadSceneCover(File jpgFile,long sceneId) throws Exception;

    String uploadUserAvatar(File jpgFile,long userId) throws Exception;


    String uploadImageToCOS(File jpgFile, long annotationId) throws Exception;

    String uploadVideoToCOS(File videoFile, long annotationId) throws Exception;
    String sceneUploadAudio(File audioFile, long annotationId) throws Exception;
    String uploadAudio(File audioFile, long annotationId) throws Exception ;

}
