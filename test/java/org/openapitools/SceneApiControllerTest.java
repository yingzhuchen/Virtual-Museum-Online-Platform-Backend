package org.openapitools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openapitools.api.EsSceneApiController;
import org.openapitools.dao.SceneCoverImageDao;
import org.openapitools.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.StatusResultMatchers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
//@Ignore
@Slf4j
public class SceneApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private String token;

    @Autowired
    SceneCoverImageDao sceneCoverImageDao;

    @Autowired
    EsSceneApiController esSceneApiController;


    @Ignore
    @Test
    public void testUserLogin() throws Exception {
        LoginRequest req = new LoginRequest();
        req.setUsername("sajdsak");
        req.setPassword("Cb123456.qwq");
        String json = JSON.toJSONString(req);
        MockHttpSession session = new MockHttpSession();
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .post("/user/login")
                .content(json)
                .contentType("application/json;charset=utf-8")
                .session(session)
                .accept("application/json");
        ResultActions action=mockMvc.perform(builder);
        //定义结果预期值
        StatusResultMatchers status = MockMvcResultMatchers.status();
        //预期状态码200成功
        ResultMatcher ok = status.isOk();
        //进行比较
        action.andExpect(ok)
                .andExpect(MockMvcResultMatchers.jsonPath("token").isNotEmpty())
                .andDo(print());

        MvcResult res=action.andReturn();
        JSONObject jsonObject = JSONObject.parseObject(res.getResponse().getContentAsString());
        this.token= jsonObject.getString("token");

    }

    @Ignore
    @Test
    public void testCreateScene() throws Exception {
        //这里假设数据库内有Id：24，Role：Admin，Name：xlx的用户数据
        testUserLogin();
        CreateSceneRequest req=new CreateSceneRequest();
        req.setName("testScene");
        req.setUrl("https://lumalabs.ai/capture/8575c740-88ce-4418-bd06-de06c51b2f3f");
        req.setDescription("test description");
        String json = JSON.toJSONString(req);
        MockHttpSession session = new MockHttpSession();
        MockHttpServletRequestBuilder infoBuilder = MockMvcRequestBuilders
                .put("/scene/create")
                .content(json)
                .header("Authorization","Bearer "+token)
                .contentType("application/json;charset=utf-8")
                .session(session)
                .accept("application/json");
        ResultActions infoAction=mockMvc.perform(infoBuilder);
        //定义结果预期值
        StatusResultMatchers status = MockMvcResultMatchers.status();
        //预期状态码201成功
        ResultMatcher created = status.isCreated();
        infoAction.andExpect(created)
                .andExpect(MockMvcResultMatchers.jsonPath("creatorId").value(24))
                .andExpect(MockMvcResultMatchers.jsonPath("name").value("testScene"))
                .andExpect(MockMvcResultMatchers.jsonPath("url").value("https://lumalabs.ai/capture/8575c740-88ce-4418-bd06-de06c51b2f3f"))
                .andExpect(MockMvcResultMatchers.jsonPath("description").value("test description"))
                .andDo(print());
    }

    @Ignore
    @Test
    public void testGetScene() throws Exception {
        //这里假设数据库内有Id：24，Role：Admin，Name：xlx的用户数据
        testUserLogin();
        //这里假设数据库内有Id：4，creatorId：24，Name：testScene的场景数据
        MockHttpSession session = new MockHttpSession();
        MockHttpServletRequestBuilder infoBuilder = MockMvcRequestBuilders
                .get("/scene")
                .param("id","4")
                .header("Authorization","Bearer "+token)
                .contentType("application/json;charset=utf-8")
                .session(session)
                .accept("application/json");
        ResultActions infoAction=mockMvc.perform(infoBuilder);
        //定义结果预期值
        StatusResultMatchers status = MockMvcResultMatchers.status();
        //预期状态码200成功
        ResultMatcher ok = status.isOk();
        infoAction.andExpect(ok)
                .andExpect(MockMvcResultMatchers.jsonPath("creatorId").value(24))
                .andExpect(MockMvcResultMatchers.jsonPath("name").value("testScene"))
                .andExpect(MockMvcResultMatchers.jsonPath("url").value("https://lumalabs.ai/capture/8575c740-88ce-4418-bd06-de06c51b2f3f"))
                .andExpect(MockMvcResultMatchers.jsonPath("description").value("test description"))
                .andDo(print());
    }

    @Ignore
    @Test
    public void testListScene() throws Exception {
        //这里假设数据库内有Id：24，Role：Admin，Name：xlx的用户数据
        testUserLogin();
        //这里假设数据库内有Id：4，creatorId：24，Name：testScene的场景数据
        MockHttpSession session = new MockHttpSession();
        MockHttpServletRequestBuilder infoBuilder = MockMvcRequestBuilders
                .get("/scene/list")
                .param("first","4")
                .header("Authorization","Bearer "+token)
                .contentType("application/json;charset=utf-8")
                .session(session)
                .accept("application/json");
        ResultActions infoAction=mockMvc.perform(infoBuilder);
        //定义结果预期值
        StatusResultMatchers status = MockMvcResultMatchers.status();
        //预期状态码200成功
        ResultMatcher ok = status.isOk();
        infoAction.andExpect(ok)
                .andExpect(MockMvcResultMatchers.jsonPath("totalCount").value(1))
                .andDo(print());
    }

    @Ignore
    @Test
    public void testDeleteScene() throws Exception {
        //这里假设数据库内有Id：24，Role：Admin，Name：xlx的用户数据
        testUserLogin();
        //这里假设数据库内有Id：4，creatorId：24，Name：testScene的场景数据
        MockHttpSession session = new MockHttpSession();
        MockHttpServletRequestBuilder infoBuilder = MockMvcRequestBuilders
                .delete("/scene")
                .param("id","4")
                .header("Authorization","Bearer "+token)
                .contentType("application/json;charset=utf-8")
                .session(session)
                .accept("application/json");
        ResultActions infoAction=mockMvc.perform(infoBuilder);
        //定义结果预期值
        StatusResultMatchers status = MockMvcResultMatchers.status();
        //预期状态码200成功
        ResultMatcher ok = status.isOk();
        infoAction.andExpect(ok)
                .andDo(print());
    }

    //@Ignore
    @Test
    public void testUploadCover() throws Exception{
        //这里假设根目录下有名为"group.jpg"的文件
        String imgPath="group.jpg";
        File file=new File(imgPath);
        long fileSize=file.length();
        byte[] buffer=new byte[(int)fileSize];
        try(FileInputStream fis = new FileInputStream(file)) {
            int result=fis.read(buffer);
            System.err.println("图片总字节数："+result);
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
        String imageString= Base64.getEncoder().encodeToString(buffer);
        //这里假设数据库内有Id：24，Role：Admin，Name：xlx的用户数据
        testUserLogin();
        UploadImageRequest request=new UploadImageRequest();
        request.setImage(imageString);
        //这里假设数据库内有Id：5，creatorId：24，Name：testScene的场景数据
        //request.setSceneId(5);
        request.setType(UploadImageType.sceneCover);
        String json = JSON.toJSONString(request);
        MockHttpSession session = new MockHttpSession();
        MockHttpServletRequestBuilder uploadBuilder = MockMvcRequestBuilders
                .post("/general/upload/image")
                .content(json)
                .header("Authorization","Bearer "+token)
                .contentType("application/json;charset=utf-8")
                .session(session)
                .accept("application/json");
        ResultActions infoAction=mockMvc.perform(uploadBuilder);
        StatusResultMatchers status = MockMvcResultMatchers.status();
        ResultMatcher ok = status.isOk();
        infoAction.andExpect(ok)
                .andDo(print());
    }

    @Test
    public void testUploadVideo() throws Exception{
        //这里假设根目录下有名为"test.mp4"的文件
        String imgPath="test.mp4";
        File file=new File(imgPath);
        long fileSize=file.length();
        byte[] buffer=new byte[(int)fileSize];
        try(FileInputStream fis = new FileInputStream(file)) {
            int result=fis.read(buffer);
            System.err.println("视频总字节数："+result);
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
        MockMultipartFile videoFile = new MockMultipartFile(
                "videoFile",
                "test.mp4",
                "video/mp4",
                buffer
        );
        //这里假设数据库内有Id：24，Role：Admin，Name：xlx的用户数据
        testUserLogin();
        MockHttpSession session = new MockHttpSession();
        MockHttpServletRequestBuilder uploadBuilder = MockMvcRequestBuilders
                .multipart("/general/upload/video")
                .file(videoFile)
                .header("Authorization","Bearer "+token)
                .contentType("multipart/form-data")
                .session(session)
                .accept("application/json");
        ResultActions infoAction=mockMvc.perform(uploadBuilder);
        StatusResultMatchers status = MockMvcResultMatchers.status();
        ResultMatcher ok = status.isOk();
        infoAction.andExpect(ok)
                .andDo(print());
    }

}
