package org.openapitools.service;

// UserService.java


import org.openapitools.entity.User;
import org.openapitools.exception.ExpectedException;
import org.openapitools.exception.ExpectedException400;
import org.openapitools.model.TopSceneId;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface UserService {
    User registerUser(User user) throws Exception;// 注册用户
    String loginUser(String userName, String password) throws Exception;// 用户登录
    User updateUser(User user); // 更新用户信息

    User updatePassword(User user,String oldPassword,  String newPassword) throws ExpectedException400;//更新密码
    User getUserById(long userId); // 根据用户ID查询用户信息
    void deleteUser(long userId); // 删除用户

    String getUserRoleById(long userId);//权限查询

    List<User> getUserListByName(String query);// 模糊查询，用于协作者列表

     //修改置顶场景
     void editTopSceneIdList(long userId, List<TopSceneId> topSceneIdList) throws ExpectedException ;
}
