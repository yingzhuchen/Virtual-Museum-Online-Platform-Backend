package org.openapitools.service;

import org.openapitools.entity.SceneAnnotationIntroduction;
import java.util.List;

public interface SceneAnnotationIntroductionService {
    SceneAnnotationIntroduction createSceneAnnotationIntroduction(SceneAnnotationIntroduction request,long userId) throws Exception;

    List<SceneAnnotationIntroduction> getSceneAnnotationIntroductionList(SceneAnnotationIntroduction request,long userId) throws Exception;

    SceneAnnotationIntroduction getSceneAnnotationIntroductionById(long introductionId,long userId) throws Exception;

    void removeSceneAnnotationIntroduction(long introductionId,long userId) throws Exception;

    SceneAnnotationIntroduction editSceneAnnotationIntroduction(SceneAnnotationIntroduction request,long userId) throws Exception;





}
