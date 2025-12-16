package org.openapitools.service;

import org.openapitools.entity.Scene;
import org.openapitools.entity.Tags;
import org.openapitools.entity.User;
import org.openapitools.service.DatGeneratorOptimizer.DatGeneratorThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.openapitools.dao.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Service
public class ScheduleServiceImpl implements ScheduleService, DatGeneratorThread.CallBack{
    @Autowired ElasticsSearchServiceImpl elasticsSearchService;
    @Autowired SceneRecommendHandler sceneRecommendHandler;
    @Autowired UserDao userDao;
    @Autowired SceneDao sceneDao;
    @Autowired TagsDao tagsDao;
    private class CleanThread implements Callable<Boolean> {
        private String targetFile;
        private CleanThread(String targetFile){
            this.targetFile=targetFile;
        }
        @Override
        public Boolean call() {
            try {
                Thread.sleep(30*60*1000);//half hour
                File file=new File(targetFile);
                if(file.exists()) file.delete();
                return true;
            }
            catch (InterruptedException e) {
                System.err.println("Cancel clean mission");
                return false;
            }
        }
    }
    private static ExecutorService executorService=null;
    private static ExecutorService getExecutor(){
        if(executorService==null) executorService= Executors.newFixedThreadPool(100);
        return executorService;
    }

    @Override
    public Future<Boolean> startCleanThread(String targetFile){
        ExecutorService executor=ScheduleServiceImpl.getExecutor();
        Future<Boolean> future=executor.submit(new CleanThread(targetFile));
        return future;
    }
    @Override
    @Scheduled(cron = "0 0 0 * * ?")//0秒0分0时任意日任意月任意星期
    public void autoClean() throws Exception{//定期清理是必要的，以免CleanThread被异常打断，比如服务器关停维护等
        String dirPath="cache";
        File dir=new File(dirPath);
        File[] files=dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isFile() && (file.getName().endsWith(".jpg") || file.getName().endsWith(".mp4")|| file.getName().endsWith(".mp3"));
            }
        });
        for(File f:files){
            Path path=Paths.get("cache/"+f.getName());
            BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class);
            FileTime creationTime = attributes.creationTime();
            LocalDateTime fileCreationDateTime=LocalDateTime.ofInstant(creationTime.toInstant(), ZoneId.systemDefault());
            LocalDateTime currentDateTime=LocalDateTime.now();
            long diffInMinutes = ChronoUnit.MINUTES.between(fileCreationDateTime, currentDateTime);
            if(diffInMinutes>=30) f.delete();//当用户正在操作或近期操作时，不能删除
        }
    }
    @Override
    @Scheduled(cron = "0 0 0 * * ?")//0秒0分0时任意日任意月任意星期
    public void autoUpdateESDoc() throws Exception {
        elasticsSearchService.updateSceneDoc();
        elasticsSearchService.updateUserDoc();
    }
    @Override
    public void allocateMission(DatGeneratorThread thread) {
        int userAmountPerThread=10;
        int datGeneratorNum=thread.getDatGeneratorNum();
        thread.setMission(
                userAmountPerThread*datGeneratorNum
                ,userAmountPerThread*datGeneratorNum+userAmountPerThread
        );
    }
    private StringBuffer multiThreadGenerateBuf(String missionType) throws Exception {
        List<User> userList=userDao.findAll();
        int userAmountPerThread=10;
        int nThread=(userList.size()+userAmountPerThread-1)/userAmountPerThread;//向上取整
        ExecutorService executor=Executors.newFixedThreadPool(nThread);
        Phaser phaser=new Phaser(nThread+1);//nThread个子线程，以及1个当前线程
        List<Future<StringBuffer>> futureList=new ArrayList<>();
        List<Scene> sceneList=sceneDao.findAllBySceneIsDeleted(0);
        for(int i=0;i<nThread;i++){
            SceneRecommendHandler handler=sceneRecommendHandler.getSceneUnseenHandler();
            DatGeneratorThread datGenerator=new DatGeneratorThread(phaser,this,i);
            datGenerator.setRuntimeContext(sceneList,userList,handler,missionType);
            Future<StringBuffer> future=executor.submit(datGenerator);
            futureList.add(future);
        }
        while(phaser.getRegisteredParties()!=1){
            phaser.arriveAndAwaitAdvance();
        }
        StringBuffer resultBuf=new StringBuffer();
        for(int i=0;i<nThread;i++){
            StringBuffer perFutureBuf=futureList.get(i).get();
            resultBuf.append(perFutureBuf);
        }
        return resultBuf;
    }
    private void generateUBRecommendDat() throws Exception{
        StringBuffer resultBuf=multiThreadGenerateBuf("UserBasedRecommend");
        String dirPath = "dat";
        File dir = new File(dirPath);
        if (!dir.exists() || !dir.isDirectory()) dir.mkdir();
        File ubDat=new File("dat/UBRecommend.dat");
        FileWriter ubfw=new FileWriter(ubDat);
        ubfw.write(resultBuf.toString());
        ubfw.flush();
        ubfw.close();
    }
    private void generateIBRecommendDat() throws Exception{
        StringBuffer resultBuf=multiThreadGenerateBuf("ItemBasedRecommend");
        //对于IBRecommend.dat，还需要处理tag
        List<Tags> tagsList=tagsDao.findAll();
        List<Scene> sceneList=sceneDao.findAllBySceneIsDeleted(0);
        for(Tags tags:tagsList){
            long tagId=1000000+tags.getTagId();
            for(Scene scene:sceneList){
                List<String> tagNameList=scene.getTagsList();
                if(tagNameList.contains(tags.getTagName())) resultBuf.append(tagId+","+scene.getSceneId()+","+3.0+"\n");
                else resultBuf.append(tagId+","+scene.getSceneId()+","+0.0+"\n");
            }
            resultBuf.append("\n");
        }
        String dirPath = "dat";
        File dir = new File(dirPath);
        if (!dir.exists() || !dir.isDirectory()) dir.mkdir();
        File ibDat=new File("dat/IBRecommend.dat");
        FileWriter ibfw=new FileWriter(ibDat);
        ibfw.write(resultBuf.toString());
        ibfw.flush();
        ibfw.close();
    }
    @Override
    @Scheduled(cron = "0 0 0 * * ?")//0秒0分0时任意日任意月任意星期
    public void autoGenerateRecommendDat() throws Exception{
        Thread IBGenerator=new Thread(){
            @Override
            public void run(){
                try {
                    generateIBRecommendDat();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
        Thread UBGenerator=new Thread(){
            @Override
            public void run(){
                try {
                    generateUBRecommendDat();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
        IBGenerator.start();
        UBGenerator.start();
        IBGenerator.join();
        UBGenerator.join();
    }
    @Override
    public void UBDat() throws Exception{
        generateUBRecommendDat();
    }
    @Override
    public void IBDat() throws Exception{
        generateIBRecommendDat();
    }
}
