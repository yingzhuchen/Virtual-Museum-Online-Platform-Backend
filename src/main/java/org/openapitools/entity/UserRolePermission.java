package org.openapitools.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "user_role_permission")
public class UserRolePermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_role_permission_id")
    private Long userRolePermissionId; // 用户角色权限ID，主键

    @Column(name = "user_id")
    private Long userId; // 用户ID

    @Column(name = "role_id")
    private Long roleId; // 角色ID

    @Column(name = "is_deleted")
    private Integer deleted; // 是否删除，true表示已删除，false表示未删除
}
