package org.openapitools.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.qcloud.cos.COSClient;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.dao.*;
import org.openapitools.dto.AnnotationData;
import org.openapitools.dto.LinePointList;
import org.openapitools.entity.*;
import org.openapitools.entity.Scene;
import org.openapitools.exception.ExpectedException;
import org.openapitools.exception.ExpectedException400;
import org.openapitools.exception.ExpectedException404;
import org.openapitools.model.EsScene;
import org.openapitools.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@SpringBootApplication
@EnableScheduling
@org.springframework.transaction.annotation.Transactional
public class SceneServiceImpl implements SceneService {

    @Autowired
    ElasticsSearchServiceImpl elasticsSearchService;

    public class ScenesListFilter {

        List<Scene> scenesList;

        int first = 10;

        //仅对SceneServiceImpl提供构造器，对外部不提供构造器
        private ScenesListFilter(List<Scene> scenesList) {
            this.scenesList = scenesList;
        }

        public void filteredByFirst(int value) {
            first = value;
        }

        public void filteredByAfter(int value) {
            scenesList = scenesList.stream().filter((scene) -> {
                return scene.getSceneId() > value;
            }).collect(Collectors.toList());
        }

        public void filteredByKeyword(String value) {
            scenesList = scenesList.stream().filter((scene) -> {
                return scene.getSceneName().matches(".*" + value + ".*")
                        || scene.getSceneDescription().matches(".*" + value + ".*");
            }).collect(Collectors.toList());
        }

        public void filteredByIsPublic() {
            scenesList = scenesList.stream().filter((scene) -> {
                return scene.getSceneIsPublic() == 1;
            }).collect(Collectors.toList());
        }

        public void filteredByElasticsSearch(String value) {
            scenesList = scenesList.stream().filter((scene) -> {
                try {
                    for (Scene sceneInES : elasticsSearchService.searchScenesList(value)) {
                        if (sceneInES.getSceneId() == scene.getSceneId()) return true;
                    }
                    return false;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }).collect(Collectors.toList());
        }

        public List<Scene> filtered() {
            scenesList = scenesList.stream().filter((scene) -> {
                return scenesList.indexOf(scene) < first;
            }).collect(Collectors.toList());
            first = 10; //确保first的默认值总是10
            return scenesList;
        }

    }

    private ScenesListFilter scenesListFilter;

    @Autowired
    SceneDao sceneDao;

    @Autowired
    UserDao userDao;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    COSClient cosClient;

    @Autowired
    SceneCoverImageDao sceneCoverImageDao;

    @Autowired
    SceneAnnotationDao sceneAnnotationDao;

    @Autowired
    SceneAnnotationTransmissionDao sceneAnnotationTransmissionDao;

    @Autowired
    SceneAnnotationIntroductionDao sceneAnnotationIntroductionDao;

    @Autowired
    SceneAnnotationNavigationDao sceneAnnotationNavigationDao;

    @Autowired
    EsSceneMapper esSceneMapper;

    public ScenesListFilter getScenesListFilter(List<Scene> scenesList) {
        return new ScenesListFilter(scenesList);
    }


    @Override
    public Scene insertCollaborators(Scene scene, List<Long> collaborators) throws Exception {
        JSONArray jsonArray = new JSONArray();
        // 更新协作者
        for (Long collaboratorId : collaborators) {
            if (!userDao.findById(collaboratorId).isPresent()) {
                throw new ExpectedException404("协作者不存在：" + collaboratorId);
            } else if (scene.getSceneCreatorId() == collaboratorId) {
                throw new ExpectedException400("场景创建者不应该是协助者");
            }
            JSONObject collaboratorObj = new JSONObject();
            collaboratorObj.put("collaborator_id", collaboratorId);
            jsonArray.add(collaboratorObj);
        }
        // 将更新后的协作者列表转换为字符串
        String updatedCollaboratorsJson = jsonArray.toJSONString();
        // 更新场景对象中的协作者列表
        scene.setCollaborators(updatedCollaboratorsJson);
        return scene;
    }

    @Override
    public void insertCollaborators(long sceneId, List<Long> collaborators) throws Exception {
        Scene scene = sceneDao.findBySceneIdAndSceneIsDeleted(sceneId, 0).orElse(null);
        if (scene == null) {
            throw new ExpectedException404("场景不存在");
        }

        try {
            // 从场景对象中获取现有的协作者列表
            JSONArray jsonArray;
            String collaboratorsJson = scene.getCollaborators();
            if (collaboratorsJson != null && !collaboratorsJson.isEmpty()) {
                jsonArray = JSON.parseArray(collaboratorsJson);
            } else {
                jsonArray = new JSONArray();
            }

            // 检查是否有重复的协作者
            Set<Long> existingCollaborators = new HashSet<>();
            for (Object obj : jsonArray) {
                JSONObject jsonObject = (JSONObject) obj;
                existingCollaborators.add(jsonObject.getLong("collaborator_id"));
            }

            // 添加新的协作者
            for (Long collaboratorId : collaborators) {
                if (!userDao.findById(collaboratorId).isPresent()) {
                    throw new ExpectedException404("协作者不存在：" + collaboratorId);
                } else if (existingCollaborators.contains(collaboratorId)) {
                    throw new ExpectedException400("用户名已存在");
                } else if (scene.getSceneCreatorId() == collaboratorId) {
                    throw new ExpectedException400("场景创建者不应该是协助者");
                }
                JSONObject collaboratorObj = new JSONObject();
                collaboratorObj.put("collaborator_id", collaboratorId);
                jsonArray.add(collaboratorObj);
            }

            // 将更新后的协作者列表转换为字符串
            String updatedCollaboratorsJson = jsonArray.toJSONString();

            // 更新场景对象中的协作者列表
            scene.setCollaborators(updatedCollaboratorsJson);

            // 保存更新后的场景对象到数据库
            sceneDao.save(scene);
        } catch (JSONException e) {
            throw new IllegalArgumentException("协作者列表序列化失败");
        }
    }


    @Override
    public List<Long> getCollaborators(long sceneId) {
        Scene scene = sceneDao.findBySceneIdAndSceneIsDeleted(sceneId, 0).orElse(null);
        if (scene == null) {
            throw new IllegalArgumentException("找不到对应场景");
        }
        String collaboratorsJson = scene.getCollaborators();
        if (collaboratorsJson == null || collaboratorsJson.isEmpty()) {
            return new ArrayList<>();
        }
        JSONArray jsonArray = JSON.parseArray(collaboratorsJson);
        List<Long> collaboratorIds = new ArrayList<>();
        for (Object obj : jsonArray) {
            JSONObject collaboratorObj = (JSONObject) obj;
            collaboratorIds.add(collaboratorObj.getLong("collaborator_id"));
        }
        return collaboratorIds;
    }

    @Override
    public SceneResponse removeCollaborator(long sceneId, List<Long> collaboratorIds) {
        Scene scene = sceneDao.findBySceneIdAndSceneIsDeleted(sceneId, 0).orElse(null);
        if (scene == null) {
            throw new IllegalArgumentException("场景未找到");
        }

        // 获取现有的协作者列表
        List<Long> collaborators = getCollaborators(scene.getSceneId());

        // 检查协作者是否存在并逐个移除
        collaborators.removeIf(collaboratorIds::contains);

        // 更新场景对象
        updateSceneCollaborators(sceneId, collaborators);

        // 构造响应对象
        SceneResponse response = new SceneResponse();
        response.setScene(scene); // 返回更新后的场景对象即可，无需再次查询数据库
        return response;
    }

    public void updateSceneCollaborators(long sceneId, List<Long> newCollaborators) {// 完全替换场景协作者列表
        Scene scene = sceneDao.findBySceneIdAndSceneIsDeleted(sceneId, 0).orElse(null);
        if (scene == null) {
            throw new IllegalArgumentException("Scene does not exist");
        }

        // 添加新的协作者
        // 构建协作者列表的 JSON 数组
        JSONArray jsonArray = new JSONArray();
        for (Long collaboratorId : newCollaborators) {
            JSONObject collaboratorObj = new JSONObject();
            collaboratorObj.put("collaborator_id", collaboratorId);
            jsonArray.add(collaboratorObj);
        }

        // 将更新后的协作者列表转换为字符串
        String updatedCollaboratorsJson = jsonArray.toJSONString();

        // 更新场景对象中的协作者列表
        scene.setCollaborators(updatedCollaboratorsJson);

        // 保存更新后的场景对象到数据库
        sceneDao.save(scene);

    }


    @Override
    public String addSceneCollaborators(long sceneId, List<Long> newCollaborators) {
        Scene scene = sceneDao.findBySceneIdAndSceneIsDeleted(sceneId, 0).orElse(null);
        if (scene == null) {
            throw new IllegalArgumentException("Scene does not exist");
        }

        try {
            // 解析现有的协作者列表
            JSONArray jsonArray;
            String collaboratorsJson = scene.getCollaborators();
            if (collaboratorsJson != null && !collaboratorsJson.isEmpty()) {
                jsonArray = JSON.parseArray(collaboratorsJson);
            } else {
                jsonArray = new JSONArray();
            }

            // 检查重复的新协作者
            List<Long> existingCollaborators = new ArrayList<>();
            for (Object obj : jsonArray) {
                JSONObject collaboratorObj = (JSONObject) obj;
                existingCollaborators.add(collaboratorObj.getLong("collaborator_id"));
            }
            for (Long newCollaboratorId : newCollaborators) {
                if (!existingCollaborators.contains(newCollaboratorId)) {
                    // 添加新协作者到现有列表中
                    JSONObject collaboratorObj = new JSONObject();
                    collaboratorObj.put("collaborator_id", newCollaboratorId);
                    jsonArray.add(collaboratorObj);
                }
            }

            // 将更新后的协作者列表转换为字符串
            String updatedCollaboratorsJson = jsonArray.toJSONString();

            // 更新场景对象中的协作者列表
            scene.setCollaborators(updatedCollaboratorsJson);

            // 保存更新后的场景对象到数据库
            sceneDao.save(scene);

            // 构造响应对象
            JSONObject response = new JSONObject();
            response.put("collaborators", jsonArray);

            // 返回 JSON 格式的响应对象给前端
            return response.toJSONString();
        } catch (JSONException e) {
            throw new IllegalArgumentException("Failed to serialize collaborators list");
        }
    }


//    @Override
//    public SceneResponse getScene(long id, long userId) throws Exception {
//        Scene scene = sceneDao.findBySceneIdAndSceneIsDeleted(id, 0).orElse(null);
//        if (scene == null) throw new IllegalArgumentException("对应场景不存在");
//        if (!scene.isSceneAssist(userId) && scene.getSceneCreatorId() != userId)
//            throw new IllegalAccessException("非场景管理员，无权限");
//        SceneResponse resp = new SceneResponse();
//        resp.setScene(scene);
//        return resp;
//    }

    @Override
    public int delScene(Long sceneId) {
        return sceneDao.softDeleteSceneBySceneId(sceneId);
    }

    @Override
    public List<Scene> getDeletedScenesIn30Days(long userId) {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minus(30, ChronoUnit.DAYS);
        return sceneDao.findScenesDeletedInLast30Days(userId,1,startDate, endDate);
    }


    public ListScenesResponse getScenesList(NativeWebRequest request, long userId) throws Exception {
        ListScenesResponse resp = new ListScenesResponse();
        //user_id应该被首先处理
        long targetUserId = userId;
        if (request.getParameter("user_id") != null) {
            long parameterUserId = Long.parseLong(request.getParameter("user_id"));
            if (userService.getUserById(parameterUserId) == null) {
                throw new IllegalAccessException("对应用户不存在");
            }
            targetUserId = parameterUserId;
        }

        List<Scene> scenesList = sceneDao.findAllByCreatorIdAndSceneIsDeleted(targetUserId, 0);

        resp.setTotalCount(BigDecimal.valueOf(scenesList.size()));
        this.scenesListFilter = new ScenesListFilter(scenesList);
        Iterator<String> iterator = request.getParameterNames();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String value = request.getParameter(key);
            if (value == null) continue;
            switch (key) {
                case "user_id":
                    if (targetUserId != userId) scenesListFilter.filteredByIsPublic();
                    break;
                case "first":
                    scenesListFilter.filteredByFirst(Integer.parseInt(value));
                    break;
                case "after":
                    scenesListFilter.filteredByAfter(Integer.parseInt(value));
                    break;
                case "keyword":
                    if (value.length() > 10) throw new IllegalArgumentException("关键词过长");
                    scenesListFilter.filteredByKeyword(value);
                    break;
                default:
                    break;
            }
        }
        scenesList = scenesListFilter.filtered();
        //最后，如果查询的不是他人的场景，而是自己的场景时，还要添加自己的协助场景
        if (targetUserId == userId) {
            for (Scene scene : sceneDao.findAll()) {
                if (scene.isSceneAssist(userId)) scenesList.add(scene);
            }
        }
        List<SceneResponse> scenResponsesList = new ArrayList<SceneResponse>();
        for (Scene scene : scenesList) {
            SceneResponse sceneResponse = new SceneResponse();
            sceneResponse.setScene(scene);
            scenResponsesList.add(sceneResponse);
        }
        resp.setData(scenResponsesList);
        return resp;
    }


//    @Override
//    public CoverResponse uploadImage(UploadCoverRequest request, long userId) throws Exception {
//        String base64Image = request.getImage();
//        long sceneId = request.getSceneId();
//        if (!sceneDao.findBySceneIdAndSceneIsDeleted(sceneId, 0).isPresent())
//            throw new IllegalArgumentException("对应场景不存在");
//        if (!sceneDao.findBySceneIdAndSceneIsDeleted(sceneId, 0).get().isSceneAssist(userId)
//                && sceneDao.findBySceneIdAndSceneIsDeleted(sceneId, 0).get().getSceneCreatorId() != userId)
//            throw new IllegalAccessException("非场景管理员，无权限");
//
//        //图像文件临时存储在服务器上
//        String filePath = sceneId + "jpgFile.jpg";
//        File jpgFile = new File(filePath);
//        try (FileOutputStream fos = new FileOutputStream(jpgFile)) {
//            byte[] imageBytes = Base64.getDecoder().decode(base64Image);
//            fos.write(imageBytes);
//            fos.flush();
//        } catch (IOException e) {
//            throw new IOException("base64图片解析失败");
//        }
//        if (ImageIO.read(jpgFile) == null) throw new IOException("base64解析失败");
//
//        String bucketName = "demo-bucket-1325569882";
//        //上传对象，注意重复上传会覆盖原文件
//        //先上传到/cover.jpg，如果审核失败，再删除
//        String uploadKey = "scene/" + sceneId + "/cover.jpg";
//        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, uploadKey, jpgFile);
//        //PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
//        cosClient.putObject(putObjectRequest);
//
//        ImageAuditingRequest imageAuditingRequest = new ImageAuditingRequest();
//        imageAuditingRequest.setBucketName(bucketName);
//        imageAuditingRequest.setDetectType("porn,ads");
//        imageAuditingRequest.setObjectKey(uploadKey);
//        ImageAuditingResponse response = cosClient.imageAuditing(imageAuditingRequest);
//        System.err.println(response);
//        //response.getResult()返回1为违规敏感文件，返回2表示疑似敏感文件
//        //response.getScore()越高,表明该图片越有可能是违规内容,通过设置illegalThreshold来控制审核严格程度
//        int score = Integer.parseInt(response.getScore());
//        int illegalThreshold = 50;
//        if (!response.getResult().equals("0")) {
//            cosClient.deleteObject(bucketName, uploadKey);
//            switch (response.getLabel()) {
//                case "Porn":
//                    throw new IOException("图片包含色情内容");
//                case "Ads":
//                    throw new IOException("图片包含广告内容");
//                default:
//                    throw new IOException("图片其他不良导向内容");
//            }
//        } else if (score > illegalThreshold) {
//            cosClient.deleteObject(bucketName, uploadKey);
//            throw new IOException("疑似违规内容");
//        }
//
//        //如果过审，再创建/currentCover.jpg，表示可以合法使用的封面，数据库中存储该路径
//        uploadKey = "scene/" + sceneId + "/currentCover.jpg";
//        AccessControlList acl = new AccessControlList();
//        //设置公有读，方便前端使用
//        acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
//        putObjectRequest = new PutObjectRequest(bucketName, uploadKey, jpgFile);
//        putObjectRequest.setAccessControlList(acl);
//        cosClient.putObject(putObjectRequest);
//
//        // 生成目标对象currentCover.jpg的URL地址，不设置过期时间
//        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, uploadKey);
//        URL objectUrl = cosClient.generatePresignedUrl(generatePresignedUrlRequest);
//        String tempUrl = objectUrl.toString();
//        //数据库不保存"？"后面的参数
//        String finalUrl = tempUrl.substring(0, tempUrl.indexOf("?"));
//
//        SceneCoverImage sceneCoverImage = sceneCoverImageDao.findById(sceneId).orElse(null);
//        if (sceneCoverImage == null) {
//            sceneCoverImage = new SceneCoverImage();
//            sceneCoverImage.setSceneId(sceneId);
//        }
//        sceneCoverImage.setUrl(finalUrl);
//        sceneCoverImage.setUploadDate(LocalDateTime.now());
//        sceneCoverImageDao.save(sceneCoverImage);
//
//        CoverResponse coverResponse = new CoverResponse();
//        coverResponse.setUrl(finalUrl);
//        coverResponse.setAuthor(
//                userDao.findById(
//                        sceneDao.findBySceneIdAndSceneIsDeleted(sceneId, 0).get().getSceneCreatorId()
//                ).get().getUserName()
//        );
//        coverResponse.setTitle(sceneDao.findBySceneIdAndSceneIsDeleted(sceneId, 0).get().getSceneName());
//
//        //最后回收临时图像文件
//        jpgFile.delete();
//
//        return coverResponse;
//    }
//
//    @Override
//    public AnnotationResponse createAnnotation(AnnotationRequest request, long userId) throws Exception {
//        SceneAnnotation sceneAnnotation = new SceneAnnotation();
//        long sceneId = request.getSceneId();
//        if (!sceneDao.findBySceneIdAndSceneIsDeleted(sceneId, 0).isPresent())
//            throw new IllegalArgumentException("找不到对应场景");
//        Scene scene = sceneDao.findBySceneIdAndSceneIsDeleted(sceneId, 0).get();
//        if (!scene.isSceneAssist(userId) && scene.getSceneCreatorId() != userId)
//            throw new IllegalAccessException("非场景管理员，无权限");
//        sceneAnnotation.setSceneId(request.getSceneId());
//        sceneAnnotation.setName(request.getName());
//        //TODO 处理非法type异常
//        sceneAnnotation.setType(request.getAnnotationData().getType());
//        String color = request.getColor();
//        if (!color.matches("^#[0-9A-Fa-f]{6}$")) throw new IllegalArgumentException("非法颜色字符串");
//        sceneAnnotation.setColor(request.getColor());
//        sceneAnnotation.setRadius(request.getRadius().doubleValue());
//        double positionX = request.getPositionX().doubleValue();
//        double positionY = request.getPositionY().doubleValue();
//        double positionZ = request.getPositionZ().doubleValue();
//        if (sceneAnnotationDao.findByPositionXAndPositionYAndPositionZ(positionX, positionY, positionZ).isPresent()) {
//            throw new IllegalArgumentException("该位置上已存在打点标记");
//        }
//        sceneAnnotation.setPositionX(positionX);
//        sceneAnnotation.setPositionY(positionY);
//        sceneAnnotation.setPositionZ(positionZ);
//        sceneAnnotationDao.save(sceneAnnotation);
//        AnnotationResponse resp = new AnnotationResponse();
//        resp.setAnnotation(sceneAnnotationDao.findByPositionXAndPositionYAndPositionZ(positionX, positionY, positionZ).get());
//        return resp;
//    }
//
//    @Override
//    public ListAnnotationResponse getAnnotationsList(Integer sceneId, long userId) throws Exception {
//        if (!sceneDao.findBySceneIdAndSceneIsDeleted(sceneId, 0).isPresent())
//            throw new IllegalArgumentException("对应场景不存在");
//        List<SceneAnnotation> annotationList = sceneAnnotationDao.findAllBySceneId((long) sceneId);
//        List<AnnotationResponse> annotationResponseList = new ArrayList<AnnotationResponse>();
//        for (SceneAnnotation annotation : annotationList) {
//            AnnotationResponse annotationResponse = new AnnotationResponse();
//            annotationResponse.setAnnotation(annotation);
//            annotationResponseList.add(annotationResponse);
//        }
//        ListAnnotationResponse resp = new ListAnnotationResponse();
//        resp.setTotalCount(annotationList.size());
//        resp.setData(annotationResponseList);
//        return resp;
//    }
//
//    @Override
//    public void removeAnnotation(long annotationId, long userId) throws Exception {
//        if (!sceneAnnotationDao.findById(annotationId).isPresent()) throw new IllegalArgumentException("该标记不存在");
//        SceneAnnotation annotation = sceneAnnotationDao.findById(annotationId).get();
//        if (!sceneDao.findBySceneIdAndSceneIsDeleted(annotation.getSceneId(), 0).isPresent())
//            throw new IllegalArgumentException("该标记所处场景可能已被删除");
//        Scene scene = sceneDao.findBySceneIdAndSceneIsDeleted(annotation.getSceneId(), 0).get();
//        if (!scene.isSceneAssist(userId) && scene.getSceneCreatorId() != userId)
//            throw new IllegalAccessException("非场景管理员，无权限");
//        sceneAnnotationDao.deleteById(annotationId);
//    }
//
//    @Override
//    public AnnotationResponse editAnnotation(EditAnnotationRequest req, long userId) throws Exception {
//        long annotationId = req.getAnnotationId();
//        if (!sceneAnnotationDao.findById(annotationId).isPresent()) throw new IllegalArgumentException("该标记不存在");
//        SceneAnnotation annotation = sceneAnnotationDao.findById(annotationId).get();
//        if (!sceneDao.findBySceneIdAndSceneIsDeleted(annotation.getSceneId(), 0).isPresent())
//            throw new IllegalArgumentException("该标记所处场景可能已被删除");
//        Scene scene = sceneDao.findBySceneIdAndSceneIsDeleted(annotation.getSceneId(), 0).get();
//        if (!scene.isSceneAssist(userId) && scene.getSceneCreatorId() != userId)
//            throw new IllegalAccessException("非场景管理员，无权限");
//        SceneAnnotation existingAnnotation = sceneAnnotationDao.findById(annotationId).get();
//        String[] argumentsList = {
//                "name",
//                "type",
//                "color",
//                "radius",
//                "position_x",
//                "position_y",
//                "position_z"
//        };
//        Iterator<String> iterator = Arrays.stream(argumentsList).iterator();
//        BigDecimal position_x = null;
//        BigDecimal position_y = null;
//        BigDecimal position_z = null;
//        while (iterator.hasNext()) {
//            String key = iterator.next();
//            switch (key) {
//                case "name":
//                    String name = req.getName();
//                    if (name == null) break;
//                    else if (name.length() > 15) throw new IllegalArgumentException("标记名过长");
//                    existingAnnotation.setName(name);
//                    break;
//                case "type":
//                    //TODO 处理非法type异常
//                    AnnotationType type = AnnotationType.fromValue(req.getAnnotationData().getType());
//                    if (type == null) break;
//                    existingAnnotation.setType(type.getValue());
//                    break;
//                case "color":
//                    String color = req.getColor();
//                    if (color == null) break;
//                    if (!color.matches("^#[0-9A-Fa-f]{6}$")) throw new IllegalArgumentException("非法颜色字符串");
//                    existingAnnotation.setColor(color);
//                    break;
//                case "radius":
//                    BigDecimal radius = req.getRadius();
//                    if (radius == null) break;
//                    existingAnnotation.setRadius(radius.doubleValue());
//                    break;
//                case "position_x":
//                    position_x = req.getPositionX();
//                    if (position_x == null) break;
//                    break;
//                case "position_y":
//                    position_y = req.getPositionY();
//                    if (position_y == null) break;
//                    break;
//                case "position_z":
//                    position_z = req.getPositionZ();
//                    if (position_z == null) break;
//                    break;
//                default:
//                    break;
//            }
//        }
//        if (position_x != null && position_y != null && position_z != null) {
//            double positionX = position_x.doubleValue();
//            double positionY = position_y.doubleValue();
//            double positionZ = position_z.doubleValue();
//            if (sceneAnnotationDao.findByPositionXAndPositionYAndPositionZ(positionX, positionY, positionZ).isPresent()) {
//                throw new IllegalArgumentException("该位置上已存在打点标记");
//            }
//            existingAnnotation.setPositionX(positionX);
//            existingAnnotation.setPositionY(positionY);
//            existingAnnotation.setPositionZ(positionZ);
//        }
//        sceneAnnotationDao.save(existingAnnotation);
//        AnnotationResponse resp = new AnnotationResponse();
//        resp.setAnnotation(sceneAnnotationDao.findById(annotationId).get());
//        return resp;
//    }
//
//    @Override
//    public CoverResponse downloadImage(long sceneId) throws Exception {
//        CoverResponse coverResponse = new CoverResponse();
//        Scene scene = sceneDao.findBySceneIdAndSceneIsDeleted(sceneId, 0).orElse(null);
//        if (scene == null) throw new IllegalArgumentException("对应场景不存在");
//        SceneCoverImage sceneCoverImage = sceneCoverImageDao.findById(sceneId).orElse(null);
//        if (sceneCoverImage == null) throw new IllegalArgumentException("该场景无封面");
//        coverResponse.setUrl(sceneCoverImage.getUrl());
//        coverResponse.setAuthor(
//                userDao.findById(
//                        scene.getSceneCreatorId()
//                ).get().getUserName()
//        );
//        coverResponse.setTitle(scene.getSceneName());
//        return coverResponse;
//    }

    @Override
    public List<String> editSceneTags(long sceneId, List<String> newTags) throws ExpectedException404 {
        Scene scene = sceneDao.findBySceneIdAndSceneIsDeleted(sceneId, 0).orElse(null);
        if (scene == null) {
            throw new ExpectedException404("场景不存在");
        }

        // 构建tags列表的 JSON 数组
        JSONArray jsonArray = new JSONArray();
        for (String tag : newTags) {
            JSONObject tagObj = new JSONObject();
            tagObj.put("tag_name", tag);
            jsonArray.add(tagObj);
        }

        // 将更新后的标签列表转换为字符串
        String updatedTagsJson = jsonArray.toJSONString();

        // 更新场景对象中的标签列表
        scene.setTags(updatedTagsJson);

        // 保存更新后的场景对象到数据库
        sceneDao.save(scene);

        return JSONObject.parseArray(updatedTagsJson, String.class); // Just parse the updated JSON string directly, no need to create another JSON array.
    }

    @Override
    public void editBirthPositionVector3(long sceneId, Vector3 topLeftVector3) throws ExpectedException {
        Scene scene = sceneDao.findBySceneIdAndSceneIsDeleted(sceneId, 0).orElse(null);
        if (scene == null) {
            throw new ExpectedException404("场景不存在");
        }

        // 构建包含 x, y, z 属性的 JSON 对象
        JSONObject birthPositionJson = new JSONObject();
        birthPositionJson.put("x", topLeftVector3.getX());
        birthPositionJson.put("y", topLeftVector3.getY());
        birthPositionJson.put("z", topLeftVector3.getZ());

        // 将 JSON 对象转换为字符串
        String birthPosition = birthPositionJson.toJSONString();

        // 更新场景对象中的 topLeftCorner 字段
        scene.setBirthPosition(birthPosition);

        // 保存更新后的场景对象到数据库
        sceneDao.save(scene);

    }

    @Override
    public void editBirthRotationVector3(long sceneId, Vector3 birthRotationVector3) throws ExpectedException {
        Scene scene = sceneDao.findBySceneIdAndSceneIsDeleted(sceneId, 0).orElse(null);
        if (scene == null) {
            throw new ExpectedException404("场景不存在");
        }

        // 构建包含 x, y, z 属性的 JSON 对象
        JSONObject birthRotationJson = new JSONObject();
        birthRotationJson.put("x", birthRotationVector3.getX());
        birthRotationJson.put("y", birthRotationVector3.getY());
        birthRotationJson.put("z", birthRotationVector3.getZ());

        // 将 JSON 对象转换为字符串
        String updateBirthRotationJson = birthRotationJson.toJSONString();

        // 更新场景对象中的 bottomRightCorner 字段
        scene.setBirthRotation(updateBirthRotationJson);

        // 保存更新后的场景对象到数据库
        sceneDao.save(scene);
    }

    @Override
    public AnnotationData getTransmissionDetails(long annotationId) {
        AnnotationData annotationData = new AnnotationData();
        SceneAnnotationTransmission sceneAnnotationTransmission = sceneAnnotationTransmissionDao.findByAnnotationId(annotationId);
        if (sceneAnnotationTransmission.getToTransmissionAnnotationId() != null) {
            annotationData.setToTransmissionAnnotationId(sceneAnnotationTransmission.getToTransmissionAnnotationId());
            annotationData.setType("transmission");
        }
        annotationData.setToSceneId(sceneAnnotationTransmission.getToSceneId());
        SceneAnnotation sceneAnnotation = sceneAnnotationDao.findById(annotationId).get();
        annotationData.setName(sceneAnnotation.getName());
        return annotationData;
    }

    @Override
    public AnnotationData getIntroductionDetails(long annotationId) {
        SceneAnnotationIntroduction sceneAnnotationIntroduction = sceneAnnotationIntroductionDao.findByAnnotationId(annotationId);
        AnnotationData annotationData = new AnnotationData();
        annotationData.setBriefIntroduction(sceneAnnotationIntroduction.getBriefIntroduction());
        annotationData.setIntroduction(sceneAnnotationIntroduction.getIntroduction());
        annotationData.setImageUrl(sceneAnnotationIntroduction.getImageUrl());
        annotationData.setVideo(sceneAnnotationIntroduction.getVideo());
        annotationData.setFocalPointViewX(sceneAnnotationIntroduction.getFocalPointViewX());
        annotationData.setFocalPointViewY(sceneAnnotationIntroduction.getFocalPointViewY());
        annotationData.setFocalPointViewZ(sceneAnnotationIntroduction.getFocalPointViewZ());
        annotationData.setType("introduction");
        SceneAnnotation sceneAnnotation = sceneAnnotationDao.findById(annotationId).get();
        annotationData.setName(sceneAnnotation.getName());
        return annotationData;
    }

    @Override
    public AnnotationData getNavigationDetails(long annotationId) {
        SceneAnnotationNavigation sceneAnnotationNavigation = sceneAnnotationNavigationDao.findByAnnotationId(annotationId);
        AnnotationData annotationData = new AnnotationData();
        List<LinePointList> list = JSONObject.parseArray(sceneAnnotationNavigation.getLinePointList(), LinePointList.class);
        annotationData.setLinePointList(list);
        annotationData.setType("navigation");
        SceneAnnotation sceneAnnotation = sceneAnnotationDao.findById(annotationId).get();
        annotationData.setName(sceneAnnotation.getName());
        return annotationData;
    }

    @Override
    public String getTypeById(long annotationId) {
        Optional<SceneAnnotation> optionalAnnotation = sceneAnnotationDao.findById(annotationId);
        SceneAnnotation sceneAnnotationFound = optionalAnnotation.orElse(null);
        if (sceneAnnotationFound == null) {
            return "none";
        }
        SceneAnnotation sceneAnnotation = sceneAnnotationDao.findById(annotationId).get();
        if (sceneAnnotation.getIsDeleted() == 0)
            return sceneAnnotation.getType();
        return "none";
    }


    // 将数据库数据插入ES
    @Override
    public List<EsScene> databaseToElasticsearch() {

        List<EsScene> esSceneList = esSceneMapper.fetchUserData();

        for (EsScene esScene : esSceneList) {
            esScene.setId((int) esScene.getSceneId());
        }

        return esSceneList;
    }

}
