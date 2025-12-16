package org.openapitools.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "scene_collect") // 映射到数据库的 scene_collect 表
public class SceneCollect {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private long Id;

    @Column(name = "Scene_Id")
    private long sceneId;

    @Column(name = "User_Id")
    private long userId;

}
