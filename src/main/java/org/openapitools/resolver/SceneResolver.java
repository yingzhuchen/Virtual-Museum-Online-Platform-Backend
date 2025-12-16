package org.openapitools.resolver;

import org.openapitools.entity.HomepageCarouselImages;
import org.openapitools.exception.ExpectedException404;
import org.openapitools.model.*;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.List;

@Service
public interface SceneResolver {

    ListScenesResponse getSceneListByMachine(NativeWebRequest request, long userId, int isRecycleBin) throws Exception;

    SceneResponse createScene(CreateSceneRequest req, long userId) throws Exception;//创建场景

    SceneResponse getScene(long id, long userId) throws Exception;

    void deleteScene(long sceneId, long userId) throws Exception;

    void unDeleteScene(long sceneId, long userId) throws Exception;

    ListScenesResponse getScenesList(NativeWebRequest request, long userId) throws Exception;

    SceneResponse editScene(EditSceneRequest request, long userId) throws Exception;

    AnnotationResponse createAnnotation(AnnotationRequest request, long userId) throws Exception;

    ListAnnotationResponse getAnnotationsList(Integer sceneId, long userId) throws Exception;

    void removeAnnotation(long annotationId, long userId) throws Exception;

    AnnotationResponse editAnnotation(EditAnnotationRequest request, long userId) throws Exception;

    CoverResponse downloadImage(long sceneId) throws Exception;

    SceneResponse removeCollaborator(CollabotatorRequest request, long userId) throws Exception;

    void insertCollaborators(CollabotatorRequest request, long userId) throws Exception;

    //添加标签列表
    TagListResponse addTagList(TagListRequest tagListRequest);

    //查询标签
    TagListResponse getTagListByName(String tagName) throws ExpectedException404;

    //删除tag
    void deleteTagList(DeleteTagListRequest deleteTagListRequest) throws ExpectedException404;

    AddCarouselImageResponse addCarouselImagesList(AddCarouselImageRequest addCarouselImageRequest);

    int deleteCarouselImage(Long carouselImageId);

    HomepageCarouselImages editCarouselImage(HomepageCarouselImages request, long userId) throws Exception;

    LikedResponse likeScene(LikeSceneRequest likeSceneRequest,long userId) throws Exception;

    ListScenesResponse recommendScene(NativeWebRequest request,long userId) throws Exception;
    ListAnnotationResponse listOrder(List<Long> orderList) throws Exception;

}
