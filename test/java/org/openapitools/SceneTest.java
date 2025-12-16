package org.openapitools;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openapitools.dao.*;
import org.openapitools.entity.Scene;
import org.openapitools.entity.Tags;
import org.openapitools.entity.User;
import org.openapitools.entity.SceneHistory;
import org.openapitools.model.*;
import org.openapitools.resolver.SceneResolver;
import org.openapitools.resolver.SceneResolverImpl;
import org.openapitools.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
//@Ignore
@Import(TestConfig.class)
@Slf4j
public class SceneTest {

    @Autowired
    private SceneDao sceneDao;

    @Autowired
    UserDao userDao;

    @Autowired
    SceneHistoryDao sceneHistoryDao;

    @Autowired
    TagsDao tagsDao;

    @Autowired
    private SceneServiceImpl sceneService;

    @Autowired
    SceneResolverImpl sceneResolver;

    @Autowired
    ElasticsSearchServiceImpl elasticsSearchService;

    @Autowired
    JedisServiceImpl jedisService;

    @Autowired
    SceneRecommendHandler sceneRecommendHandler;

    @Autowired
    ScheduleServiceImpl scheduleService;

    //@Ignore
    @Test
    public void testSceneHistoryDao(){
        Pageable pageable = PageRequest.of(0, 10);
        List<SceneHistory> list=sceneHistoryDao.findByUserId(8L,pageable);
        for(SceneHistory h:list){
            System.out.println(h.getId());
            System.out.println(h.getSceneId());
            System.out.println(h.getUserId());
            System.out.println(h.getSceneAccessTime());
        }
    }

    //@Ignore
    @Test
    public void testSearchSceneES() throws Exception{
        elasticsSearchService.searchScenesList("test");
    }

    // ES创建索引
    @Ignore
    @Test
    public void testCreateIndex() throws Exception {
        elasticsSearchService.createSceneIndex();
        elasticsSearchService.createUserIndex();
        System.out.println("Successfully created Index!");
    }

    //ES更新文档
    //@Ignore
    @Test
    public void testCreateDoc() throws Exception {
        elasticsSearchService.updateSceneDoc();
        elasticsSearchService.updateUserDoc();
        System.out.println("Successfully update document!");
    }
 

    @Test
    void testCreateScene() throws Exception {
        // 准备测试数据
        CreateSceneRequest request = new CreateSceneRequest();
        request.setName("Test Scene");
        request.setUrl("http://example.com/test");
        request.setDescription("This is a test scene");
        List<Long> collaborators = new ArrayList<>();
        collaborators.add(1L);
        collaborators.add(2L);
        request.setCollaborators(collaborators);
        long userId = 26L;

        // 调用测试方法
        SceneResponse response = sceneResolver.createScene(request, userId);

        // 验证返回的场景是否符合预期
        assertNotNull(response);
        assertEquals(userId, response.getCreatorId());
        assertEquals(request.getName(), response.getName());
        assertEquals(request.getUrl(), response.getUrl());
        assertEquals(request.getDescription(), response.getDescription());

        // 验证场景是否被保存到数据库中
        Optional<Scene> savedSceneOpt = sceneDao.findBySceneIdAndSceneIsDeleted(response.getId(),0);
        assertTrue(savedSceneOpt.isPresent());
        Scene savedScene = savedSceneOpt.get();
        assertEquals(response.getId(), savedScene.getSceneId());
        assertEquals(response.getCreatorId(), savedScene.getSceneCreatorId());
        assertEquals(response.getName(), savedScene.getSceneName());
        assertEquals(response.getUrl(), savedScene.getSceneUrl());
        assertEquals(response.getDescription(), savedScene.getSceneDescription());
    }

    @Ignore
    @Test
    void testInsertCollaborators() {
        // 创建一个场景对象
        Scene scene = new Scene();
        scene.setSceneCreatorId(10L);
        scene.setSceneCreateTime(LocalDateTime.now());
        scene.setSceneName("testScene");
        scene.setSceneUrl("https://lumalabs.ai/capture/gQFB");
        scene.setSceneDescription("This is a test scene");
        scene.setSceneIsDeleted(0);
        scene.setSceneIsAudit(0);
        scene.setSceneIsPublic(0);

        // 保存场景到数据库中
        Scene savedScene = sceneDao.save(scene);

        // 创建要添加的协作者列表
        List<Long> collaborators = new ArrayList<>();
        collaborators.add(3L);
        collaborators.add(4L);

        // 调用插入协作者的方法
//        Scene updatedScene = sceneService.insertCollaborators(savedScene.getSceneId(), collaborators);
//
//        // 验证更新后的场景对象是否正确
//        assertNotNull(updatedScene);
//        assertEquals(savedScene.getSceneId(), updatedScene.getSceneId());
//        assertEquals(savedScene.getSceneCreatorId(), updatedScene.getSceneCreatorId());
//        assertEquals(savedScene.getSceneCreateTime(), updatedScene.getSceneCreateTime());
//        assertEquals(savedScene.getSceneName(), updatedScene.getSceneName());
//        assertEquals(savedScene.getSceneUrl(), updatedScene.getSceneUrl());
//        assertEquals(savedScene.getSceneIsDeleted(), updatedScene.getSceneIsDeleted());
//        assertEquals(savedScene.getSceneIsAudit(), updatedScene.getSceneIsAudit());
//        assertEquals(savedScene.getSceneIsPublic(), updatedScene.getSceneIsPublic());

        // 验证协作者列表是否正确
        JSONArray jsonArray = new JSONArray();
        for (Long collaboratorId : collaborators) {
            JSONObject collaboratorObj = new JSONObject();
            collaboratorObj.put("collaborator_id", collaboratorId);
            jsonArray.add(collaboratorObj);
        }

        // 将 JSON 数组转换为字符串
        String expectedCollaboratorsJson = jsonArray.toJSONString();
//        assertEquals(expectedCollaboratorsJson, updatedScene.getCollaborators());
    }

    @Ignore
    @Test
    void testUpdateCollaborators() throws Exception{
        // Prepare test data
        CreateSceneRequest request = new CreateSceneRequest();
        request.setName("Test Scene");
        request.setUrl("http://example.com/test");
        request.setDescription("This is a test scene");
        List<Long> collaborators = new ArrayList<>();
        request.setCollaborators(collaborators);
        long userId = 27L;

        // Invoke the test method
        SceneResponse response = sceneResolver.createScene(request, userId);

        List<Long> initialCollaborators = Arrays.asList(6L, 7L, 8L);
        sceneService.insertCollaborators(response.getId(), initialCollaborators);

        // Collaborator to be added
        long updatedCollaborator = 13L;

        // Call the method to update collaborators
//        sceneService.addSceneCollaborators(response.getId(), updatedCollaborator);

        // Verify if the updated list of collaborators meets the expectations
        List<Long> retrievedCollaborators = sceneService.getCollaborators(response.getId());
        assertEquals(4, retrievedCollaborators.size());
        assertTrue(retrievedCollaborators.contains(updatedCollaborator));
    }

    @Ignore
    @Test
    void testUpdateCollaborators2() throws Exception{
        // 准备测试数据
        CreateSceneRequest request = new CreateSceneRequest();
        request.setName("Test Scene");
        request.setUrl("http://example.com/test");
        request.setDescription("This is a test scene");
        List<Long> collaborators = new ArrayList<>();
        collaborators.add(1L);
        collaborators.add(2L);
        request.setCollaborators(collaborators);
        long userId = 26L;

        // 调用测试方法
        SceneResponse response = sceneResolver.createScene(request, userId);

        List<Long> initialCollaborators = Arrays.asList(6L, 7L, 8L);
        sceneService.insertCollaborators(response.getId(), initialCollaborators);

        // 将要更新的协作者列表
        List<Long> updatedCollaborators = Arrays.asList(13L, 14L);

        // 调用更新协作者的方法
        sceneService.addSceneCollaborators(response.getId(), updatedCollaborators);

        // 验证更新后的协作者列表是否符合预期
        List<Long> retrievedCollaborators = sceneService.getCollaborators(response.getId());
        assertEquals(7, retrievedCollaborators.size());
        assertTrue(retrievedCollaborators.containsAll(updatedCollaborators));
    }


    @Test
    void testRemoveCollaborator() throws Exception {
        // 准备测试数据
        CreateSceneRequest request = new CreateSceneRequest();
        request.setName("Test Scene");
        request.setUrl("http://example.com/test");
        request.setDescription("This is a test scene");
        List<Long> collaborators = new ArrayList<>();
        collaborators.add(6L);
        collaborators.add(7L);
        collaborators.add(8L);
        request.setCollaborators(collaborators);
        long userId = 27L;

        // 创建场景并添加协作者
        SceneResponse response = sceneResolver.createScene(request, userId);

        // 要移除的协作者列表
        List<Long> collaboratorIdsToRemove = Arrays.asList(6L, 8L);

        // 调用移除协作者的方法
        SceneResponse updatedScene = sceneService.removeCollaborator(response.getId(), collaboratorIdsToRemove);

        // 验证更新后的协作者列表是否符合预期
        List<Long> retrievedCollaborators = sceneService.getCollaborators(response.getId());
        assertEquals(1, retrievedCollaborators.size());
        assertFalse(retrievedCollaborators.containsAll(collaboratorIdsToRemove));
    }

    @Test
    void testGetCollaboratorsBySceneId() {
        // Arrange
        long sceneId = 13L; // Assuming scene with ID 1 exists in the database

        // Act
        List<Long> actualCollaborators = sceneService.getCollaborators(sceneId);
        List<Long>expect =
                Arrays.asList(1L, 2L); // Assuming the expected collaborators are 1, 2, and 3
        // Assert
        assertEquals(2, actualCollaborators.size());
        assertEquals(expect, actualCollaborators);
    }

    @Test
    void testGetSceneIdByCollaboratorId(){
        List<Scene> scenes=sceneDao.findAllByAssist(10L);
        for(Scene s:scenes){
            System.out.println(s.getSceneId());
        }
    }

    @Test
    void testGenerateDat() throws Exception{
        scheduleService.autoGenerateRecommendDat();
    }

    @Test
    void testGetMostPopularSceneList() throws Exception{
        System.out.println(jedisService.getMostPopularSceneList(5));
    }

}
