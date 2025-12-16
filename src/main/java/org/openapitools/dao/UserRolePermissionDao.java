package org.openapitools.dao;

import org.openapitools.entity.UserRolePermission;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.Optional;

@Repository
public interface UserRolePermissionDao extends JpaRepository<UserRolePermission, Long> {
    // 这里可以添加自定义的查询方法，或者直接使用 JpaRepository 提供的方法
    Optional<UserRolePermission> findByUserId(long userId);

}
