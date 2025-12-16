package org.openapitools.service;

import org.openapitools.configuration.RedisConfig;
import org.openapitools.dao.LikeSceneDao;
import org.openapitools.dao.SceneCollectDao;
import org.openapitools.dao.SceneDao;
import org.openapitools.dao.SceneHistoryDao;
import org.openapitools.entity.*;
import org.openapitools.exception.ExpectedException400;
import org.openapitools.exception.ExpectedException403;
import org.openapitools.exception.ExpectedException404;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.*;

@Service
public class JedisServiceImpl implements JedisService{//TODO Redis实现事务管理

    @Autowired
    RedisConfig redisConfig;

    @Autowired
    SceneHistoryDao sceneHistoryDao;

    @Autowired
    SceneCollectDao sceneCollectDao;

    @Autowired
    SceneDao sceneDao;

    @Autowired
    LikeSceneDao likeSceneDao;

    final int maxRecords = 50;

    @Override
    public void saveSceneHistory(long sceneId,long userId) throws Exception{
        Jedis jedis=redisConfig.getJedisPool().getResource();

        String key="sceneHistory:"+"user:"+userId;
        LocalDateTime now=LocalDateTime.now();
        //北京时间东八区
        Instant currentInstant=now.toInstant(ZoneOffset.ofHours(8));
        long timeStamp= currentInstant.getEpochSecond();
        String value="timeStamp:"+timeStamp+"sceneId:"+sceneId;
        jedis.zadd(key,timeStamp,value);

        jedis.close();
    }

    @Override
    public List<SceneHistory> getSceneHistory(int first,int after,long userId) throws Exception{
        Jedis jedis=redisConfig.getJedisPool().getResource();

        List<SceneHistory> ansList=new ArrayList<>();
        String key="sceneHistory:"+"user:"+userId;
        long currentRecords = jedis.zcard(key);
        if(after>=currentRecords) after=(int)currentRecords;
        int start=after;
        int end= Math.min(start+first-1,(int)currentRecords-1);
        Set<Tuple> records=jedis.zrevrangeWithScores(key,start,end);
        long i=-currentRecords+after;//id为负数表示是redis内的记录
        for(Tuple record:records){
            Instant instant = Instant.ofEpochSecond((long)record.getScore());
            ZoneId zoneId = ZoneId.systemDefault();
            LocalDateTime date = LocalDateTime.ofInstant(instant, zoneId);
            String element=record.getElement();
            String sceneId=element.substring(element.lastIndexOf(":")+1);
            SceneHistory history=new SceneHistory();
            history.setId(i);
            history.setUserId(userId);
            history.setSceneId(Long.parseLong(sceneId));
            history.setSceneAccessTime(date);
            ansList.add(history);
            i++;
        }

        jedis.close();
        return ansList;
    }

    @Override
    public void saveSceneColllect(long sceneId,long userId) throws Exception{
        Jedis jedis=redisConfig.getJedisPool().getResource();

        String key="sceneCollect:"+"user:"+userId;
        String value="sceneId:"+sceneId;
        jedis.sadd(key,value);

        jedis.close();
    }

    @Override
    public List<SceneCollect> getSceneCollect(long userId) throws Exception{
        Jedis jedis=redisConfig.getJedisPool().getResource();

        List<SceneCollect> ansList=new ArrayList<>();
        String key="sceneCollect:"+"user:"+userId;
        long currentRecords = jedis.scard(key);
        Set<String> records=jedis.smembers(key);
        long i=-currentRecords;//id为负数表示是redis内的记录
        for(String record:records){
            SceneCollect collect=new SceneCollect();
            collect.setId(i);
            collect.setUserId(userId);
            collect.setSceneId(Long.parseLong(record));
            ansList.add(collect);
            i++;
        }

        jedis.close();
        return ansList;
    }

    @Override
    @Scheduled(cron = "0 0 0 * * ?")//0秒0分0时任意日任意月任意星期
    public void redisToMySQL() throws Exception {
        Jedis jedis=redisConfig.getJedisPool().getResource();

        //用于执行keys命令的关键字
        String keyForKeys="sceneHistory:user:*";
        Set<String> keys=jedis.keys(keyForKeys);
        for(String key:keys){
            long currentRecords = jedis.zcard(key);
            if (currentRecords > maxRecords) {
                long recordsToRemove = currentRecords - maxRecords;
                Set<Tuple> records=jedis.zrangeWithScores(key,0,recordsToRemove-1);
                for(Tuple record:records){
                    Instant instant = Instant.ofEpochSecond((long)record.getScore());
                    ZoneId zoneId = ZoneId.systemDefault();
                    LocalDateTime date = LocalDateTime.ofInstant(instant, zoneId);
                    SceneHistory sceneHistory=new SceneHistory();
                    String userId=key.substring(key.lastIndexOf(":")+1);
                    sceneHistory.setUserId(Long.parseLong(userId));
                    String element=record.getElement();
                    String sceneId=element.substring(element.lastIndexOf(":")+1);
                    sceneHistory.setSceneId(Long.parseLong(sceneId));
                    sceneHistory.setSceneAccessTime(date);
                    sceneHistoryDao.save(sceneHistory);
                }
                jedis.zremrangeByRank(key, 0, recordsToRemove - 1);
            }
        }

        //用于执行keys命令的关键字
        keyForKeys="sceneCollect:user:*";
        keys=jedis.keys(keyForKeys);
        for(String key:keys){
            long currentRecords = jedis.scard(key);
            if (currentRecords > maxRecords) {
                long recordsToRemove = currentRecords - maxRecords;
                Set<String> records=jedis.smembers(key);
                long i=0;
                for(String sceneId:records){
                    if(!(i<recordsToRemove)) break;
                    SceneCollect sceneCollect=new SceneCollect();
                    String userId=key.substring(key.lastIndexOf(":")+1);
                    sceneCollect.setUserId(Long.parseLong(userId));
                    sceneCollect.setSceneId(Long.parseLong(sceneId));
                    sceneCollectDao.save(sceneCollect);
                    jedis.srem(key,sceneId);
                    i++;
                }
            }
        }

        jedis.close();
    }

    @Override
    public void createSceneFavourites(String newFavouritesName,long userId) throws Exception {
        Jedis jedis=redisConfig.getJedisPool().getResource();

        String key="sceneFavourites:"+"user:"+userId+":favouritesName:"+newFavouritesName;
        if(jedis.exists(key)) throw new ExpectedException400("收藏夹已存在");
        FavoriteScene placeHolder=FavoriteScene.generatePlaceHolder();//添加占位符
        jedis.sadd(key,placeHolder.generateRedisValue());

        jedis.close();
    }

    @Override
    public void updateSceneFavourites(String existingFavouritesName, String newFavouritesName,long userId) throws Exception {
        Jedis jedis=redisConfig.getJedisPool().getResource();

        String existingKey="sceneFavourites:"+"user:"+userId+":favouritesName:"+existingFavouritesName;
        String newKey="sceneFavourites:"+"user:"+userId+":favouritesName:"+newFavouritesName;
        if(!jedis.exists(existingKey)) throw new ExpectedException404("收藏夹不存在");
        else if(existingFavouritesName.equals("default")) throw new ExpectedException403("默认收藏夹不可更名");
        jedis.rename(existingKey,newKey);

        jedis.close();
    }

    @Override
    public List<String> readSceneFavourites(long userId) throws Exception {
        Jedis jedis=redisConfig.getJedisPool().getResource();
        List<String> ansList=new ArrayList<>();

        String keyForKeys="sceneFavourites:"+"user:"+userId+":favouritesName:*";
        Set<String> keys=jedis.keys(keyForKeys);
        for(String key:keys){
            ansList.add(key.substring(key.lastIndexOf(":")+1));
        }

        jedis.close();
        return ansList;
    }

    @Override
    public void deleteSceneFavourites(String existingFavouritesName,long userId) throws Exception {
        Jedis jedis=redisConfig.getJedisPool().getResource();

        String existingKey="sceneFavourites:"+"user:"+userId+":favouritesName:"+existingFavouritesName;
        if(!jedis.exists(existingKey)) throw new ExpectedException404("收藏夹不存在");
        else if(existingFavouritesName.equals("default")) throw new ExpectedException403("默认收藏夹不可删除");
        jedis.del(existingKey);

        jedis.close();
    }

    @Override
    public void pushFavoriteScene(String existingFavouritesName, long sceneId, long userId) throws Exception {
        Jedis jedis=redisConfig.getJedisPool().getResource();

        String existingKey="sceneFavourites:"+"user:"+userId+":favouritesName:"+existingFavouritesName;
        if(!jedis.exists(existingKey)) throw new ExpectedException404("收藏夹不存在");
        if(!sceneDao.findBySceneId(sceneId).isPresent()) throw new ExpectedException404("找不到对应场景");
        Scene scene=sceneDao.findBySceneId(sceneId).get();
        if(scene.getSceneIsPublic()==0&&scene.getSceneCreatorId()!=userId) throw new ExpectedException403("只有场景创建者能够收藏自己的私人场景");
        Set<String> members=jedis.smembers(existingKey);
        for(String member:members){
            FavoriteScene favoriteScene=new FavoriteScene(member);
            if(favoriteScene.getSceneId()==sceneId){
                throw new ExpectedException400("收藏夹中已有该场景");
            }
        }
        Date now=new Date();
        FavoriteScene favoriteScene=new FavoriteScene(sceneId,now.toString());
        jedis.sadd(existingKey,favoriteScene.generateRedisValue());

        jedis.close();
    }

    @Override
    public void popFavoriteScene(String existingFavouritesName, long sceneId, long userId) throws Exception {
        Jedis jedis=redisConfig.getJedisPool().getResource();

        String existingKey="sceneFavourites:"+"user:"+userId+":favouritesName:"+existingFavouritesName;
        if(!jedis.exists(existingKey)) throw new ExpectedException404("收藏夹不存在");
        Set<String> members=jedis.smembers(existingKey);
        boolean found=false;
        for(String member:members){
            FavoriteScene favoriteScene=new FavoriteScene(member);
            if(favoriteScene.getSceneId()==sceneId){
                found=true;
                jedis.srem(existingKey,member);
                break;
            }
        }
        if(!found) throw new ExpectedException404("没有收藏过该场景");

        jedis.close();
    }

    @Override
    public void replaceFavoriteSceneList(String existingFavouritesName, List<Integer> sceneIdList, long userId) throws Exception {
        Jedis jedis=redisConfig.getJedisPool().getResource();

        String existingKey="sceneFavourites:"+"user:"+userId+":favouritesName:"+existingFavouritesName;
        if(!jedis.exists(existingKey)) throw new ExpectedException404("收藏夹不存在");
        if(sceneIdList.size()!=sceneIdList.stream().distinct().count()) throw new ExpectedException400("场景列表包含重复项");
        Set<String> members=jedis.smembers(existingKey);
        for(String member:members){
            FavoriteScene favoriteScene=new FavoriteScene(member);
            if(!favoriteScene.isPlaceHolder()) jedis.srem(existingKey,member);
        }
        for(Integer sceneId:sceneIdList){
            if(!sceneDao.findBySceneId(sceneId).isPresent()) throw new ExpectedException404("找不到对应场景:"+sceneId);
            Scene scene=sceneDao.findBySceneId(sceneId).get();
            if(scene.getSceneIsPublic()==0&&scene.getSceneCreatorId()!=userId) throw new ExpectedException403("只有场景创建者能够收藏自己的私人场景:"+sceneId);
            Date now=new Date();
            FavoriteScene favoriteScene=new FavoriteScene(sceneId,now.toString());
            jedis.sadd(existingKey,favoriteScene.generateRedisValue());
        }

        jedis.close();
    }

    @Override
    public List<Long> findAllFavoriteScene(String existingFavouritesName, long userId) throws Exception {
        Jedis jedis=redisConfig.getJedisPool().getResource();
        List<Long> ansList=new ArrayList<>();

        String existingKey="sceneFavourites:"+"user:"+userId+":favouritesName:"+existingFavouritesName;
        if(!jedis.exists(existingKey)) throw new ExpectedException404("收藏夹不存在");
        Set<String> members=jedis.smembers(existingKey);
        for(String member:members){
            FavoriteScene favoriteScene=new FavoriteScene(member);
            if(!favoriteScene.isPlaceHolder()) ansList.add(favoriteScene.getSceneId());
        }

        jedis.close();
        return ansList;
    }

    @Override
    public void likeScene(long sceneId,long userId) throws Exception{
        Jedis jedis=redisConfig.getJedisPool().getResource();

        if(!sceneDao.findBySceneId(sceneId).isPresent()) throw new ExpectedException404("对应场景不存在");
        String key="sceneLike:"+"sceneId:"+sceneId;
        Set<String> members=jedis.smembers(key);
        for(String member:members){
            LikeScene likeScene=new LikeScene(member);
            if(likeScene.getUserId()==userId&&(!likeScene.isLiked())){
                likeScene.like();
                jedis.srem(key,member);
                jedis.sadd(key,likeScene.generateRedisValue());
            }
        }
        LikeScene newLikeScene=new LikeScene(sceneId,userId);
        jedis.sadd(key,newLikeScene.generateRedisValue());

        jedis.close();
    }

    @Override
    public void dislikeScene(long sceneId,long userId) throws Exception{
        Jedis jedis=redisConfig.getJedisPool().getResource();

        if(!sceneDao.findBySceneId(sceneId).isPresent()) throw new ExpectedException404("对应场景不存在");
        String key="sceneLike:"+"sceneId:"+sceneId;
        Set<String> members=jedis.smembers(key);
        for(String member:members){
            LikeScene likeScene=new LikeScene(member);
            if(likeScene.getUserId()==userId&&(likeScene.isLiked())){
                likeScene.dislike();
                jedis.srem(key,member);
                jedis.sadd(key,likeScene.generateRedisValue());
            }
        }

        jedis.close();
    }
    @Override
    public List<LikeScene> getLikeSceneList(long sceneId) throws Exception{
        Jedis jedis=redisConfig.getJedisPool().getResource();
        List<LikeScene> listSceneList=new ArrayList<>();

        if(!sceneDao.findBySceneId(sceneId).isPresent()) throw new ExpectedException404("对应场景不存在");
        String key="sceneLike:"+"sceneId:"+sceneId;
        Set<String> members=jedis.smembers(key);
        for(String member:members){
            LikeScene likeScene=new LikeScene(member);
            listSceneList.add(likeScene);
        }

        jedis.close();
        return listSceneList;
    }

    private class ScenePopularity{
        long sceneId;
        long popularity;
        ScenePopularity(long sceneId,long popularity){
            this.sceneId=sceneId;
            this.popularity=popularity;
        }
    }

    @Override
    public List<Long> getMostPopularSceneList(int number) throws Exception {
        //TODO 考虑收藏和历史浏览对热度的影响
        Jedis jedis=redisConfig.getJedisPool().getResource();

        PriorityQueue<ScenePopularity> mostPopularSceneList=new PriorityQueue<>(new Comparator<ScenePopularity>() {
            @Override
            public int compare(ScenePopularity s1, ScenePopularity s2) {
                return (int) (s2.popularity-s1.popularity);
            }
        });

        String keyForKeys="sceneLike:sceneId:*";
        Set<String> keys=jedis.keys(keyForKeys);
        for(String key:keys){
            long sceneId = Long.parseLong(key.substring(key.lastIndexOf(":")+1));
            long likeCount = 0;
            List<LikeScene> likeSceneList=likeSceneDao.findAllBySceneId(sceneId);
            //这里redis与mysql内的scene状况可能不一致，比如redis内标志存在该sceneId，但是mysql内却被删除，我们添加if语句专门处理这种情况
            if(!sceneDao.findBySceneId(sceneId).isPresent()) continue;
            for(LikeScene likeScene:likeSceneList){
                if(likeScene.isLiked()) likeCount++;
            }
            ScenePopularity newScenePopularity=new ScenePopularity(sceneId,likeCount);
            mostPopularSceneList.add(newScenePopularity);
        }

        //取热度最高的number个
        List<Long> ansList=new ArrayList<>();
        for(int i=0;i<number;i++){
            if(mostPopularSceneList.isEmpty()) break;
            ScenePopularity scenePopularity=mostPopularSceneList.poll();
            ansList.add(scenePopularity.sceneId);
        }
        jedis.close();
        return ansList;
    }
}
