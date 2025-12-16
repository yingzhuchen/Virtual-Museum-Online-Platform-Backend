package org.openapitools.resolver;

import org.openapitools.model.AudioResponse;
import org.openapitools.model.UploadImageRequest;
import org.openapitools.model.UploadImageResponse;
import org.openapitools.model.IntroductionUploadResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public interface GeneralResolver {
    UploadImageResponse uploadImage(UploadImageRequest request, long userId) throws Exception;

    void runCommand(String command, long userId) throws Exception ;

    IntroductionUploadResponse uploadVideoToCache(MultipartFile file, long userId) throws Exception;

    IntroductionUploadResponse uploadImageToCache(MultipartFile jpgFile, long userId) throws Exception;
    AudioResponse uploadAudioToCache(MultipartFile audioFile, long userId) throws Exception;
}
