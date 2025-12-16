package org.openapitools.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.openapitools.configuration.SpringSecurityConfig;
import org.openapitools.dao.*;
import org.openapitools.entity.*;
import org.openapitools.exception.ExpectedException;
import org.openapitools.exception.ExpectedException400;
import org.openapitools.exception.ExpectedException404;
import org.openapitools.model.TopSceneId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@SpringBootApplication
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRolePermissionDao userRolePermissionDao; // 用户角色权限数据访问对象

    @Autowired
    private RoleDao roleDao;

    @Autowired
    SpringSecurityConfig springSecurityConfig;

    @Autowired
    JedisServiceImpl jedisService;

    @Override
    public User registerUser(User user) throws Exception {
        // 检查用户名是否已存在
        if (userDao.findByUserName(user.getUserName()) != null) {
            throw new IllegalArgumentException("The username already exists");
        }

        // 检查密码是否符合要求
        String password = user.getUserPassword();
        if (!isValidPassword(password)) {
            throw new IllegalArgumentException("Password too simple");
        }

        // 加密密码
        user.setUserPassword(passwordEncoder.encode(password));

        //注册暂时不需要头像，先注释掉
        // 将用户头像URL插入到数据库
        // 这里假设用户头像URL不为空，若为空则根据实际情况进行处理
//        String avatarUrl = user.getAvatarUrl();
//        if (avatarUrl != null && !avatarUrl.isEmpty()) {
//            // 设置用户头像URL
//            user.setAvatarUrl(avatarUrl);
//        }

        //User插入数据库
        User savedUser = userDao.save(user);

        // 创建用户角色权限记录并保存到数据库
        UserRolePermission userRolePermission = new UserRolePermission();
        userRolePermission.setUserId(savedUser.getUserId());
        userRolePermission.setRoleId(1L); // 假设默认角色ID为1
        userRolePermission.setDeleted(0); // 默认不删除
        userRolePermissionDao.save(userRolePermission);

        //为新注册的用户添加default收藏夹
        jedisService.createSceneFavourites("default",savedUser.getUserId());

        // 将用户保存到数据库
        return savedUser;
    }

    @Override
    public String loginUser(String userName, String password) throws Exception {
        // 根据用户名查找用户
        User user = userDao.findByUserName(userName);

        // 检查用户是否存在，并且密码是否匹配
        if (user != null && passwordEncoder.matches(password, user.getUserPassword())) {
            // 生成 JWT 令牌
            return generateToken(user);
        } else {
            // 如果登录失败，则返回 null 或抛出异常
            throw new ExpectedException404("用户名或密码错误");
        }
    }


    @Override
    public User updateUser(User user) {
        // 根据用户ID查询用户信息
        User existingUser = userDao.findById(user.getUserId()).orElse(null);
        if (existingUser == null) {
            throw new IllegalArgumentException("User not found");
        }
        // 检查是否存在重名用户
        User userWithSameName = userDao.findByUserName(user.getUserName());
        if (userWithSameName != null && userWithSameName.getUserId() != user.getUserId()) {
            throw new IllegalArgumentException("Username already exists");
        }

        // 更新用户信息
        existingUser.setUserName(user.getUserName());
        existingUser.setUserPassword(user.getUserPassword());
        existingUser.setUserVersion(existingUser.getUserVersion() + 1); // 修改密码后版本号加1

        return userDao.save(existingUser);
    }

    @Override
    public void deleteUser(long userId) {
        // 根据用户ID查询用户信息
        User user = userDao.findById(userId).orElse(null);
        if (user != null) {
            // 修改 User_Is_Deleted 为 1
            user.setUserIsDeleted(1);
            // 保存更新后的用户信息到数据库
            userDao.save(user);
        }
    }

    @Override
    public User getUserById(long userId) {
        // 自定义查询，排除 User_Is_Deleted 为 1 的用户
        return userDao.findByUserIdAndUserIsDeleted(userId, 0);
    }

    @Override
    public String getUserRoleById(long userId) {
        // 根据用户ID查询用户信息
        User user = userDao.findById(userId).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        UserRolePermission userRole = userRolePermissionDao.findByUserId(userId).orElse(null);
        if (userRole == null) {
            throw new IllegalArgumentException("No record");
        }
        String roleString = roleDao.findByRoleId(userRole.getRoleId()).get().getRoleName();
        return roleString;
    }

    private boolean isValidPassword(String password) {
        // 检查密码长度和字符组合
        return password.length() >= 8 &&
                password.length() <= 25 &&
                password.matches(".*[0-9].*") &&
                password.matches(".*[a-z].*") &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[!\\?\\-+*/.^].*");
    }

    public String generateToken(User user) {
        // 将版本信息添加到有效载荷中
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 7 * 24 * 60 * 60 * 1000); // 7 天
        return Jwts.builder()
                .setSubject(Long.toString(user.getUserId()))
                .claim("version", user.getUserVersion())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, springSecurityConfig.getJwtSecret())
                .compact();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public List<User> getUserListByName(String query) {
        return userDao.findByUserNameContaining(query);
    }

    @Override
    public User updatePassword(User user,String oldPassword, String newPassword) throws ExpectedException400//更新密码
    {
        // 检查旧密码是否正确
        if (!passwordEncoder.matches(oldPassword, user.getUserPassword())) {
            throw new ExpectedException400("原密码错误");
        }
        // 检查密码格式
        if (!isValidPassword(newPassword)) {
            throw new ExpectedException400("新密码格式不正确");
        }

        // 加密新密码
        String encodedNewPassword = passwordEncoder.encode(newPassword);

        // 更新密码
        user.setUserPassword(encodedNewPassword);

        //version+1
        user.setUserVersion(user.getUserVersion() + 1);

        userDao.save(user);
        return user;
    }

    @Override
    public void editTopSceneIdList(long userId, List<TopSceneId> topSceneIdList) throws ExpectedException{

        User user = userDao.findById(userId).get();

        if (user == null) {
            throw new ExpectedException("用户不存在");
        }

        // 构建tags列表的 JSON 数组
        JSONArray jsonArray = new JSONArray();
        for (TopSceneId topSceneId : topSceneIdList) {
            JSONObject tagObj = new JSONObject();
            tagObj.put("top_scene_id", topSceneId.getId());
            jsonArray.add(tagObj);
        }

        // 将更新后的标签列表转换为字符串
        String updatedTopSceneIdJson = jsonArray.toJSONString();

        // 更新场景对象中的标签列表
        user.setTopSceneId(updatedTopSceneIdJson);

        // 保存更新后的场景对象到数据库
        userDao.save(user);

    }
}


