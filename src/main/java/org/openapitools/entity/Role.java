package org.openapitools.entity;

// Role.java

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "Role") // 映射到数据库的Role表
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Role_Id") // 映射到数据库的Role_Id列
    private long roleId;

    @Column(name = "Role_name") // 映射到数据库的Role_name列
    private String roleName;
}
