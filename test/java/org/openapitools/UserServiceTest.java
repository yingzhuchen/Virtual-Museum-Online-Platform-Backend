package org.openapitools;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openapitools.entity.User;
import org.openapitools.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@Ignore
@Import(TestConfig.class)
public class UserServiceTest {

    @Autowired
    private UserServiceImpl userService;

    @Ignore
    @Test
    public void testRegisterUser() throws Exception {

        // 创建一个新的 User 对象并设置关联的 Role 对象
        User newUser = new User();
        newUser.setUserName("testuser256789");
        newUser.setUserPassword("TestPassword14!");
        newUser.setUserVersion(0);
        newUser.setUserIsDeleted(0);
        newUser.setAvatarUrl("https://example.com/avatar.jpg"); // 设置用户头像URL

        // 注册新用户
        User registeredUser = userService.registerUser(newUser);

        // 验证注册的用户不为空
        assertNotNull(registeredUser);

        // 验证注册的用户在数据库中存在
        String token = userService.loginUser("testuser256789", "TestPassword14!");
        assertNotNull(token);
    }

    @Ignore
    @Test
    public void testLoginUser() throws Exception {
        // 创建一个新的 User 对象并设置关联的 Role 对象
        User newUser = new User();
        newUser.setUserName("testuser2789");
        newUser.setUserPassword("TestPassword2!");
        newUser.setUserVersion(0);
        newUser.setUserIsDeleted(0);

        // 注册新用户
        userService.registerUser(newUser);

        // 使用测试用户登录
        String token = userService.loginUser("testuser2789", "TestPassword2!");

        // 验证登录的 token 不为空
        assertNotNull(token);
    }

    @Test
    void testUpdateUser() throws Exception{
        // 在数据库中创建一个示例用户
        User existingUser = new User();
        existingUser.setUserName("testuser2789123456");
        existingUser.setUserPassword("TestPassword2!");
        existingUser.setUserVersion(1);
        userService.registerUser(existingUser);

        // 更新用户信息
        existingUser.setUserName("testuser2789999");
        existingUser.setUserPassword("TestPassword2!");
        User updatedUser = userService.updateUser(existingUser);

        // 从数据库中检索用户
        User retrievedUser = userService.getUserById(updatedUser.getUserId());

        // 验证用户是否被正确更新
        assertNotNull(retrievedUser);
        assertEquals("testuser2789999", retrievedUser.getUserName());
        assertEquals("TestPassword2!", retrievedUser.getUserPassword());
        assertEquals(2, retrievedUser.getUserVersion());
    }


    @Test
    void testDeleteUser() throws Exception{
        // 在数据库中创建一个示例用户
        User existingUser = new User();
        existingUser.setUserName("testuser27899991");
        existingUser.setUserPassword("TestPassword2!");
        existingUser.setUserIsDeleted(0);
        userService.registerUser(existingUser);

        // 删除用户
        userService.deleteUser(existingUser.getUserId());

        // 尝试从数据库中检索已删除的用户
        User retrievedUser = userService.getUserById(existingUser.getUserId());

        // 验证用户是否已被删除
        assertNull(retrievedUser);
    }


    @Test
    void testGetUserById() throws Exception{
        // 在数据库中创建一个示例用户
        User existingUser = new User();
        existingUser.setUserName("testuser2789999123");
        existingUser.setUserPassword("TestPassword2!!");
        existingUser.setUserIsDeleted(0);
        userService.registerUser(existingUser);

        // 从数据库中检索用户
        User retrievedUser = userService.getUserById(existingUser.getUserId());

        // 验证检索到的用户与示例用户匹配
        assertNotNull(retrievedUser);
        assertEquals(existingUser.getUserId(), retrievedUser.getUserId());
        assertEquals(existingUser.getUserName(), retrievedUser.getUserName());
        assertEquals(existingUser.getUserPassword(), retrievedUser.getUserPassword());
        assertEquals(existingUser.getUserIsDeleted(), retrievedUser.getUserIsDeleted());
    }

    @Test
    //测试权限查询
    public void testGetUserRoleById() throws Exception{
        // 测试角色为 Admin 的用户
        User adminUser = new User();
        adminUser.setUserName("admin");
        adminUser.setUserPassword("adminA123!");

        User savedAdminUser = userService.registerUser(adminUser);
        String adminRole = userService.getUserRoleById(savedAdminUser.getUserId());
        assertEquals("Admin", adminRole);

        // 测试角色为 Traveller 的用户
        User travellerUser = new User();
        travellerUser.setUserName("traveller");
        travellerUser.setUserPassword("travellerA123!");

        User savedTravellerUser = userService.registerUser(travellerUser);
        String travellerRole = userService.getUserRoleById(savedTravellerUser.getUserId());
        assertEquals("Traveller", travellerRole);

        // 测试角色为 User 的用户
        User userUser = new User();
        userUser.setUserName("user");
        userUser.setUserPassword("userA123!");

        User savedUserUser = userService.registerUser(userUser);
        String userRole = userService.getUserRoleById(savedUserUser.getUserId());
        assertEquals("User", userRole);

        // 测试用户不存在的情况
        assertThrows(IllegalArgumentException.class, () -> userService.getUserRoleById(-1L));
    }

}
