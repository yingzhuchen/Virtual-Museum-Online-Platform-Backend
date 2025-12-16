package org.openapitools.resolver;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.validator.routines.UrlValidator;
import org.openapitools.dao.*;
import org.openapitools.dto.AnnotationData;
import org.openapitools.dto.LinePointList;
import org.openapitools.entity.*;
import org.openapitools.entity.Scene;
import org.openapitools.entity.Tags;
import org.openapitools.exception.ExpectedException400;
import org.openapitools.exception.ExpectedException403;
import org.openapitools.exception.ExpectedException404;
import org.openapitools.model.*;
import org.openapitools.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.NativeWebRequest;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SceneResolverImpl implements SceneResolver {

    @Autowired
    SceneDao sceneDao;

    @Autowired
    UserDao userDao;

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
    UserServiceImpl userService;

    @Autowired
    SceneServiceImpl sceneService;

    @Autowired
    TencentCloudServiceImpl tencentCloudService;

    @Autowired
    SceneFilterMachineImpl sceneFilterMachine;

    @Autowired
    JedisServiceImpl jedisService;

    @Autowired
    SceneRecommenderServiceImpl sceneRecommenderService;

    @Autowired
    TagsDao tagsDao;

    @Autowired
    HomepageCarouselImagesDao homepageCarouselImagesDao;

    @Autowired
    LikeSceneDao likeSceneDao;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public SceneResponse createScene(CreateSceneRequest req, long userId) throws Exception {
        String name = req.getName();
        if (name.length() > 50) throw new ExpectedException400("场景名过长");

        String url = req.getUrl();
        UrlValidator urlValidator = new UrlValidator();
        if (!urlValidator.isValid(url)) throw new ExpectedException400("场景URL不合法");

        String description = req.getDescription();
        if (description.length() > 300) throw new ExpectedException400("场景描述过长");

        Scene scene = new Scene();
        scene.setSceneCreatorId(userId);
        scene.setSceneCreateTime(ZonedDateTime.now(ZoneId.of("Asia/Shanghai")).toLocalDateTime());
        scene.setSceneName(name);
        scene.setSceneUrl(url);
        scene.setSceneDescription(description);

        Scene newScene = sceneDao.save(scene);

        // 插入协作者
        if (req.getCollaborators() != null && !req.getCollaborators().isEmpty()) {
            sceneService.updateSceneCollaborators(newScene.getSceneId(), req.getCollaborators());
        }

        //插入Tags
        if (req.getTags() != null && !req.getTags().isEmpty()) {
            sceneService.editSceneTags(newScene.getSceneId(), req.getTags());
        }
        //插入音频
        if (req.getAudioUrl() != null && !req.getAudioUrl().isEmpty()) {
            String filePath = req.getAudioUrl();
            Path outputFile = Paths.get(filePath);
            File audioFile = new File(outputFile.toString());
            String finalUrl = tencentCloudService.sceneUploadAudio(audioFile, newScene.getSceneId());
            newScene.setAudioUrl(finalUrl);
            newScene = sceneDao.save(newScene);
        }
        String finalUrl = null;
        File jpgFile = null;
        String jpgFileString = req.getCacheImageUrl();
        if (jpgFileString != null) {
            jpgFile = new File(jpgFileString);
            if (!jpgFile.exists()) throw new ExpectedException404("找不到该缓存图片");
            if (jpgFileString.startsWith("cache/sceneCover")) {
                finalUrl = tencentCloudService.uploadSceneCover(jpgFile, newScene.getSceneId());

                SceneCoverImage sceneCoverImage = sceneCoverImageDao.findById(newScene.getSceneId()).orElse(null);
                if (sceneCoverImage == null) {
                    sceneCoverImage = new SceneCoverImage();
                    sceneCoverImage.setSceneId(newScene.getSceneId());
                }
                sceneCoverImage.setUrl(finalUrl);
                sceneCoverImage.setUploadDate(LocalDateTime.now());
                sceneCoverImageDao.save(sceneCoverImage);

                newScene.setSceneCoverUrl(finalUrl);
            } else {
                throw new ExpectedException400("仅支持上传场景封面类型");
            }
        }

        newScene = sceneDao.save(scene);
        SceneResponse resp = new SceneResponse();
        resp.setScene(newScene);
        return resp;
    }

    @Override
    public SceneResponse getScene(long id, long userId) throws Exception {
        Scene scene = sceneDao.findBySceneIdAndSceneIsDeleted(id, 0).orElse(null);
        if (scene == null) throw new ExpectedException404("对应场景不存在");
        //TODO 决定是否保留权限限制
        //if (!scene.isSceneAssist(userId)&&scene.getSceneCreatorId() != userId) throw new ExpectedException403("非场景管理员，无权限");
        SceneResponse resp = new SceneResponse();
        resp.setScene(scene);
        //redis数据库中写入历史记录
        jedisService.saveSceneHistory(id, userId);
        return resp;
    }


    @Override
    public void deleteScene(long sceneId, long userId) throws Exception {
        Scene scene = sceneDao.findBySceneIdAndSceneIsDeleted(sceneId, 0).orElse(null);
        if (scene == null) throw new ExpectedException404("对应场景不存在");
        if (scene.getSceneCreatorId() != userId) throw new ExpectedException403("非场景创建者，无权限");
        scene.setDeleteTime(ZonedDateTime.now(ZoneId.of("Asia/Shanghai")).toLocalDateTime());
        ;
        sceneDao.softDeleteSceneBySceneId(sceneId);
        sceneDao.save(scene);
    }

    @Override
    public void unDeleteScene(long sceneId, long userId) throws Exception {
        Scene scene = sceneDao.findBySceneIdAndSceneIsDeleted(sceneId, 1).orElse(null);
        if (scene == null) throw new ExpectedException404("对应场景不存在或者场景未被删除，无法撤销");
        if (scene.getSceneCreatorId() != userId) throw new ExpectedException403("非场景创建者，无权限");
        scene.setDeleteTime(null);
        sceneDao.undoDeleteSceneBySceneId(sceneId);
        sceneDao.save(scene);
    }

    public ListScenesResponse getSceneListByMachine(NativeWebRequest request, long userId, int isRecycleBin) throws Exception {
        SceneFilterMachineImpl.FilterMachine machine = sceneFilterMachine.getFilterMachine();
        boolean publicMode = false;
        if (request.getParameter("publicMode") != null)
            publicMode = Boolean.parseBoolean(request.getParameter("publicMode"));

        List<Scene> scenesList = new ArrayList<>();
        long parameterUserId = -1;
        if (request.getParameter("user_id") != null) {
            parameterUserId = Long.parseLong(request.getParameter("user_id"));
            if (isRecycleBin == 1) {
                scenesList = sceneService.getDeletedScenesIn30Days(parameterUserId);
            } else {
                scenesList = sceneDao.findAllByCreatorIdAndSceneIsDeleted(parameterUserId, 0);
            }
        } else {
            scenesList = sceneDao.findAllBySceneIsPublicAndSceneIsDeleted(1, 0);
        }
        SceneFilterMachineImpl.SceneListContainer container = sceneFilterMachine.getSceneListContainer(scenesList);

        machine.switchAfter(request.getParameter("after") != null)
                .setAfter(request.getParameter("after") != null ? Integer.parseInt(request.getParameter("after")) : null)
                .switchKeyword(request.getParameter("keyword") != null)
                .setKeyword(request.getParameter("keyword") != null ? request.getParameter("keyword") : null)
                .switchTag(request.getParameter("tag") != null)
                .setTag(request.getParameter("tag") != null ? request.getParameter("tag") : null)
                .switchIsPublic((parameterUserId != userId) || publicMode)
                .switchFirst(request.getParameter("first") != null)
                .setFirst(request.getParameter("first") != null ? Integer.parseInt(request.getParameter("first")) : null)
                .start();

        int totalCount = 0;
        while (machine.isRunning(container)) {
            if (machine.getCurrentPhrase().equals("FILTER_BY_FIRST")) totalCount = container.getList().size();
            if (container.getList().isEmpty()) machine.shutDown();
        }

        if (isRecycleBin != 1 && !publicMode) {
            //最后，如果查询的不是他人的场景，而是自己的场景时，还要添加自己的协助场景
            if (parameterUserId == userId) {
                for (Scene scene : sceneDao.findAllByAssist(userId)) {
                    container.getList().add(scene);
                    totalCount++;
                }
            }
        }

        //对最终结果按时间排序，结果从新到旧
        PriorityQueue<Scene> scenePriorityQueueQueue = new PriorityQueue<>(new Comparator<Scene>() {
            @Override
            public int compare(Scene s1, Scene s2) {
                return s2.getSceneCreateTime().compareTo(s1.getSceneCreateTime());
            }
        });
        scenePriorityQueueQueue.addAll(container.getList());

        ListScenesResponse resp = new ListScenesResponse();
        List<SceneResponse> scenResponsesList = new ArrayList<>();
        while(!scenePriorityQueueQueue.isEmpty()){
            Scene scene=scenePriorityQueueQueue.poll();
            SceneResponse sceneResponse = new SceneResponse();
            sceneResponse.setScene(scene);
            scenResponsesList.add(sceneResponse);
        }
        resp.setData(scenResponsesList);
        resp.setTotalCount(BigDecimal.valueOf(totalCount));
        return resp;
    }

    public ListScenesResponse getScenesList(NativeWebRequest request, long userId) throws Exception {
        ListScenesResponse resp = new ListScenesResponse();
        //user_id应该被首先处理
        long targetUserId = userId;
        if (request.getParameter("user_id") != null) {
            long parameterUserId = Long.parseLong(request.getParameter("user_id"));
            if (userService.getUserById(parameterUserId) == null) {
                throw new ExpectedException404("对应用户不存在");
            }
            targetUserId = parameterUserId;
        }

        List<Scene> scenesList = sceneDao.findAllByCreatorIdAndSceneIsDeleted(targetUserId, 0);

        resp.setTotalCount(BigDecimal.valueOf(scenesList.size()));
        SceneServiceImpl.ScenesListFilter scenesListFilter = sceneService.getScenesListFilter(scenesList);
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
                    if (value.length() > 10) throw new ExpectedException400("关键词过长");
                    //scenesListFilter.filteredByKeyword(value);
                    scenesListFilter.filteredByElasticsSearch(value);
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

    @Override
    public SceneResponse editScene(EditSceneRequest req, long userId) throws Exception {
        long sceneId = req.getId();
        if (!sceneDao.findBySceneIdAndSceneIsDeleted(sceneId, 0).isPresent())
            throw new ExpectedException400("找不到对应场景");
        Scene existingScene = sceneDao.findBySceneIdAndSceneIsDeleted(sceneId, 0).get();
        if (!existingScene.isSceneAssist(userId) && userId != existingScene.getSceneCreatorId())
            throw new ExpectedException403("非场景管理员，无权限");
        String[] argumentsList = {"name", "url", "description", "isPublic", "tags", "collaborators", "cacheImageUrl", "birthPosition", "birthRotation", "audioUrl"};
        Iterator<String> iterator = Arrays.stream(argumentsList).iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            switch (key) {
                case "name":
                    String name = req.getName();
                    if (name == null) break;
                    else if (name.length() > 50) throw new ExpectedException400("场景名过长");
                    existingScene.setSceneName(name);
                    break;
                case "url":
                    String url = req.getUrl();
                    UrlValidator urlValidator = new UrlValidator();
                    if (url == null) break;
                    else if (!urlValidator.isValid(url)) throw new ExpectedException400("场景URL不合法");
                    existingScene.setSceneUrl(url);
                    break;
                case "description":
                    String description = req.getDescription();
                    if (description == null) break;
                    else if (description.length() > 500) throw new ExpectedException400("场景描述过长");
                    existingScene.setSceneDescription(description);
                    break;
                case "isPublic":
                    Boolean isPublic = req.getIsPublic();
                    if (isPublic == null) break;
                    else if (isPublic) existingScene.setSceneIsPublic(1);
                    else existingScene.setSceneIsPublic(0);
                    break;
                case "tags":
                    List<String> tags = req.getTags();
                    if (tags == null) break;
                    else {
                        sceneService.editSceneTags(sceneId, tags);
                    }
                    break;
                case "collaborators":
                    List<Integer> collaborators = req.getCollaborators();
                    if (collaborators == null) break;
                    else if (sceneDao.findBySceneId(sceneId).get().getSceneCreatorId() != userId)
                        throw new ExpectedException403("非场景创建者，无权限");
                    List<Long> list = new ArrayList<>();
                    for (Integer i : collaborators) {
                        list.add((long) i);
                    }
                    sceneService.insertCollaborators(existingScene, list);
                    break;
                case "cacheImageUrl":
                    String finalUrl = null;
                    String jpgFileString = req.getCacheImageUrl();
                    if (jpgFileString == null) break;
                    File jpgFile = new File(jpgFileString);
                    if (!jpgFile.exists()) throw new ExpectedException404("找不到该缓存图片");
                    if (jpgFileString.startsWith("cache/sceneCover")) {
                        if (!sceneDao.findBySceneIdAndSceneIsDeleted(sceneId, 0).get().isSceneAssist(userId) && sceneDao.findBySceneIdAndSceneIsDeleted(sceneId, 0).get().getSceneCreatorId() != userId)
                            throw new ExpectedException403("非场景管理员，无权限");
                        finalUrl = tencentCloudService.uploadSceneCover(jpgFile, sceneId);

                        SceneCoverImage sceneCoverImage = sceneCoverImageDao.findById(sceneId).orElse(null);
                        if (sceneCoverImage == null) {
                            sceneCoverImage = new SceneCoverImage();
                            sceneCoverImage.setSceneId(sceneId);
                        }
                        sceneCoverImage.setUrl(finalUrl);
                        sceneCoverImage.setUploadDate(LocalDateTime.now());
                        sceneCoverImageDao.save(sceneCoverImage);

                        existingScene.setSceneCoverUrl(finalUrl);
                    } else {
                        throw new ExpectedException400("仅支持上传场景封面类型");
                    }
                    break;
                case "birthPosition":
                    Vector3 birthpositionVector3 = req.getBirthPosition();
                    if (birthpositionVector3 == null) break;
                    else {
                        sceneService.editBirthPositionVector3(sceneId, birthpositionVector3);
                    }
                    break;
                case "birthRotation":
                    Vector3 birthRotationVector3 = req.getBirthRotation();
                    if (birthRotationVector3 == null) break;
                    else {
                        sceneService.editBirthRotationVector3(sceneId, birthRotationVector3);
                    }
                    break;
                case "audioUrl"://修改音频
                    String filePath = req.getAudioUrl();
                    if (filePath == null) break;
                    Path outputFile = Paths.get(filePath);
                    File newAudioFile = new File(outputFile.toString());
                    String finalUrl2 = tencentCloudService.sceneUploadAudio(newAudioFile, sceneId);
                    existingScene.setAudioUrl(finalUrl2);
                    break;
                default:
                    break;
            }
        }
        sceneDao.save(existingScene);
        SceneResponse resp = new SceneResponse();
        resp.setScene(sceneDao.findBySceneIdAndSceneIsDeleted(sceneId, 0).get());
        return resp;
    }

    @Override
    public AnnotationResponse createAnnotation(AnnotationRequest request, long userId) throws Exception {
        SceneAnnotation sceneAnnotation = new SceneAnnotation();
        SceneAnnotationTransmission sceneAnnotationTransmission1 = new SceneAnnotationTransmission();
        SceneAnnotationIntroduction sceneAnnotationIntroduction = new SceneAnnotationIntroduction();
        AnnotationData annotationData = new AnnotationData();
        SceneAnnotationNavigation sceneAnnotationNavigation = new SceneAnnotationNavigation();
        long sceneId = request.getSceneId();
        if (!sceneDao.findBySceneIdAndSceneIsDeleted(sceneId, 0).isPresent())
            throw new ExpectedException400("找不到对应场景");
        Scene scene = sceneDao.findBySceneIdAndSceneIsDeleted(sceneId, 0).get();
        if (!scene.isSceneAssist(userId) && scene.getSceneCreatorId() != userId)
            throw new ExpectedException403("非场景管理员，无权限");
        sceneAnnotation.setSceneId(request.getSceneId());
        sceneAnnotation.setName(request.getName());
        //TODO 处理非法type异常
        sceneAnnotation.setType(request.getAnnotationData().getType());
        String color = request.getColor();
        if (!color.matches("^#[0-9A-Fa-f]{6}$")) throw new ExpectedException400("非法颜色字符串");
        sceneAnnotation.setColor(request.getColor());
        sceneAnnotation.setRadius(request.getRadius().doubleValue());
        double positionX = request.getPositionX().doubleValue();
        double positionY = request.getPositionY().doubleValue();
        double positionZ = request.getPositionZ().doubleValue();
        if (sceneAnnotationDao.findByPositionXAndPositionYAndPositionZ(positionX, positionY, positionZ).isPresent()) {
            throw new ExpectedException400("该位置上已存在打点标记");
        }
        // 不带toSceneId抛出错误
        if (request.getAnnotationData().getType().equals("transmission") && request.getAnnotationData().getToSceneId() == null) {
            throw new ExpectedException400("参数toSceneId缺失");
        }
        sceneAnnotation.setPositionX(positionX);
        sceneAnnotation.setPositionY(positionY);
        sceneAnnotation.setPositionZ(positionZ);
        sceneAnnotationDao.save(sceneAnnotation);
        // 将对应属性值赋给目标对象
        long annotationId = sceneAnnotation.getAnnotationId();
        switch (AnnotationType.fromValue(request.getAnnotationData().getType())) {
            case INTRODUCTION:
                BeanUtils.copyProperties(request.getAnnotationData(), sceneAnnotationIntroduction);
                BeanUtils.copyProperties(request.getAnnotationData(), annotationData);
                sceneAnnotationIntroduction.setAnnotationId(annotationId);
                // 上传音频到腾讯云
                if (request.getAnnotationData().getAudioUrl() != null) {
                    String filePath = request.getAnnotationData().getAudioUrl();
                    Path outputFile = Paths.get(filePath);
                    File audioFile = new File(outputFile.toString());
                    String finalUrl = tencentCloudService.uploadAudio(audioFile, annotationId);
                    sceneAnnotationIntroduction.setAudioUrl(finalUrl);
                    annotationData.setAudioUrl(finalUrl);
                    sceneAnnotationIntroductionDao.save(sceneAnnotationIntroduction);
                }
                // 上传图片到腾讯云
                if (request.getAnnotationData().getImageUrl() != null) {
                    String filePath = request.getAnnotationData().getImageUrl();
                    Path outputFile = Paths.get(filePath);
                    File jpgFile = new File(outputFile.toString());
                    String finalUrl = tencentCloudService.uploadImageToCOS(jpgFile, annotationId);
                    sceneAnnotationIntroduction.setImageUrl(finalUrl);
                    annotationData.setImageUrl(finalUrl);
                    sceneAnnotationIntroductionDao.save(sceneAnnotationIntroduction);
                    break;
                }
                // 上传视频到腾讯云
                if (request.getAnnotationData().getVideo() != null) {
                    String filePath = request.getAnnotationData().getVideo();
                    Path outputFile = Paths.get(filePath);
                    File videoFile = new File(outputFile.toString());
                    String finalUrl = tencentCloudService.uploadVideoToCOS(videoFile, annotationId);
                    sceneAnnotationIntroduction.setVideo(finalUrl);
                    annotationData.setVideo(finalUrl);
                    sceneAnnotationIntroductionDao.save(sceneAnnotationIntroduction);
                    break;
                }
                sceneAnnotationIntroductionDao.save(sceneAnnotationIntroduction);
                break;
            case TRANSMISSION:
                BeanUtils.copyProperties(request.getAnnotationData(), sceneAnnotationTransmission1);
                // 含目标场景不含目标传送点
                if (request.getAnnotationData().getToSceneId() != null &&
                        request.getAnnotationData().getToTransmissionAnnotationId() == null) {
                    sceneAnnotationTransmission1.setAnnotationId(annotationId);
                    sceneAnnotationTransmissionDao.save(sceneAnnotationTransmission1);
                    BeanUtils.copyProperties(request.getAnnotationData(), annotationData);
                    break;
                }
                // 含目标场景含目标传送点
                if (request.getAnnotationData().getToSceneId() != null &&
                        sceneAnnotationTransmission1.getToTransmissionAnnotationId() != null) {
                    sceneAnnotationTransmission1.setAnnotationId(annotationId);
                    sceneAnnotationTransmissionDao.save(sceneAnnotationTransmission1);
                    SceneAnnotationTransmission sceneAnnotationTransmission2 = sceneAnnotationTransmissionDao.findByAnnotationId(request.getAnnotationData().getToTransmissionAnnotationId());
                    sceneAnnotationTransmission2.setAnnotationId(sceneAnnotationTransmission1.getToTransmissionAnnotationId());
                    sceneAnnotationTransmission2.setToTransmissionAnnotationId(sceneAnnotationTransmission1.getAnnotationId());
                    sceneAnnotationTransmission2.setToSceneId(request.getSceneId());
                    sceneAnnotationTransmissionDao.save(sceneAnnotationTransmission2);
                    BeanUtils.copyProperties(request.getAnnotationData(), annotationData);
                    break;
                }
                break;
            case NAVIGATION:
                sceneAnnotationNavigation.setAnnotationId(annotationId);
                if (request.getAnnotationData().getLinePointList() != null && !request.getAnnotationData().getLinePointList().isEmpty()) {
                    JSONArray jsonArray = new JSONArray();
                    for (LinePointList linePointList : request.getAnnotationData().getLinePointList()) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("position_x", linePointList.getPosition_x());
                        jsonObject.put("position_y", linePointList.getPosition_y());
                        jsonObject.put("position_z", linePointList.getPosition_z());
                        jsonArray.add(jsonObject);
                    }
                    String jsonString = jsonArray.toJSONString();
                    sceneAnnotationNavigation.setLinePointList(jsonString);
                }
                sceneAnnotationNavigationDao.save(sceneAnnotationNavigation);
                annotationData.setLinePointList(request.getAnnotationData().getLinePointList());
                annotationData.setType("navigation");
                break;
            default:
        }
        AnnotationResponse resp = new AnnotationResponse();
        resp.setAnnotation(sceneAnnotationDao.findByPositionXAndPositionYAndPositionZ(positionX, positionY, positionZ).get());
        resp.setAnnotationData(annotationData);
        return resp;
    }

    @Override
    public ListAnnotationResponse getAnnotationsList(Integer sceneId, long userId) throws Exception {
        if (!sceneDao.findBySceneIdAndSceneIsDeleted(sceneId, 0).isPresent())
            throw new ExpectedException404("对应场景不存在");
        List<SceneAnnotation> annotationList = sceneAnnotationDao.findAllBySceneId((long) sceneId);
        List<AnnotationResponse> annotationResponseList = new ArrayList<>();
        int count = 0;
        for (SceneAnnotation annotation : annotationList) {
            AnnotationResponse annotationResponse = new AnnotationResponse();
            annotationResponse.setAnnotation(annotation);
            AnnotationData annotationData = setAnnotationData(annotation);
            annotationResponse.setAnnotationData(annotationData);
            annotationResponseList.add(annotationResponse);
            count++;
        }
        ListAnnotationResponse resp = new ListAnnotationResponse();
        resp.setTotalCount(count);
        resp.setData(annotationResponseList);
        return resp;
    }

    @Override
    public ListAnnotationResponse listOrder(List<Long> orderList) throws Exception {
        List<AnnotationResponse> annotationResponseList = new ArrayList<>();
        int count = 0;
        long orderId = 1;
        for (Long annotationId : orderList) {
            SceneAnnotation annotation = sceneAnnotationDao.findById(annotationId).get();
            annotation.setOrderId(orderId++);
            AnnotationResponse annotationResponse = new AnnotationResponse();
            annotationResponse.setAnnotation(annotation);
            AnnotationData annotationData = setAnnotationData(annotation);
            annotationResponse.setAnnotationData(annotationData);
            annotationResponseList.add(annotationResponse);
            count++;
        }
        ListAnnotationResponse resp = new ListAnnotationResponse();
        resp.setTotalCount(count);
        resp.setData(annotationResponseList);
        return resp;
    }

    public AnnotationResponse getAnnotationById(long annotationId) throws Exception {
        Optional<SceneAnnotation> annotationOptional = sceneAnnotationDao.findById(annotationId);
        if (!annotationOptional.isPresent()) throw new ExpectedException404("该标记不存在");

        SceneAnnotation annotation = annotationOptional.get();
        if (annotation.getIsDeleted() == 1) throw new ExpectedException404("该标记不存在");

        AnnotationResponse resp = new AnnotationResponse();
        resp.setAnnotation(annotation);
        AnnotationData annotationData = setAnnotationData(annotation);
        resp.setAnnotationData(annotationData);

        return resp;
    }

    public AnnotationData setAnnotationData(SceneAnnotation annotation) {
        String type = annotation.getType();
        AnnotationData annotationData = new AnnotationData();
        switch (AnnotationType.fromValue(type)) {
            case INTRODUCTION:
                SceneAnnotationIntroduction annotationIntroduction = sceneAnnotationIntroductionDao.findByAnnotationId(annotation.getAnnotationId());
                annotationData.setType(type);
                annotationData.setBriefIntroduction(annotationIntroduction.getBriefIntroduction());
                annotationData.setIntroduction(annotationIntroduction.getIntroduction());
                annotationData.setImageUrl(annotationIntroduction.getImageUrl());
                annotationData.setVideo(annotationIntroduction.getVideo());
                annotationData.setAudioUrl(annotationIntroduction.getAudioUrl());
                annotationData.setFocalPointViewX(annotationIntroduction.getFocalPointViewX());
                annotationData.setFocalPointViewY(annotationIntroduction.getFocalPointViewY());
                annotationData.setFocalPointViewZ(annotationIntroduction.getFocalPointViewZ());
                break;
            case NAVIGATION:
                SceneAnnotationNavigation annotationNavigation = sceneAnnotationNavigationDao.findByAnnotationId(annotation.getAnnotationId());
                annotationData.setType(type);
                List<LinePointList> list = JSONObject.parseArray(annotationNavigation.getLinePointList(), LinePointList.class);
                annotationData.setLinePointList(list);
                break;
            case TRANSMISSION:
                SceneAnnotationTransmission annotationTransmission = sceneAnnotationTransmissionDao.findByAnnotationId(annotation.getAnnotationId());
                annotationData.setType(type);
                annotationData.setToSceneId(annotationTransmission.getToSceneId());
                annotationData.setToTransmissionAnnotationId(annotationTransmission.getToTransmissionAnnotationId());
                break;
            default:
                break;
        }
        return annotationData;
    }

    @Override
    public void removeAnnotation(long annotationId, long userId) throws Exception {
        if (!sceneAnnotationDao.findById(annotationId).isPresent() || sceneAnnotationDao.findById(annotationId).get().getIsDeleted() == 1)
            throw new ExpectedException404("该标记不存在");
        SceneAnnotation annotation = sceneAnnotationDao.findById(annotationId).get();
        if (!sceneDao.findBySceneIdAndSceneIsDeleted(annotation.getSceneId(), 0).isPresent())
            throw new ExpectedException404("该标记所处场景可能已被删除");
        Scene scene = sceneDao.findBySceneIdAndSceneIsDeleted(annotation.getSceneId(), 0).get();
        if (!scene.isSceneAssist(userId) && scene.getSceneCreatorId() != userId)
            throw new ExpectedException403("非场景管理员，无权限");
        // 执行标记点假删除，将标记状态设置为已删除
        annotation.setIsDeleted(1);
        sceneAnnotationDao.save(annotation);
        // 判断传送点类型，对子表数据进行假删除
        String type = sceneAnnotationDao.getAnnotationType(annotationId);
        switch (AnnotationType.fromValue(type)) {
            case TRANSMISSION:
                sceneAnnotationTransmissionDao.updateByAnnotationId(annotationId);
                break;
            case INTRODUCTION:
                sceneAnnotationIntroductionDao.updateByAnnotationId(annotationId);
                break;
            case NAVIGATION:
                sceneAnnotationNavigationDao.updateByAnnotationId(annotationId);
                break;
            default:
        }

    }


    @Override
    public AnnotationResponse editAnnotation(EditAnnotationRequest req, long userId) throws Exception {
        long annotationId = req.getAnnotationId();
        if (!sceneAnnotationDao.findById(annotationId).isPresent() || sceneAnnotationDao.findById(annotationId).get().getIsDeleted() == 1)
            throw new ExpectedException400("该标记不存在");
        SceneAnnotation annotation = sceneAnnotationDao.findById(annotationId).get();
        if (!sceneDao.findBySceneIdAndSceneIsDeleted(annotation.getSceneId(), 0).isPresent())
            throw new ExpectedException400("该标记所处场景可能已被删除");
        Scene scene = sceneDao.findBySceneIdAndSceneIsDeleted(annotation.getSceneId(), 0).get();
        if (!scene.isSceneAssist(userId) && scene.getSceneCreatorId() != userId)
            throw new ExpectedException403("非场景管理员，无权限");
        SceneAnnotation existingAnnotation = sceneAnnotationDao.findById(annotationId).get();
        String[] argumentsList = {"name", "type", "color", "radius", "position_x", "position_y", "position_z",};
        Iterator<String> iterator = Arrays.stream(argumentsList).iterator();
        BigDecimal position_x = null;
        BigDecimal position_y = null;
        BigDecimal position_z = null;
        while (iterator.hasNext()) {
            String key = iterator.next();
            switch (key) {
                case "name":
                    String name = req.getName();
                    if (name == null) break;
                    else if (name.length() > 50) throw new ExpectedException400("标记名过长");
                    existingAnnotation.setName(name);
                    break;
                case "type":
                    //TODO 处理非法type异常
                    AnnotationType type = AnnotationType.fromValue(req.getAnnotationData().getType());
                    if (type == null) break;
                    existingAnnotation.setType(type.getValue());
                    break;
                case "color":
                    String color = req.getColor();
                    if (color == null) break;
                    if (!color.matches("^#[0-9A-Fa-f]{6}$")) throw new ExpectedException400("非法颜色字符串");
                    existingAnnotation.setColor(color);
                    break;
                case "radius":
                    BigDecimal radius = req.getRadius();
                    if (radius == null) break;
                    existingAnnotation.setRadius(radius.doubleValue());
                    break;
                case "position_x":
                    position_x = req.getPositionX();
                    if (position_x == null) break;
                    break;
                case "position_y":
                    position_y = req.getPositionY();
                    if (position_y == null) break;
                    break;
                case "position_z":
                    position_z = req.getPositionZ();
                    if (position_z == null) break;
                    break;
                default:
                    break;
            }
        }
        if (position_x != null && position_y != null && position_z != null) {
            double positionX = position_x.doubleValue();
            double positionY = position_y.doubleValue();
            double positionZ = position_z.doubleValue();
            Optional<SceneAnnotation> sceneAnnotationOptional = sceneAnnotationDao.findByPositionXAndPositionYAndPositionZ(positionX, positionY, positionZ);
            if (sceneAnnotationOptional.isPresent()) {
                SceneAnnotation sceneAnnotation = sceneAnnotationOptional.get();
                if (sceneAnnotation.getAnnotationId() != req.getAnnotationId())
                    throw new ExpectedException400("该位置上已存在打点标记");
            }
            existingAnnotation.setPositionX(positionX);
            existingAnnotation.setPositionY(positionY);
            existingAnnotation.setPositionZ(positionZ);
        }
        // 更新对应子表
        AnnotationData annotationData = updateSonTable(req, annotationId);
        sceneAnnotationDao.save(existingAnnotation);
        AnnotationResponse resp = new AnnotationResponse();
        resp.setAnnotation(sceneAnnotationDao.findById(annotationId).get());
        resp.setAnnotationData(annotationData);
        return resp;
    }

    // 更新子表
    public AnnotationData updateSonTable(EditAnnotationRequest request, long annotationId) throws Exception {
        AnnotationData annotationData = new AnnotationData();
        String type = sceneAnnotationDao.getAnnotationType(annotationId);
        switch (AnnotationType.fromValue(type)) {
            case TRANSMISSION:
                // 含目标场景不含目标传送点
                if (request.getAnnotationData().getToSceneId() != null &&
                        request.getAnnotationData().getToTransmissionAnnotationId() == null) {
                    SceneAnnotationTransmission existingAnnotationTransmission = sceneAnnotationTransmissionDao.findByAnnotationId(annotationId);
                    existingAnnotationTransmission.setToSceneId(request.getAnnotationData().getToSceneId());
                    existingAnnotationTransmission.setToTransmissionAnnotationId(request.getAnnotationData().getToTransmissionAnnotationId());
                    sceneAnnotationTransmissionDao.save(existingAnnotationTransmission);
                    BeanUtils.copyProperties(existingAnnotationTransmission, annotationData);
                    break;
                }
                // 含目标场景含目标传送点
                if (request.getAnnotationData().getToSceneId() != null &&
                        request.getAnnotationData().getToTransmissionAnnotationId() != null) {
                    SceneAnnotationTransmission existingAnnotationTransmission1 = sceneAnnotationTransmissionDao.findByAnnotationId(annotationId);
                    SceneAnnotationTransmission existingAnnotationTransmission2 = sceneAnnotationTransmissionDao.findByAnnotationId(request.getAnnotationData().getToTransmissionAnnotationId());
                    SceneAnnotation annotation = sceneAnnotationDao.findById(annotationId).get();
                    existingAnnotationTransmission1.setToSceneId(request.getAnnotationData().getToSceneId());
                    existingAnnotationTransmission1.setToTransmissionAnnotationId(request.getAnnotationData().getToTransmissionAnnotationId());
                    existingAnnotationTransmission2.setToSceneId(annotation.getSceneId());
                    existingAnnotationTransmission2.setToTransmissionAnnotationId(annotationId);
                    sceneAnnotationTransmissionDao.save(existingAnnotationTransmission1);
                    sceneAnnotationTransmissionDao.save(existingAnnotationTransmission2);
                    BeanUtils.copyProperties(existingAnnotationTransmission1, annotationData);
                    break;
                }
                break;
            case INTRODUCTION:
                String[] emptyKey = getNullPropertyNames(request);
                SceneAnnotationIntroduction existingAnnotationIntroduction = sceneAnnotationIntroductionDao.findByAnnotationId(annotationId);
                BeanUtils.copyProperties(request.getAnnotationData(), existingAnnotationIntroduction, emptyKey);
                BeanUtils.copyProperties(request.getAnnotationData(), annotationData);
                // 上传音频到腾讯云
                if (request.getAnnotationData().getAudioUrl() != null) {
                    if (!request.getAnnotationData().getAudioUrl().equals(existingAnnotationIntroduction.getAudioUrl())) {
                        String filePath = request.getAnnotationData().getAudioUrl();
                        Path outputFile = Paths.get(filePath);
                        File audioFile = new File(outputFile.toString());
                        String finalUrl = tencentCloudService.uploadAudio(audioFile, annotationId);
                        existingAnnotationIntroduction.setAudioUrl(finalUrl);
                        annotationData.setAudioUrl(finalUrl);
                        sceneAnnotationIntroductionDao.save(existingAnnotationIntroduction);
                    }
                }
                // 上传图片到腾讯云
                if (request.getAnnotationData().getImageUrl() != null) {
                    if (!request.getAnnotationData().getImageUrl().equals(existingAnnotationIntroduction.getImageUrl())) {
                        String filePath = request.getAnnotationData().getImageUrl();
                        Path outputFile = Paths.get(filePath);
                        File jpgFile = new File(outputFile.toString());
                        String finalUrl = tencentCloudService.uploadImageToCOS(jpgFile, annotationId);
                        existingAnnotationIntroduction.setImageUrl(finalUrl);
                        annotationData.setImageUrl(finalUrl);
                        sceneAnnotationIntroductionDao.save(existingAnnotationIntroduction);
                    }
                }
                // 上传视频到腾讯云
                if (request.getAnnotationData().getVideo() != null) {
                    if (!request.getAnnotationData().getVideo().equals(existingAnnotationIntroduction.getVideo())) {
                        String filePath = request.getAnnotationData().getVideo();
                        Path outputFile = Paths.get(filePath);
                        File videoFile = new File(outputFile.toString());
                        String finalUrl = tencentCloudService.uploadVideoToCOS(videoFile, annotationId);
                        existingAnnotationIntroduction.setVideo(finalUrl);
                        annotationData.setVideo(finalUrl);
                        sceneAnnotationIntroductionDao.save(existingAnnotationIntroduction);
                    }
                }
                sceneAnnotationIntroductionDao.save(existingAnnotationIntroduction);
                break;
            case NAVIGATION:
                SceneAnnotationNavigation existingAnnotationNavigation = sceneAnnotationNavigationDao.findByAnnotationId(annotationId);
                if (request.getAnnotationData().getLinePointList() != null && !request.getAnnotationData().getLinePointList().isEmpty()) {
                    JSONArray jsonArray = new JSONArray();
                    for (LinePointList linePointList : request.getAnnotationData().getLinePointList()) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("position_x", linePointList.getPosition_x());
                        jsonObject.put("position_y", linePointList.getPosition_y());
                        jsonObject.put("position_z", linePointList.getPosition_z());
                        jsonArray.add(jsonObject);
                    }
                    String jsonString = jsonArray.toJSONString();
                    existingAnnotationNavigation.setLinePointList(jsonString);
                }
                sceneAnnotationNavigationDao.save(existingAnnotationNavigation);
                annotationData.setLinePointList(request.getAnnotationData().getLinePointList());
                annotationData.setType("navigation");
                break;
        }
        return annotationData;
    }

    // 忽略空数据
    public String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<String>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    @Override
    public CoverResponse downloadImage(long sceneId) throws Exception {
        CoverResponse coverResponse = new CoverResponse();
        Scene scene = sceneDao.findBySceneIdAndSceneIsDeleted(sceneId, 0).orElse(null);
        if (scene == null) throw new ExpectedException404("对应场景不存在");
        SceneCoverImage sceneCoverImage = sceneCoverImageDao.findById(sceneId).orElse(null);
        if (sceneCoverImage == null) throw new ExpectedException404("该场景无封面");
        coverResponse.setUrl(sceneCoverImage.getUrl());
        coverResponse.setAuthor(userDao.findById(scene.getSceneCreatorId()).get().getUserName());
        coverResponse.setTitle(scene.getSceneName());
        return coverResponse;
    }

    @Override
    public SceneResponse removeCollaborator(CollabotatorRequest request, long userId) throws Exception {
        //TODO 当用户非场景管理员时，抛出无权限异常
        List<Long> list = new ArrayList<>();
        for (Integer i : request.getTargetCollaborators()) {
            list.add((long) i);
        }
        return sceneService.removeCollaborator((long) request.getSceneId(), list);
    }

    @Override
    public void insertCollaborators(CollabotatorRequest request, long userId) throws Exception {
        SceneResponse resp = new SceneResponse();
        List<Long> list = new ArrayList<>();
        for (Integer i : request.getTargetCollaborators()) {
            list.add((long) i);
        }
        sceneService.insertCollaborators((long) request.getSceneId(), list);
    }

    @Override
    public TagListResponse addTagList(TagListRequest tagListRequest) {
        List<Tags> addedTags = new ArrayList<>();
        TagListResponse tagListResponse = new TagListResponse();
        for (String tagName : tagListRequest.getTags()) {
            Tags tag = new Tags();
            tag.setTagName(tagName);
            Tags newTag = tagsDao.save(tag);

            // Create a new TagsModel to add to the response
            TagsModel tagsModel = new TagsModel();
            tagsModel.setTagId((int) newTag.getTagId());
            tagsModel.setTagName(newTag.getTagName());

            tagListResponse.addTagsListItem(tagsModel);
        }
        return tagListResponse;
    }

    @Override
    public TagListResponse getTagListByName(String tagName) throws ExpectedException404 {
//        String tagName = tagsFindRequest.getTagName();
        TagListResponse tagListResponse = new TagListResponse();
        List<Tags> tagList = tagsDao.findByIsDeletedAndTagNameContaining(0, tagName);
        if (tagList.isEmpty()) return tagListResponse;
        for (Tags tag : tagList) {
            TagsModel tagsModel = new TagsModel();
            tagsModel.setTagId((int) tag.getTagId());
            tagsModel.setTagName(tag.getTagName());
            tagListResponse.addTagsListItem(tagsModel);
        }
        return tagListResponse;
    }

    @Override
    public void deleteTagList(DeleteTagListRequest deleteTagListRequest) throws ExpectedException404 {

        List<TagId> tagIdList = deleteTagListRequest.getTagList();
        for (TagId tagId : tagIdList) {
            Tags tag = tagsDao.findById((long) tagId.getId()).orElse(null);
            if (tag != null) {
                tagsDao.softDeleteTag(tag.getTagId());
            } else {
                throw new ExpectedException404("tag:" + tagId.getId() + "不存在");
            }
        }
        return;
    }

    public ListCarouselImageResponse getCarouselImagesList(NativeWebRequest request) throws Exception {
        ListCarouselImageResponse resp = new ListCarouselImageResponse();
        List<HomepageCarouselImages> carouselImagesList = homepageCarouselImagesDao.findAll();
        carouselImagesList = carouselImagesList.stream().filter(e -> e.getIsDeleted() == null || e.getIsDeleted() == 0).collect(Collectors.toList());
        resp.setTotalCount(carouselImagesList.size());
        Iterator<String> iterator = request.getParameterNames();
        int first = 10;
        while (iterator.hasNext()) {
            String key = iterator.next();
            String value = request.getParameter(key);
            if (value == null) continue;
            switch (key) {
                case "first":
                    first = Integer.parseInt(value);
                    break;
                case "after":
                    carouselImagesList = carouselImagesList.stream().filter((carouselImages) -> {
                        return carouselImages.getHomepageCarouselImagesId() > Integer.parseInt(value);
                    }).collect(Collectors.toList());
                    break;
                default:
                    break;
            }
        }

        int finalFirst = first;
        List<HomepageCarouselImages> finalCarouselImagesList = carouselImagesList;
        carouselImagesList = carouselImagesList.stream().filter((carouselImages) -> finalCarouselImagesList.indexOf(carouselImages) < finalFirst).collect(Collectors.toList());


        List<CarouselImageResponse> carouselList = new ArrayList<CarouselImageResponse>();
        for (HomepageCarouselImages carousel : carouselImagesList) {
            CarouselImageResponse coverResponse = new CarouselImageResponse();
            coverResponse.setCreatorId(carousel.getCreatorId());
            coverResponse.setCaption(carousel.getCaption());
            coverResponse.setUrl(carousel.getImageUrl());
            coverResponse.setSceneId(carousel.getSceneId());
            coverResponse.setCarouselImageId(carousel.getHomepageCarouselImagesId());
            coverResponse.setOrderIndex(carousel.getOrderIndex());
            carouselList.add(coverResponse);
        }
        resp.setData(carouselList);
        return resp;
    }

    public AddCarouselImageResponse addCarouselImagesList(AddCarouselImageRequest addCarouselImageRequest) {
        List<CarouselImage> carouselImageList = addCarouselImageRequest.getCarouselImageList();
        AddCarouselImageResponse resp = new AddCarouselImageResponse();
        List<CarouselImageResponse> respList = resp.getCarouselImageList();
        for (CarouselImage carouselImage : carouselImageList) {
            HomepageCarouselImages homepageCarouselImages = new HomepageCarouselImages();
            homepageCarouselImages.setCreatorId((long) carouselImage.getCreatorId());
            homepageCarouselImages.setImageUrl(carouselImage.getUrl());
            homepageCarouselImages.setCaption(carouselImage.getCaption());
            homepageCarouselImages.setOrderIndex(carouselImage.getOrderIndex());
            homepageCarouselImages.setSceneId((long) carouselImage.getSceneId());
//            homepageCarouselImages .setCreatedTime(LocalDateTime.now());
            HomepageCarouselImages saved = homepageCarouselImagesDao.save(homepageCarouselImages);
            CarouselImageResponse carouselImageResponse = new CarouselImageResponse();
            BeanUtils.copyProperties(saved, carouselImageResponse);
            carouselImageResponse.setCarouselImageId(saved.getHomepageCarouselImagesId());
            carouselImageResponse.setUrl(saved.getImageUrl());
            respList.add(carouselImageResponse);
        }
        resp.setCarouselImageList(respList);
        return resp;
    }

    @Override
    public int deleteCarouselImage(Long carouselImageId) {
        return homepageCarouselImagesDao.softDeleteCarouselImagesById(carouselImageId);
    }

    @Override
    public HomepageCarouselImages editCarouselImage(HomepageCarouselImages request, long userId) throws Exception {
        request.setCreatorId(userId);
        return homepageCarouselImagesDao.save(request);

    }

    @Override
    public LikedResponse likeScene(LikeSceneRequest likeSceneRequest, long userId) throws Exception {
        if (!sceneDao.findBySceneId(likeSceneRequest.getSceneId()).isPresent())
            throw new ExpectedException404("找不到对应场景");
        LikeScene likeScene = new LikeScene(likeSceneRequest.getSceneId(), userId);
        switch (likeSceneRequest.getOperationType()) {
            case LIKE:
                likeScene.like();
                likeSceneDao.save(likeScene);
                //jedisService.likeScene(likeSceneRequest.getSceneId(), userId);
                break;
            case DISLIKE:
                likeScene.dislike();
                likeSceneDao.save(likeScene);
                //jedisService.dislikeScene(likeSceneRequest.getSceneId(), userId);
                break;
            case READ:
                likeScene = likeSceneDao.findBySceneIdAndUserId(likeSceneRequest.getSceneId(), userId);
                if (likeScene == null) {
                    likeScene = new LikeScene(likeSceneRequest.getSceneId(), userId);
                    likeScene.dislike();
                    likeSceneDao.save(likeScene);
                }
                break;
            default:
                throw new ExpectedException400("不支持的操作类型");
        }
        LikedResponse resp = new LikedResponse();
        resp.setSceneId((int) likeScene.getSceneId());
        resp.setUserId((int) likeScene.getUserId());
        resp.setIsLiked(likeScene.isLiked());
        int sceneLikedCount = 0;
        for (LikeScene ls : likeSceneDao.findAllBySceneId(likeScene.getSceneId())) {
            if (ls.isLiked()) sceneLikedCount++;
        }
        resp.setSceneLikedCount(sceneLikedCount);
        return resp;
    }

    @Override
    public ListScenesResponse recommendScene(NativeWebRequest request, long userId) throws Exception {
        ListScenesResponse resp = new ListScenesResponse();

        List<Long> sceneIdList = new ArrayList<>();
        switch (request.getParameter("recommendType")) {
            case "userBasedRecommend":
                sceneIdList = sceneRecommenderService.userBasedRecommender(userId);
                break;
            case "itemBasedRecommend":
                String sceneId = request.getParameter("sceneId");
                if (sceneId == null) throw new ExpectedException400("itemBasedRecommend需要提供sceneId");
                if (!sceneDao.findBySceneIdAndSceneIsDeleted(Long.parseLong(sceneId), 0).isPresent())
                    throw new ExpectedException400("找不到对应场景");
                sceneIdList = sceneRecommenderService.itemBasedRecommend(Long.parseLong(sceneId));
                break;
            default:
                throw new ExpectedException404("不支持的type类型");
        }
        List<SceneResponse> sceneResponseList = new ArrayList<>();
        for (long sceneId : sceneIdList) {
            SceneResponse sceneResponse = new SceneResponse();
            sceneResponse.setScene(sceneDao.findBySceneId(sceneId).get());
            sceneResponseList.add(sceneResponse);
        }
        if (sceneIdList.size() < 6) {
            for (long sceneId : sceneRecommenderService.recommendByMostPopular(6 - sceneIdList.size())) {
                SceneResponse sceneResponse = new SceneResponse();
                sceneResponse.setScene(sceneDao.findBySceneId(sceneId).get());
                sceneResponseList.add(sceneResponse);
            }
        }

        resp.setTotalCount(BigDecimal.valueOf(sceneResponseList.size()));
        resp.setData(sceneResponseList);
        return resp;
    }


}
