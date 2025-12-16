package org.openapitools.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SceneRecommenderService {
    List<Long> itemBasedRecommend(long sceneId) throws Exception;
    List<Long> userBasedRecommender(long userId) throws Exception;
    List<Long> recommendByMostPopular(int number) throws Exception;
}
