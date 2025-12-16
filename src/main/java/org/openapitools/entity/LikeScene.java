package org.openapitools.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeScene {
    private boolean isPlaceHolder;
    private long sceneId;
    private long userId;
    private boolean isLiked;
    public LikeScene(long sceneId, long userId){
        this.isPlaceHolder=false;
        this.sceneId=sceneId;
        this.userId=userId;
        this.isLiked=false;
    }
    public LikeScene(String redisVaule){
        if(redisVaule.equals("placeHolder")){
            this.isPlaceHolder=true;
            this.sceneId=-1;
            this.userId=-1;
            this.isLiked=false;
        }else{
            this.isPlaceHolder=false;
            JSONObject object=JSON.parseObject(redisVaule);
            this.sceneId=object.getLong("sceneId");
            this.userId=object.getLong("userId");
            this.isLiked=object.getBoolean("liked");
        }

    }
    public String generateRedisValue(){
        if(this.isPlaceHolder) return "placeHolder";
        return JSON.toJSONString(this);
    }
    public void like(){this.isLiked=true;}
    public void dislike(){this.isLiked=false;}
    public static LikeScene generatePlaceHolder(){
        return new LikeScene("placeHolder");
    }
    public boolean isPlaceHolder(){
        return this.isPlaceHolder;
    }
}
