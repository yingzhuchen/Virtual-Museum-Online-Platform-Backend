package org.openapitools.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "scene_history") // 映射到数据库的 scene_istory 表
public class SceneHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private long Id;

    @Column(name = "Scene_Id")
    private long sceneId;

    @Column(name = "User_Id")
    private long userId;

    @Column(name = "Access_Time")
    private LocalDateTime sceneAccessTime;

}
