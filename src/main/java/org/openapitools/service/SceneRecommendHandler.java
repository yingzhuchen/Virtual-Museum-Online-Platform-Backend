package org.openapitools.service;

import org.openapitools.dao.LikeSceneDao;
import org.openapitools.entity.LikeScene;
import org.openapitools.entity.SceneHistory;
import org.openapitools.model.AnnotationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
public class SceneRecommendHandler {
    @Autowired JedisServiceImpl jedisService;
    @Autowired LikeSceneDao likeSceneDao;
    //被@Autowired注解修饰的类前似乎不支持通过protected static关键字来在父子类之间传递信息...
    protected static JedisServiceImpl jedisServiceRuntimeInstance=null;
    protected static LikeSceneDao likeSceneDaoRuntimeInstance=null;
    protected enum RecommendType{
        UserBasedRecommend("UserBasedRecommend"),
        ItemBasedRecommend("ItemBasedRecommend");
        String value;
        RecommendType(String value) {
            this.value = value;
        }
        public static RecommendType fromValue(String value) {
            for (RecommendType recommendType : RecommendType.values()) {
                if (recommendType.value.equals(value)) {
                    return recommendType;
                }
            }
            throw new IllegalArgumentException("不支持的类型 '" + value + "'，仅支持UserBasedRecommend与ItemBasedRecommend");
        }
    }
    protected class Request{
        RecommendType recommendType;
        long sceneId;
        long userId;
        Request(long sceneId,long userId,String recommendType){
            this.sceneId=sceneId;
            this.userId=userId;
            this.recommendType=RecommendType.fromValue(recommendType);
        }
    }
    public Request generateRequest(long sceneId,long userId,String recommendType) throws Exception{
        if(this instanceof SceneUnseenHandler) return new Request(sceneId,userId,recommendType);
        else throw new IllegalStateException("内部错误：只应由子类SceneUnlikedHandler生成Request");
    }
    public SceneRecommendHandler getSceneUnseenHandler(){
        if(jedisServiceRuntimeInstance==null) jedisServiceRuntimeInstance=jedisService;
        if(likeSceneDaoRuntimeInstance==null) likeSceneDaoRuntimeInstance=likeSceneDao;
        return new SceneUnseenHandler();
    }
    protected SceneRecommendHandler nextHandler=null;
    //使用了@Autowired注解的类似乎不能是抽象类...
    public String handleRequest(Request request) throws Exception{
        throw new IllegalStateException("内部错误：方法尚未被子类重写，不应被调用，先调用getSceneUnseenHandler方法");
    };
}
class SceneUnseenHandler extends SceneRecommendHandler{//没看过
    @Override
    public String handleRequest(Request request) throws Exception {
        if(this.nextHandler==null) this.nextHandler=new SceneUnlikedHandler();
        boolean seen=false;
        List<SceneHistory> sceneHistoryList=jedisServiceRuntimeInstance.getSceneHistory(50,0,request.userId);
        for(SceneHistory sceneHistory:sceneHistoryList){
            if(sceneHistory.getSceneId()==request.sceneId){
                seen=true;
                break;
            }
        }
        if(seen){
            return nextHandler.handleRequest(request);
        }else{
            switch(request.recommendType){
                case UserBasedRecommend:
                    //基于用户不予评分
                    return "";
                case ItemBasedRecommend:
                    return request.userId+","+request.sceneId+","+0.0+"\n";
                default:
                    return null;
            }
        }
    }
}
class SceneUnlikedHandler extends SceneRecommendHandler{//看过但没点赞
    @Override
    public String handleRequest(Request request) throws Exception {
        if(this.nextHandler==null) this.nextHandler=new SceneLikedHandler();
        boolean liked=false;
        //List<LikeScene> likeSceneList=jedisServiceRuntimeInstance.getLikeSceneList(request.sceneId);
        List<LikeScene> likeSceneList=likeSceneDaoRuntimeInstance.findAllBySceneId(request.sceneId);
        for(LikeScene likeScene:likeSceneList){
            if(likeScene.getUserId()==request.userId&&(likeScene.isLiked())){
                liked=true;
                break;
            }
        }
        if(liked){
            return nextHandler.handleRequest(request);
        }else{
            return request.userId+","+request.sceneId+","+1.0+"\n";
        }
    }
}
class SceneLikedHandler extends SceneRecommendHandler{//点赞但没收藏
    @Override
    public String handleRequest(Request request) throws Exception {
        if(this.nextHandler==null) this.nextHandler=new SceneFavoriteHandler();
        boolean favorite=false;
        List<String> favouritesNameList=jedisServiceRuntimeInstance.readSceneFavourites(request.userId);
        for(String favouritesName:favouritesNameList){
            List<Long> sceneIdList=jedisServiceRuntimeInstance.findAllFavoriteScene(favouritesName,request.userId);
            for(Long sceneId:sceneIdList){
                if(sceneId==request.sceneId){
                    favorite=true;
                    break;
                }
            }
            if(favorite) break;
        }
        if(favorite){
            return nextHandler.handleRequest(request);
        }else{
            return request.userId+","+request.sceneId+","+2.0+"\n";
        }
    }
}
class SceneFavoriteHandler extends SceneRecommendHandler{//点赞且已收藏
    @Override
    public String handleRequest(Request request) throws Exception {
        return request.userId+","+request.sceneId+","+3.0+"\n";
    }
}