package org.openapitools.service;

import org.openapitools.dto.AnnotationData;
import org.openapitools.exception.ExpectedException;
import org.openapitools.exception.ExpectedException404;
import org.openapitools.model.*;
import org.openapitools.entity.Scene;

import java.util.List;


public interface SceneService {

    void insertCollaborators(long sceneId, List<Long> collaborators) throws Exception;//插入协作者列表

    Scene insertCollaborators(Scene scene, List<Long> collaborators) throws Exception;//插入协助者的重载

    List<Long> getCollaborators(long sceneId);//获取该场景的协作者列表

    SceneResponse removeCollaborator(long sceneId, List<Long> collaboratorIds);//删除协作者列表

    String addSceneCollaborators(long scene_id, List<Long> collaborators);//添加协作者列表
    List<Scene> getDeletedScenesIn30Days(long userId);//查询近30天的删除场景列表

    int delScene(Long sceneId);


    List<EsScene> databaseToElasticsearch();

    //添加tags
    List<String> editSceneTags(long sceneId, List<String> newTags) throws ExpectedException404;
    //修改左上角
    void editBirthPositionVector3(long sceneId, Vector3 topLeftVector3) throws ExpectedException;
    //修改右下角
    void editBirthRotationVector3(long sceneId, Vector3 bottomRightVector3) throws ExpectedException;

    AnnotationData getTransmissionDetails(long annotationId) throws Exception;

    AnnotationData getIntroductionDetails(long annotationId) throws Exception;

    AnnotationData getNavigationDetails(long annotationId) throws Exception;

    String getTypeById(long annotationId);

}
