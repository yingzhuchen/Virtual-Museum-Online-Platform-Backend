package org.openapitools.service;

import org.openapitools.entity.LikeScene;
import org.openapitools.entity.SceneCollect;
import org.openapitools.entity.SceneHistory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface JedisService {

    void saveSceneHistory(long userId,long sceneId) throws Exception;

    void saveSceneColllect(long sceneId,long userId) throws Exception;

    //获取after后的first条记录
    List<SceneHistory> getSceneHistory(int first,int after,long userId) throws Exception;

    List<SceneCollect> getSceneCollect(long userId) throws Exception;

    //每天凌晨0点将redis内存中的数据写入到MySQL数据库中
    void redisToMySQL() throws Exception;
    void createSceneFavourites(String newFavouritesName,long userId) throws Exception;
    void updateSceneFavourites(String existingFavouritesName,String newFavouritesName,long userId) throws Exception;
    List<String> readSceneFavourites(long userId) throws Exception;
    void deleteSceneFavourites(String existingFavouritesName,long userId) throws Exception;
    void pushFavoriteScene(String existingFavouritesName,long sceneId,long userId) throws Exception;
    void popFavoriteScene(String existingFavouritesName,long sceneId,long userId) throws Exception;
    List<Long> findAllFavoriteScene(String existingFavouritesName,long userId) throws Exception;
    void replaceFavoriteSceneList(String existingFavouritesName,List<Integer> sceneIdList,long userId) throws Exception;
    void likeScene(long sceneId,long userId) throws Exception;
    void dislikeScene(long sceneId,long userId) throws Exception;
    List<LikeScene> getLikeSceneList(long sceneId) throws Exception;
    List<Long> getMostPopularSceneList(int number) throws Exception;
}
