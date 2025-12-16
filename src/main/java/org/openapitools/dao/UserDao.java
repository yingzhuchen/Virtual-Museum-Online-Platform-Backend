package org.openapitools.dao;

// UserDao.java

import org.openapitools.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserDao extends JpaRepository<User, Long> {
    User findByUserName(String userName);
    User findByUserIdAndUserIsDeleted(Long userId, int userIsDeleted);

    // 添加根据用户名模糊查询的方法
    List<User> findByUserNameContaining(String userName);
}