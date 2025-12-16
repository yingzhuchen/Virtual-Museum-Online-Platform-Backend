package org.openapitools.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavoriteScene {
    private boolean isPlaceHolder;
    private long sceneId;
    private String favoriteTime;
    public FavoriteScene(long sceneId, String favoriteTime){
        this.isPlaceHolder=false;
        this.sceneId=sceneId;
        this.favoriteTime=favoriteTime;
    }
    public FavoriteScene(String redisVaule){
        if(redisVaule.equals("placeHolder")){
            this.isPlaceHolder=true;
            this.sceneId=-1;
            this.favoriteTime="null";
        }else{
            this.isPlaceHolder=false;
            this.sceneId=Long.parseLong(
                    redisVaule.substring(
                            redisVaule.indexOf(":")+1, redisVaule.indexOf(",")
                    )
            );
            this.favoriteTime= redisVaule.substring(
                    redisVaule.lastIndexOf(":")+1,
                    redisVaule.lastIndexOf("}")
            );
        }

    }
    public String generateRedisValue(){
        if(this.isPlaceHolder) return "placeHolder";
        return "{sceneId:"+this.sceneId+",favoriteTime:"+favoriteTime+"}";
    }
    public static FavoriteScene generatePlaceHolder(){
        return new FavoriteScene("placeHolder");
    }
    public boolean isPlaceHolder(){
        return this.isPlaceHolder;
    }
}
