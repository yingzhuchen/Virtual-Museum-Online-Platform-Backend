package org.openapitools.resolver;

import org.openapitools.dao.*;
import org.openapitools.exception.ExpectedException400;
import org.openapitools.exception.ExpectedException403;
import org.openapitools.exception.ExpectedException404;
import org.openapitools.model.AudioResponse;
import org.openapitools.model.UploadImageRequest;
import org.openapitools.model.UploadImageResponse;
import org.openapitools.entity.User;
import org.openapitools.model.IntroductionUploadResponse;
import org.openapitools.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Base64;

@Service
public class GeneralResolverImpl implements GeneralResolver {

    @Autowired
    SceneDao sceneDao;

    @Autowired
    UserDao userDao;

    @Autowired
    SceneAnnotationIntroductionDao sceneAnnotationIntroductionDao;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    ElasticsSearchServiceImpl elasticsSearchService;

    @Autowired
    SceneCoverImageDao sceneCoverImageDao;

    @Autowired
    TencentCloudServiceImpl tencentCloudService;

    @Autowired
    ScheduleServiceImpl scheduleService;

    @Override
    public UploadImageResponse uploadImage(UploadImageRequest request, long userId) throws Exception {
        String base64Image = request.getImage();
        // 如果 base64 字符串以 "data:image" 开头，则应去除元信息
        if (base64Image.startsWith("data:image")) {
            base64Image = base64Image.substring(base64Image.indexOf(",") + 1);
        }
        LocalDateTime now = LocalDateTime.now();
        //北京时间东八区
        Instant currentInstant = now.toInstant(ZoneOffset.ofHours(8));
        long timeStamp = currentInstant.getEpochSecond();

        String dirPath = "cache";
        File dir = new File(dirPath);
        if (!dir.exists() || !dir.isDirectory()) dir.mkdir();
        //图像文件临时存储在服务器上
        String filePath = dirPath + "/" + request.getType().getValue() + userId + timeStamp + "jpgFile.jpg";
        File jpgFile = new File(filePath);

        try (FileOutputStream fos = new FileOutputStream(jpgFile)) {
            byte[] imageBytes = Base64.getDecoder().decode(base64Image);
            fos.write(imageBytes);
            fos.flush();
        } catch (Exception e) {
            throw new ExpectedException400("base64图片解析失败");
        }
        if (ImageIO.read(jpgFile) == null) throw new ExpectedException400("base64图片解析失败");

        String finalUrl = filePath;
        if (request.getType().getValue().equals("avatar")) {
            User user = userDao.findById(userId).get();
            finalUrl = tencentCloudService.uploadUserAvatar(jpgFile, userId);
            user.setAvatarUrl(finalUrl);
            userDao.save(user);
        }

        UploadImageResponse uploadImageResponse = new UploadImageResponse();
        uploadImageResponse.setImageUrl(finalUrl);
        scheduleService.startCleanThread(filePath);
        return uploadImageResponse;
    }

    @Override
    public void runCommand(String command, long userId) throws Exception {
        if (!userService.getUserRoleById(userId).equals("Admin")) throw new ExpectedException403("非管理员无权限");
        switch (command) {
            case "es init user index":
                elasticsSearchService.createUserIndex();
                break;
            case "es update user doc":
                elasticsSearchService.updateUserDoc();
                break;
            case "es init scene index":
                elasticsSearchService.createSceneIndex();
                break;
            case "es update scene doc":
                elasticsSearchService.updateSceneDoc();
                break;
            case "clean cache images":
                scheduleService.autoClean();
                break;
            case "calculate recommend dat":
                scheduleService.autoGenerateRecommendDat();
                break;
            default:
                throw new ExpectedException404("不支持该命令：" + command);
        }
    }

    @Override
    public IntroductionUploadResponse uploadVideoToCache(MultipartFile file, long userId) throws Exception {
        LocalDateTime now = LocalDateTime.now();
        //北京时间东八区
        Instant currentInstant = now.toInstant(ZoneOffset.ofHours(8));
        long timeStamp = currentInstant.getEpochSecond();
        String filePath = "cache/" + timeStamp + "video.mp4";
        IntroductionUploadResponse response = new IntroductionUploadResponse();

        try (InputStream inputStream = file.getInputStream()) {
            Path outputFile = Paths.get(filePath);
            Files.copy(inputStream, outputFile, StandardCopyOption.REPLACE_EXISTING);
            System.err.println("MPEG 文件生成成功：" + filePath);

            response.setVideoUrl(filePath);

        } catch (IOException e) {
            System.err.println("MPEG 文件生成失败：" + e.getMessage());
        }
        return response;
    }

    @Override
    public AudioResponse uploadAudioToCache(MultipartFile audioFile, long userId) throws Exception {

        //北京时间东八区
        LocalDateTime now = LocalDateTime.now();
        Instant currentInstant = now.toInstant(ZoneOffset.ofHours(8));
        long timeStamp = currentInstant.getEpochSecond();

        String dirPath = "cache";
        File dir = new File(dirPath);
        if (!dir.exists() || !dir.isDirectory()) dir.mkdir();
        String filePath = dirPath + "/" + userId + timeStamp + "Audio.mp3";

        AudioResponse response = new AudioResponse();
        try (InputStream inputStream = audioFile.getInputStream();) {
            Path outputFile = Paths.get(filePath);
            Files.copy(inputStream, outputFile, StandardCopyOption.REPLACE_EXISTING);
            System.err.println("mp3 文件生成成功：" + filePath);

            response.setAudioUrl(filePath);

        } catch (IOException e) {
            System.err.println("mp3 文件生成失败：" + e.getMessage());
        }
        return response;
    }

    @Override
    public IntroductionUploadResponse uploadImageToCache(MultipartFile jpgFile, long userId) throws Exception {
        LocalDateTime now = LocalDateTime.now();
        //北京时间东八区
        Instant currentInstant = now.toInstant(ZoneOffset.ofHours(8));
        long timeStamp = currentInstant.getEpochSecond();
        String filePath = "cache/" + timeStamp + "image.jpg";
        IntroductionUploadResponse response = new IntroductionUploadResponse();

        try (InputStream inputStream = jpgFile.getInputStream();) {
            Path outputFile = Paths.get(filePath);
            Files.copy(inputStream, outputFile, StandardCopyOption.REPLACE_EXISTING);
            System.err.println("JPG 文件生成成功：" + filePath);

            response.setImageUrl(filePath);

        } catch (IOException e) {
            System.err.println("JPG 文件生成失败：" + e.getMessage());
        }
        return response;
    }

}
