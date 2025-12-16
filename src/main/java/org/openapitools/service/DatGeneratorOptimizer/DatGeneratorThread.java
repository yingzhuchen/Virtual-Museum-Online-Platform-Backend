package org.openapitools.service.DatGeneratorOptimizer;

import org.openapitools.dao.SceneDao;
import org.openapitools.dao.UserDao;
import org.openapitools.entity.Scene;
import org.openapitools.entity.User;
import org.openapitools.service.SceneRecommendHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Phaser;

public class DatGeneratorThread implements Callable<StringBuffer> {
    List<Scene> sceneList=null;
    List<User> userList=null;
    SceneRecommendHandler handler=null;
    public interface CallBack{
        void allocateMission(DatGeneratorThread thread);
    }
    private int datGeneratorNum;
    private int userStartIndex,userEndIndex;
    private String currentPhase;
    private String missionType;
    private StringBuffer missionBuf;
    private Phaser phaser;
    private DatGeneratorThread.CallBack caller;
    public DatGeneratorThread(Phaser phaser,CallBack caller,int datGeneratorNum){
        this.phaser=phaser;
        this.currentPhase="NOT_START_YET";
        this.caller=caller;
        this.datGeneratorNum=datGeneratorNum;
        this.missionBuf=new StringBuffer();
    }
    public DatGeneratorThread setRuntimeContext(List<Scene> sceneList,List<User> userList
            ,SceneRecommendHandler handler
            ,String missionType){
        this.sceneList=sceneList;
        this.userList=userList;
        this.handler=handler;
        this.missionType=missionType;
        if(!(missionType.equals("UserBasedRecommend")
                ||missionType.equals("ItemBasedRecommend"))
        ) throw new IllegalStateException("意料之外的任务");
        return this;
    }
    public int getDatGeneratorNum() {return datGeneratorNum;}
    public DatGeneratorThread setMission(int startIndex, int endIndex){
        this.userStartIndex=startIndex;
        this.userEndIndex=endIndex;
        return this;
    }
    @Override
    public StringBuffer call() throws Exception {
        switch(currentPhase){
            case "NOT_START_YET":
                currentPhase="MISSION_ALLOCATE";
            case "MISSION_ALLOCATE":
                caller.allocateMission(this);
                phaser.arriveAndAwaitAdvance();
                currentPhase="EXECUTE_MISSION";
            case "EXECUTE_MISSION":
                for(int userIndex=userStartIndex;userIndex<userEndIndex;userIndex++){
                    if(userIndex>=userList.size()) continue; //由于向上取整，所以这里会出现一些不存在的userIndex
                    for(Scene scene:sceneList){
                        String perSceneString=handler.handleRequest(
                                handler.generateRequest(
                                        scene.getSceneId()
                                        ,userList.get(userIndex).getUserId()
                                        ,missionType)
                        );
                        missionBuf.append(perSceneString);
                    }
                    missionBuf.append("\n");
                }
                phaser.arriveAndAwaitAdvance();
                currentPhase="COMMIT_RESULT";
            case "COMMIT_RESULT":
                phaser.arriveAndDeregister();
                return missionBuf;
            default:
                throw new IllegalStateException("no such phase");
        }
    }
}
