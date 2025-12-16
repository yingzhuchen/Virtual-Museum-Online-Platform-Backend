package org.openapitools.service;

import lombok.extern.slf4j.Slf4j;
import org.openapitools.dao.SceneAnnotationIntroductionDao;
import org.openapitools.entity.SceneAnnotationIntroduction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Example;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@SpringBootApplication
@EnableScheduling
@org.springframework.transaction.annotation.Transactional
public class SceneAnnotationIntroductionServiceImpl implements SceneAnnotationIntroductionService {
    @Autowired
    SceneAnnotationIntroductionDao sceneAnnotationIntroductionDao;

    @Override
    public SceneAnnotationIntroduction createSceneAnnotationIntroduction(SceneAnnotationIntroduction request, long userId) throws Exception {
        return sceneAnnotationIntroductionDao.save(request);
    }

    @Override
    public List<SceneAnnotationIntroduction> getSceneAnnotationIntroductionList(SceneAnnotationIntroduction request, long userId) throws Exception {
        return sceneAnnotationIntroductionDao.findAll(Example.of(request));
    }

    @Override
    public SceneAnnotationIntroduction getSceneAnnotationIntroductionById(long introductionId, long userId) throws Exception {
        return sceneAnnotationIntroductionDao.findById(introductionId).orElse(null);
    }

    @Override
    public void removeSceneAnnotationIntroduction(long introductionId, long userId) throws Exception {
        sceneAnnotationIntroductionDao.deleteById(introductionId);
    }

    @Override
    public SceneAnnotationIntroduction editSceneAnnotationIntroduction(SceneAnnotationIntroduction request, long userId) throws Exception {
        Optional<SceneAnnotationIntroduction> byId = sceneAnnotationIntroductionDao.findById(request.getSceneAnnotationIntroductionId());
        if (!byId.isPresent()) {
            throw new IllegalArgumentException("id not exists");
        }
        SceneAnnotationIntroduction sceneAnnotationIntroduction = byId.get();
        if(request.getAnnotationId()!=0) {
            sceneAnnotationIntroduction.setAnnotationId(request.getAnnotationId());
        }
        if(request.getBriefIntroduction()!=null) {
            sceneAnnotationIntroduction.setBriefIntroduction(request.getBriefIntroduction());
        }
        if(request.getVideo()!=null) {
            sceneAnnotationIntroduction.setVideo(request.getVideo());
        }
        if(request.getImageUrl()!=null) {
            sceneAnnotationIntroduction.setImageUrl(request.getImageUrl());
        }
        if(request.getIntroduction()!=null) {
            sceneAnnotationIntroduction.setIntroduction(request.getIntroduction());
        }
        if(request.getFocalPointViewX()!=0.0) {
            sceneAnnotationIntroduction.setFocalPointViewX(request.getFocalPointViewX());
        }
        if(request.getFocalPointViewY()!=0.0) {
            sceneAnnotationIntroduction.setFocalPointViewY(request.getFocalPointViewY());
        }
        if(request.getFocalPointViewZ()!=0.0) {
            sceneAnnotationIntroduction.setFocalPointViewZ(request.getFocalPointViewZ());
        }

        return sceneAnnotationIntroductionDao.save(sceneAnnotationIntroduction);
    }
}
