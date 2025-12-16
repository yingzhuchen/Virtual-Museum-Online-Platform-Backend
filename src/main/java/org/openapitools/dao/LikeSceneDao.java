package org.openapitools.dao;

import org.openapitools.configuration.RedisConfig;
import org.openapitools.entity.LikeScene;
import org.openapitools.exception.ExpectedException404;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class LikeSceneDao {
    @Autowired RedisConfig redisConfig;
    @Autowired SceneDao sceneDao;
    public List<LikeScene> findAllBySceneId(long sceneId){
        Jedis jedis=redisConfig.getJedisPool().getResource();
        List<LikeScene> listSceneList=new ArrayList<>();
        String key="sceneLike:"+"sceneId:"+sceneId;
        Set<String> members=jedis.smembers(key);
        for(String member:members){
            LikeScene likeScene=new LikeScene(member);
            listSceneList.add(likeScene);
        }
        jedis.close();
        return listSceneList;
    }
    public LikeScene findBySceneIdAndUserId(long sceneId,long userId){
        Jedis jedis=redisConfig.getJedisPool().getResource();
        if(!sceneDao.findBySceneId(sceneId).isPresent()) return null;
        String key="sceneLike:"+"sceneId:"+sceneId;
        Set<String> members=jedis.smembers(key);
        for(String member:members){
            LikeScene likeScene=new LikeScene(member);
            if(likeScene.getUserId()==userId) return likeScene;
        }
        return null;
    }
    public void save(LikeScene likeScene){
        Jedis jedis=redisConfig.getJedisPool().getResource();
        String key="sceneLike:"+"sceneId:"+likeScene.getSceneId();
        Set<String> members=jedis.smembers(key);
        for(String member:members){
            LikeScene memberLikeScene=new LikeScene(member);
            if(memberLikeScene.getUserId()==likeScene.getUserId()
                    &&memberLikeScene.getSceneId()==likeScene.getSceneId()){
                jedis.srem(key,member);
                break;
            }
        }
        jedis.sadd(key,likeScene.generateRedisValue());
        jedis.close();
    }
    public void delete(LikeScene likeScene){
        Jedis jedis=redisConfig.getJedisPool().getResource();
        String key="sceneLike:"+"sceneId:"+likeScene.getSceneId();
        Set<String> members=jedis.smembers(key);
        for(String member:members){
            LikeScene memberLikeScene=new LikeScene(member);
            if(memberLikeScene.getUserId()==likeScene.getUserId()
                    &&memberLikeScene.getSceneId()==likeScene.getSceneId()){
                jedis.srem(key,member);
                break;
            }
        }
        jedis.close();
    }
}
