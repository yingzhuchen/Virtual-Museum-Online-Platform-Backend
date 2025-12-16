package org.openapitools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openapitools.model.LoginRequest;
import org.openapitools.model.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.StatusResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore
@AutoConfigureMockMvc
public class UserApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private String token;

    @Ignore
    @Test
    public void testUserLogin() throws Exception {
        LoginRequest req = new LoginRequest();
        req.setUsername("xlx");
        req.setPassword("aB123456!");
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
    public void testUserRegister() throws Exception {
        RegisterRequest req = new RegisterRequest();
        req.setUsername("xlx");
        req.setPassword("aB123456!");
        String json = JSON.toJSONString(req);
        MockHttpSession session = new MockHttpSession();
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .post("/user/register")
                .content(json)
                .contentType("application/json;charset=utf-8")
                .session(session)
                .accept("application/json");
        ResultActions action=mockMvc.perform(builder);
        //定义结果预期值
        StatusResultMatchers status = MockMvcResultMatchers.status();
        //预期状态码201成功
        ResultMatcher created = status.isCreated();
        //进行比较
        action.andExpect(created)
                .andDo(print());
    }

    @Ignore
    @Test
    public void testUserInfo() throws Exception {
        //这里假设数据库内有Id：24，Role：Admin，Name：xlx的用户数据
        testUserLogin();
        MockHttpSession session = new MockHttpSession();
        MockHttpServletRequestBuilder infoBuilder = MockMvcRequestBuilders
                .get("/user/info")
                .param("id","19")
                .header("Authorization","Bearer "+token)
                .contentType("application/json;charset=utf-8")
                .session(session)
                .accept("application/json");
        ResultActions infoAction=mockMvc.perform(infoBuilder);
        //定义结果预期值
        StatusResultMatchers status = MockMvcResultMatchers.status();
        //预期状态码200成功
        ResultMatcher ok = status.isOk();
        //这里假设数据库内有Id：19，Role：User，Name：user的用户数据
        infoAction.andExpect(ok)
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(19))
                .andExpect(MockMvcResultMatchers.jsonPath("name").value("user"))
                .andExpect(MockMvcResultMatchers.jsonPath("role").value("User"))
                .andDo(print());
    }

}
